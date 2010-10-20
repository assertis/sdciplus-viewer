package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class CardPaymentRecord extends SDCIPlusRecord
{
    public CardPaymentRecord(String rawRecord) throws ParseException
    {
        addField("Merchant Number", rawRecord.substring(0, 11));
        // Omit credit card number (19 chars).  Just because LENNON stores it insecurely, doesn't mean we
        // should show it to everybody.
        addField("Transaction Number", rawRecord.substring(30, 35));
        // Ignore other card details (15 chars).
        addField("Payment Amount", formatCurrencyField(rawRecord.substring(50, 60)));
        // Ignore ATM/POS code (1 char) always 4 (no card reader).
        addField("Debit/Credit", rawRecord.charAt(61) == '1' ? 'D' : 'C'); // Change from 1/2 to D/C to be consistent with other records.
        addField("Date", EXTENDED_DATE_TIME_FORMAT.parse(rawRecord.substring(62, 76)));
        addField("Owning Transaction Header Number", rawRecord.substring(76, 81));
        addField("Customer Instruction Field", rawRecord.substring(49,50));
        addField("Business Code", rawRecord.substring(81, 83));
        addField("Origin NLC", rawRecord.substring(83, 90));
        addField("Destination NLC", rawRecord.substring(90, 97));
        addField("CTOT", rawRecord.substring(97, 102));
    }


    @Override
    public String toString()
    {
        return "Card Payment #" + getFields().get("Transaction Number") + " (" + getFields().get("Payment Amount") + ')';
    }
}
