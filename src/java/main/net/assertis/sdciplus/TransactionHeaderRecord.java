package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class TransactionHeaderRecord extends SDCIPlusRecord
{
    public TransactionHeaderRecord(String rawRecord) throws ParseException
    {
        addField("Number of Transactions", Integer.parseInt(rawRecord.substring(0, 2)));
        addField("Number of Payments", Integer.parseInt(rawRecord.substring(2, 4)));
        boolean tod = rawRecord.charAt(32) == 'R';
        addField("ToD", tod);
        if (tod)
        {
            addField("Retailer NLC", rawRecord.substring(4, 8));
            addField("CTR Reference", rawRecord.substring(8, 16));
            // Next 4 chars are blank.
            addField("Machine Number", rawRecord.substring(20, 24));
            addField("Date", DATE_FORMAT.parse(rawRecord.substring(24, 32)));
            addField("Machine Type", rawRecord.substring(33, 35));
        }
        addField("Transaction Header Number", rawRecord.substring(35, 40));
    }


    @Override
    public String toString()
    {
        return "Transaction Header #" + getFields().get("Transaction Header Number");
    }
}
