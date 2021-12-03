package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class RefundRecord extends SDCIPlusRecord
{
    public RefundRecord(String rawRecord) throws ParseException
    {
        addField("Origin NLC", expandNLC(rawRecord.substring(0, 4)));
        addField("Destination NLC", expandNLC(rawRecord.substring(4, 8)));
        addField("Route Code", rawRecord.substring(8, 13));
        addField("Refund Type", rawRecord.substring(13, 16));
        addField("Status Code", rawRecord.substring(16, 19));
        addField("Number of Adults", Integer.parseInt(rawRecord.substring(19, 21)));
        addField("Number of Children", Integer.parseInt(rawRecord.substring(21, 23)));
        addField("Class Indicator", classIndicatorToString(rawRecord.charAt(23)));
        // Ignore season-ticket specific fields (assuming we're not dealing with season ticket refunds).
        addField("Original Fare Amount", formatCurrencyField(rawRecord.substring(60, 70)));
        addField("Refund Amount", formatCurrencyField(rawRecord.substring(70, 80)));
        // Ignore refund method (always credit card).
        addField("Reason for Refund", rawRecord.substring(81, 83));
        addField("Authorisation Reference", rawRecord.substring(83, 91));
        addField("Refund Reference", rawRecord.substring(91, 99));
        addField("Original Transaction Number", rawRecord.substring(99, 104));
        addField("Issuing Machine", rawRecord.substring(104, 108));
        addField("Issuing Retailer NLC", expandNLC(rawRecord.substring(108, 112)));
        addField("Issuing Window", rawRecord.substring(112, 114));
        addField("Issue Date", DATE_FORMAT.parse(rawRecord.substring(114, 122)));
        addField("Refund Transaction Number", rawRecord.substring(122, 127));
        addField("Owning Transaction Header Number", rawRecord.substring(127, 132));
        addField("Refund Time", rawRecord.substring(132, 136));
    }


    @Override
    public String toString()
    {
        return "Refund #" + getFields().get("Refund Transaction Number") + " (" + getFields().get("Refund Amount") + ")";
    }
}
