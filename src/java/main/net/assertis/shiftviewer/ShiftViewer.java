package net.assertis.shiftviewer;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import net.assertis.sdciplus.SDCIPlusReader;
import net.assertis.sdciplus.SDCIPlusRecord;
import net.assertis.sdciplus.Shift;
import net.assertis.sdciplus.Transaction;

/**
 * GUI for displaying the contents of an SDCI+ file.
 * @author Daniel Dyer
 */
public class ShiftViewer extends JFrame
{
    public ShiftViewer(List<Shift> shifts)
    {
        super("SDCI+ Viewer");
        JTree tree = new JTree(createTreeStructure(shifts));
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        add(new JScrollPane(tree), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private TreeNode createTreeStructure(List<Shift> shifts)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (Shift shift : shifts)
        {
            DefaultMutableTreeNode shiftNode = new DefaultMutableTreeNode(shift);
            shiftNode.add(createRecordNode(shift.getHeaderRecord()));
            shiftNode.add(createRecordNode(shift.getVersionRecord()));
            for (Transaction transaction : shift.getTransactions())
            {
                DefaultMutableTreeNode transactionNode = new DefaultMutableTreeNode(transaction);
                transactionNode.add(createRecordNode(transaction.getHeaderRecord()));
                for (SDCIPlusRecord record : transaction.getRecords())
                {
                    transactionNode.add(createRecordNode(record));
                }
                shiftNode.add(transactionNode);
            }
            shiftNode.add(createRecordNode(shift.getTrailerRecord()));
            root.add(shiftNode);
        }
        return root;
    }

    private MutableTreeNode createRecordNode(SDCIPlusRecord record)
    {
        DefaultMutableTreeNode recordNode = new DefaultMutableTreeNode(record);
        for (Map.Entry<String, Object> entry : record.getFields().entrySet())
        {
            recordNode.add(new DefaultMutableTreeNode(entry.getKey() + "=" + entry.getValue().toString(), false));
        }
        return recordNode;
    }


    public static void main(String[] args) throws IOException
    {
        ShiftViewer viewer = new ShiftViewer(SDCIPlusReader.parseFile(args[0]));
        viewer.setSize(400, 600);
        viewer.validate();
        viewer.setVisible(true);
    }
}
