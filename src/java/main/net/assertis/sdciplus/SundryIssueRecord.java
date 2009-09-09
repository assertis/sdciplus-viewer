package net.assertis.sdciplus;

/**
 * @author Daniel Dyer
 */
public class SundryIssueRecord extends SDCIPlusRecord
{
    public SundryIssueRecord(String rawRecord)
    {
        addField("Fare Method", rawRecord.substring(0, 1));
        addField("Fare Amount", formatCurrencyField(rawRecord.substring(1, 11)));
        // Ignore next 4 chars, currency and method of payment are always the same.
        addField("Transaction Number", rawRecord.substring(15, 20));
        addField("Sundry Transaction Number", rawRecord.substring(20, 25));
        addField("Sundry Code", rawRecord.substring(25, 30));
        addField("Owning Transaction Header Number", rawRecord.substring(30, 35));
        addField("Debit/Credit", rawRecord.charAt(35));
        addField("Number of Sundry Items", Integer.parseInt(rawRecord.substring(36, 40)));
        addField("Issue Time", rawRecord.substring(40, 44));
    }


    @Override
    public String toString()
    {
        return "Sundry #" + getFields().get("Transaction Number") + " (" + getFields().get("Fare Amount") + ')';
    }
}
