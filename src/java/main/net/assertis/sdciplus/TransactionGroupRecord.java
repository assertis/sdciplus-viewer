package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class TransactionGroupRecord extends TransactionHeader
{
    public TransactionGroupRecord(String rawRecord) throws ParseException
    {
        addField("Number of Transactions", Integer.parseInt(rawRecord.substring(0, 2)));
        addField("Number of Payments", Integer.parseInt(rawRecord.substring(2, 4)));
        addField("Transaction Header Number", rawRecord.substring(4, 9));
        addField("Retailer NLC", rawRecord.substring(9, 13));
        addField("Date", DATE_FORMAT.parse(rawRecord.substring(13, 21)));
        // Skip sales type field (is always '1').
        addField("Delivery Type", rawRecord.substring(22, 25));
        // TO DO: Other fields
    }


    @Override
    public String toString()
    {
        return "Transaction Group #" + getFields().get("Transaction Header Number");
    }
}

