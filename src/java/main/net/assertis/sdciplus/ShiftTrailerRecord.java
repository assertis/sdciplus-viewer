package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class ShiftTrailerRecord extends SDCIPlusRecord
{
    public ShiftTrailerRecord(String rawRecord) throws ParseException
    {
        addField("Date", DATE_TIME_FORMAT.parse(rawRecord.substring(0, 12)));
        addField("Cummulative Total Debit Amount", formatCurrencyField(rawRecord.substring(12, 24)));
        addField("Cummulative Total Credit Amount", formatCurrencyField(rawRecord.substring(24, 36)));
        // Skip currency (1 char), always Sterling.
        addField("Machine Type", rawRecord.substring(37, 39));
        addField("Machine Number", rawRecord.substring(39, 43));
        addField("Retailer NLC", rawRecord.substring(43, 47));
        addField("Shift Number", rawRecord.substring(47, 51));
        addField("Window Number", rawRecord.substring(51, 53));
        addField("Business Group", rawRecord.substring(53, 56));
        // Ignore next 2 chars (always 03 for WebTIS).
        addField("Revenue Mode", rawRecord.charAt(58) == '0');
        addField("Transaction Number", rawRecord.substring(59, 64));
        addField("Last Cross London Transaction Number", rawRecord.substring(64, 69));
        addField("Next Payment Transaction Number", rawRecord.substring(69, 74));
        addField("Last Sundry Transaction Number", rawRecord.substring(74, 79));
        addField("Last Transaction Header Number", rawRecord.substring(79, 84));
        addField("Shift Ticket Total Debit Amount", formatCurrencyField(rawRecord.substring(84, 96)));
        addField("Shift Ticket Total Credit Amount", formatCurrencyField(rawRecord.substring(96, 108)));
        addField("Shift Sundry Total Debit Amount", formatCurrencyField(rawRecord.substring(108, 120)));
        addField("Shift Sundry Total Credit Amount", formatCurrencyField(rawRecord.substring(120, 132)));
        addField("Shift Payment Card Total Debit Amount", formatCurrencyField(rawRecord.substring(132, 144)));
        addField("Shift Payment Card Total Credit Amount", formatCurrencyField(rawRecord.substring(144, 156)));
        addField("Refund Reversals Total Debit Amount", formatCurrencyField(rawRecord.substring(156, 168)));
        addField("Refunds Total Credit Amount", formatCurrencyField(rawRecord.substring(168, 180)));
    }


    @Override
    public String toString()
    {
        return "Shift End - " + getFields().get("Date");
    }
}
