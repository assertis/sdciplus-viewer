package net.assertis.shiftviewer;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Renderer for nodes in the {@link ShiftViewer} tree.
 * @author Daniel Dyer
 */
public class ShiftTreeRenderer extends DefaultTreeCellRenderer
{
    private static final Font NORMAL_FONT = UIManager.getDefaults().getFont("Tree.font");
    private static final Font BOLD_FONT = NORMAL_FONT.deriveFont(Font.BOLD);

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus)
    {
        JLabel component = (JLabel) super.getTreeCellRendererComponent(tree,
                                                                       value,
                                                                       selected,
                                                                       expanded,
                                                                       leaf,
                                                                       row,
                                                                       false); // Don't paint focus.
        component.setFont(leaf ? NORMAL_FONT : BOLD_FONT);
        return component;
    }
}
