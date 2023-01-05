package net.assertis.shiftviewer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
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
    private final SDCIPlusRecordTableModel tableModel = new SDCIPlusRecordTableModel();
    private final JTable table = new JTable(tableModel);
    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                                        new JScrollPane(tree),
                                                        new JScrollPane(table));

    public ShiftViewer(List<Shift> shifts)
    {
        super("SDCI+ File Viewer");
        tree.setModel(new DefaultTreeModel(createTreeStructure(shifts)));
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setSelectionModel(selectionModel);
        tree.setCellRenderer(new ShiftTreeRenderer());
        tree.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent ev)
            {
                TreePath path = ev.getNewLeadSelectionPath();
                Object value = path == null ? null : ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
                if (value != null && value instanceof SDCIPlusRecord)
                {
                    tableModel.setRecord((SDCIPlusRecord) value);
                }
                else
                {
                    tableModel.setRecord(null);
                }
            }
        });
        add(splitPane, BorderLayout.CENTER);

        setJMenuBar(createMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());
        return menuBar;
    }


    private JMenu createFileMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open\u2026");
        openItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                showFileOpenDialog();
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
        return fileMenu;
    }


    private JMenu createViewMenu()
    {
        JMenu viewMenu = new JMenu("View");
        JMenuItem expandItem = new JMenuItem("Expand all");
        expandItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                expandAll(new TreePath((TreeNode) tree.getModel().getRoot()));
            }
        });
        JMenuItem collapseItem = new JMenuItem("Collapse all");
        collapseItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                collapseAll(new TreePath((TreeNode) tree.getModel().getRoot()));
            }
        });
        viewMenu.add(expandItem);
        viewMenu.add(collapseItem);
        return viewMenu;
    }


    private void expandAll(TreePath parent)
    {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        for (Enumeration e = node.children(); e.hasMoreElements();)
        {
            TreeNode child = (TreeNode) e.nextElement();
            expandAll(parent.pathByAddingChild(child));
        }
        tree.expandPath(parent);
    }


    private void collapseAll(TreePath parent)
    {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        for (Enumeration e = node.children(); e.hasMoreElements();)
        {
            TreeNode child = (TreeNode) e.nextElement();
            collapseAll(parent.pathByAddingChild(child));
        }
        if (node != tree.getModel().getRoot())
        {
            tree.collapsePath(parent);
        }
    }


    private void showFileOpenDialog()
    {
        int result = fileChooser.showOpenDialog(ShiftViewer.this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            openFile(fileChooser.getSelectedFile());
        }
    }


    private void openFile(File file)
    {
        try
        {
            List<Shift> shifts = SDCIPlusReader.parseFile(file);
            tree.setModel(new DefaultTreeModel(createTreeStructure(shifts)));
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(ShiftViewer.this,
                                          ex.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
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
            if (shift.isNullShift())
            {
                shiftNode.add(new DefaultMutableTreeNode(shift.getNullShiftRecord()));
            }
            else
            {
                shiftNode.add(new DefaultMutableTreeNode(shift.getHeaderRecord()));
                shiftNode.add(new DefaultMutableTreeNode(shift.getVersionRecord()));
                String previous = null;
                for (Transaction transaction : shift.getTransactions())
                {
                    transaction.setInvalid(previous != null && !transaction.getPreviousTransactionHeaderNumber().equals(previous));
                    DefaultMutableTreeNode transactionNode = new DefaultMutableTreeNode(transaction);
                    transactionNode.add(new DefaultMutableTreeNode(transaction.getHeaderRecord()));
                    for (SDCIPlusRecord record : transaction.getRecords())
                    {
                        transactionNode.add(new DefaultMutableTreeNode(record));
                    }
                    shiftNode.add(transactionNode);
                    previous = transaction.getTransactionHeaderNumber();
                }
                shiftNode.add(new DefaultMutableTreeNode(shift.getTrailerRecord()));
            }
            root.add(shiftNode);
        }
        return root;
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
        viewer.setSize(800, 600);
        viewer.validate();
        viewer.setVisible(true);
        viewer.splitPane.setDividerLocation(0.5);
        // If no file is specified on the command line, prompt to open one.
        if (args.length == 0)
        {
            viewer.showFileOpenDialog();
        }
    }
}
