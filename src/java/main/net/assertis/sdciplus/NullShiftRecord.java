package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class NullShiftRecord extends SDCIPlusRecord
{
    public NullShiftRecord(String rawRecord) throws ParseException
    {
        addField("Date", DATE_TIME_FORMAT.parse(rawRecord.substring(0, 12)));
        addField("Machine Type", rawRecord.substring(12, 14));
        addField("Machine Number", rawRecord.substring(14, 18));
        addField("Retailer NLC", rawRecord.substring(18, 22));
        addField("Window Number", rawRecord.substring(22, 24));
        addField("Business Group", rawRecord.substring(24, 27));
        // Ignore last 2 chars (always 03 for WebTIS).
    }


    @Override
    public String toString()
    {
        return "Null Shift - " + getFields().get("Date");
    }
}
