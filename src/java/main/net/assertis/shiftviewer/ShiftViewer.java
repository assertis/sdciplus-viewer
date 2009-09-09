package net.assertis.shiftviewer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
    private final JFileChooser fileChooser = new JFileChooser();
    private final JTree tree = new JTree();

    public ShiftViewer(List<Shift> shifts)
    {
        super("SDCI+ File Viewer");
        tree.setModel(new DefaultTreeModel(createTreeStructure(shifts)));
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        setJMenuBar(createMenuBar());
        add(new JScrollPane(tree), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open\u2026");
        openItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                int result = fileChooser.showOpenDialog(ShiftViewer.this);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        File file = fileChooser.getSelectedFile();
                        List<Shift> shifts = SDCIPlusReader.parseFile(file);
                        tree.setModel(new DefaultTreeModel(createTreeStructure(shifts)));
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(ShiftViewer.this,
                                                      ex.getMessage(),
                                                      "Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                System.exit(0);
            }
        });
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);            
        return menuBar;
    }


    /**
     * Convert a list of shifts into a tree of nodes that can be displayed in a JTree.
     */
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


    public static void main(String[] args) throws IOException,
                                                  IllegalAccessException,
                                                  UnsupportedLookAndFeelException,
                                                  InstantiationException,
                                                  ClassNotFoundException
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        List<Shift> shifts = args.length > 0
                             ? SDCIPlusReader.parseFile(new File(args[0]))
                             : Collections.<Shift>emptyList();
        ShiftViewer viewer = new ShiftViewer(shifts);
        viewer.setSize(400, 600);
        viewer.validate();
        viewer.setVisible(true);
    }
}
