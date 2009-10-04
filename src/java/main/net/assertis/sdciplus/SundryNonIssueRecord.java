package net.assertis.sdciplus;

/**
 * @author Daniel Dyer
 */
public class SundryNonIssueRecord extends SundryIssueRecord
{
    public SundryNonIssueRecord(String rawRecord)
    {
        super(rawRecord);
    }


    @Override
    public String toString()
    {
        return "Non-Issued Sundry (No. " + getFields().get("Transaction Number") + "- " + getFields().get("Fare Amount") + ')';
    }
}
