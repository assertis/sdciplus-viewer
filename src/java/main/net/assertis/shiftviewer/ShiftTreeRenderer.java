package net.assertis.shiftviewer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import net.assertis.sdciplus.Transaction;

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
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        component.setForeground(getColor(node.getUserObject()));
        return component;
    }


    private Color getColor(Object item)
    {
        if (item instanceof Transaction && ((Transaction) item).isInvalid())
        {
            return Color.RED;
        }
        return UIManager.getDefaults().getColor("Label.foreground");
    }
}
