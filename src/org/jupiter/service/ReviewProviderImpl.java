package org.jupiter.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jupiter.model.review.Review;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: Iurii Lytvynenko
 * Date: 29.05.11
 * Time: 12:52
 */
public class ReviewProviderImpl implements ReviewProvider {
    public ReviewProviderImpl(Project project) {
        this.project = project;
    }

    private Project project;

    public Review getReview(String id) {
        VirtualFile reviewFile = getReviewFile();
        if (reviewFile != null) {
            try {
                return JAXB.unmarshal(reviewFile.getInputStream(), Review.class);
            } catch (IOException e) {
                // TODO: Log
                e.printStackTrace();
            }
        }
        return null;
    }

    private VirtualFile getReviewFile() {
        VirtualFile baseDir = project.getBaseDir();
//        return baseDir != null ? baseDir.findFileByRelativePath("review/GXT client v. 0.0.1-ander.review") : null;
        return baseDir != null ? baseDir.findFileByRelativePath("review/test-me.review") : null;
    }

    public void saveReview(Review review) {
        VirtualFile reviewFile = getReviewFile();
        if (reviewFile == null) {
            throw new IllegalStateException("Review file not found");
        }
        try {
            OutputStream out = reviewFile.getOutputStream(this);
            try {
                JAXB.marshal(review, out);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Can't write review file", e);
        }
    }
}