package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class ShiftHeaderRecord extends SDCIPlusRecord
{
    public ShiftHeaderRecord(String rawRecord) throws ParseException
    {
        addField("Date", DATE_TIME_FORMAT.parse(rawRecord.substring(0, 12)));
        addField("Revenue Mode", rawRecord.charAt(12) == '0');
        addField("Machine Type", rawRecord.substring(13, 15));
        addField("Machine Number", rawRecord.substring(15, 19));
        addField("Retailer NLC", rawRecord.substring(19, 23));
        addField("Shift Number", rawRecord.substring(23, 27));
        addField("Window Number", rawRecord.substring(27, 29));
        addField("Business Group", rawRecord.substring(29, 32));
        // Ignore next 2 chars (always 03 for WebTIS).
        addField("User", rawRecord.substring(34, 42));
        addField("Cummulative Total Debit Amount", formatCurrencyField(rawRecord.substring(42, 54)));
        addField("Cummulative Total Credit Amount", formatCurrencyField(rawRecord.substring(54, 66)));
        addField("Transaction Number", rawRecord.substring(66, 71));
        addField("Last Sundry Transaction Number", rawRecord.substring(71, 76));
        addField("Next Payment Transaction Number", rawRecord.substring(76, 81));
        addField("Last Cross London Transaction Number", rawRecord.substring(81, 86));
        addField("Last Sale Transaction Number", rawRecord.substring(86, 91));
    }


    @Override
    public String toString()
    {
        return "Shift Start - " + getFields().get("Date");
    }
}
