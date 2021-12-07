package net.assertis.shiftviewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import net.assertis.sdciplus.SDCIPlusRecord;

public class SDCIPlusRecordTableModel extends AbstractTableModel
{
    private List<String> keys = Collections.emptyList();
    private List<Object> values = Collections.emptyList();


    public void setRecord(SDCIPlusRecord record)
    {
        if (record == null)
        {
            keys = Collections.emptyList();
            values = Collections.emptyList();
        }
        else
        {
            Map<String, Object> fields = record.getFields();
            keys = new ArrayList<>(fields.keySet());
            values = new ArrayList<>(fields.values());
        }
        fireTableDataChanged();
    }

    public int getRowCount()
    {
        return keys.size();
    }


    public int getColumnCount()
    {
        return 2;
    }


    public String getColumnName(int column)
    {
        switch (column)
        {
            case 0: return "Field";
            case 1: return "Value";
            default: throw new IllegalArgumentException("Invalid column index.");
        }
    }


    public Object getValueAt(int row, int column)
    {
        switch (column)
        {
            case 0: return keys.get(row);
            case 1: return values.get(row);
            default: throw new IllegalArgumentException("Invalid column index.");
        }
    }
}
