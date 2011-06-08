package org.jupiter.idea.ui.tree;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.usageView.UsageTreeColors;
import com.intellij.usageView.UsageTreeColorsScheme;
import org.jupiter.model.review.ReviewIssue;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Author: Iurii Lytvynenko
 * Date: 06.06.11
 * Time: 23:43
 */
public class ReviewFileNode extends PsiFileNode {
    private Collection<ReviewIssue> myChildIssues;

    public ReviewFileNode(Project project, PsiFile value, Collection<ReviewIssue> childIssues) {
        super(project, value, ViewSettings.DEFAULT);
        this.myChildIssues = childIssues;
    }

    @Override
    public Collection<AbstractTreeNode> getChildrenImpl() {
        Collection<AbstractTreeNode> children = new LinkedList<AbstractTreeNode>();
        for (ReviewIssue issue : myChildIssues) {
            children.add(new IssueNode(myProject, issue));
        }
        return children;
    }

    @Override
    protected void updateImpl(PresentationData data) {
        super.updateImpl(data);
        String text = data.getPresentableText();
        String usage = String.format(" (%d issues)", this.myChildIssues.size());
        EditorColorsScheme colorsScheme = UsageTreeColorsScheme.getInstance().getScheme();
        SimpleTextAttributes attrs = SimpleTextAttributes
                .fromTextAttributes(colorsScheme.getAttributes(UsageTreeColors.NUMBER_OF_USAGES));
        data.addText(text, SimpleTextAttributes.REGULAR_ATTRIBUTES);
        data.addText(usage, attrs);

    }
}