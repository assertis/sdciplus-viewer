package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class TicketIssueRecord extends SDCIPlusRecord
{
    public TicketIssueRecord(String rawRecord) throws ParseException
    {
        addField("LENNON Type of Ticket", rawRecord.substring(0, 3));
        addField("Fare Method", rawRecord.substring(3, 4));
        addField("Via London", rawRecord.charAt(4) == '1');
        addField("Fare Amount", formatCurrencyField(rawRecord.substring(5, 15)));
        // Ignore next 4 chars, currency and method of payment are always the same.
        addField("Transaction Number", rawRecord.substring(19, 24));
        addField("Ticket Number", rawRecord.substring(24, 33).trim());
        addField("Owning Transaction Header Number", rawRecord.substring(33, 38));
        addField("Status Code", rawRecord.substring(38, 41));
        addField("Number of Adults", Integer.parseInt(rawRecord.substring(41, 43)));
        addField("Number of Children", Integer.parseInt(rawRecord.substring(43, 45)));
        addField("Class Indicator", classIndicatorToString(rawRecord.charAt(45)));
        addField("Origin NLC", expandNLC(rawRecord.substring(46, 50)));
        addField("Destination NLC", expandNLC(rawRecord.substring(50, 54)));
        addField("Route Code", rawRecord.substring(54, 59));
        addField("Promotion Code", rawRecord.substring(59, 61));
        addField("Discount Code", rawRecord.substring(61, 65));
        addField("Discount Percentage", rawRecord.substring(65, 68));
        addField("Travel Date", DATE_FORMAT.parse(rawRecord.substring(68, 76)));
        addField("Issue Time", rawRecord.substring(76, 80));
    }


    @Override
    public String toString()
    {
        return "Ticket #" + getFields().get("Transaction Number") + " (" + getFields().get("Fare Amount") + ')';
    }
}
