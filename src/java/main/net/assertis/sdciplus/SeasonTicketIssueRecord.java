package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class SeasonTicketIssueRecord extends SDCIPlusRecord
{
    public SeasonTicketIssueRecord(String rawRecord) throws ParseException
    {
        addField("LENNON Type of Ticket", rawRecord.substring(0, 3));
        addField("Fare Method", rawRecord.substring(3, 4));
        addField("Fare Amount", formatCurrencyField(rawRecord.substring(4, 14)));
        // Ignore next 4 chars, currency and method of payment are always the same.
        addField("Transaction Number", rawRecord.substring(18, 23));
        addField("Owning Transaction Header Number", rawRecord.substring(23, 28));
        addField("Status Code", rawRecord.substring(28, 31));
        addField("Class Indicator", classIndicatorToString(rawRecord.charAt(31)));
        addField("Origin NLC", rawRecord.substring(32, 36));
        addField("Destination NLC", rawRecord.substring(36, 40));
        addField("Route Code", rawRecord.substring(40, 45));
        addField("Promotion Code", rawRecord.substring(45, 47));
        addField("Discount Code", rawRecord.substring(47, 51));
        addField("Discount Percentage", rawRecord.substring(51, 54));
        int months = Integer.parseInt(rawRecord.substring(54, 56));
        int days = Integer.parseInt(rawRecord.substring(56, 58));
        addField("Period of Validity", months + " months, " + days + " days");
        addField("Start Date", DATE_FORMAT.parse(rawRecord.substring(58, 66)));
        addField("Expiry Date", DATE_FORMAT.parse(rawRecord.substring(66, 74)));
        addField("Lost Days", rawRecord.substring(74, 76));
        addField("Issue Time", rawRecord.substring(76, 80));
        addField("Advance Issue", rawRecord.charAt(80) == 'A');
    }


    @Override
    public String toString()
    {
        return "Season Ticket #" + getFields().get("Transaction Number") + " (" + getFields().get("Fare Amount") + ')';
    }
}
