package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class WarrantPaymentRecord extends SDCIPlusRecord
{
    public WarrantPaymentRecord(String rawRecord) throws ParseException
    {
        addField("Warrant Account Number", rawRecord.substring(0, 6));
        addField("Warrant Number", rawRecord.substring(6, 13));
        addField("Payment Amount", formatCurrencyField(rawRecord.substring(13, 23)));
        addField("Currency", rawRecord.charAt(23));
        addField("Debit/Credit", rawRecord.charAt(24) == '1' ? 'D' : 'C'); // Change from 1/2 to D/C to be consistent with other records.
        addField("Owning Transaction Header Number", rawRecord.substring(25, 30));
    }


    @Override
    public String toString()
    {
        return "Warrant Payment (" + getFields().get("Payment Amount") + ')';
    }
}

