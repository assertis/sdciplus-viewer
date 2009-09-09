package net.assertis.sdciplus;

/**
 * @author Daniel Dyer
 */
public class SundryNonIssueRecord extends SDCIPlusRecord
{
    public SundryNonIssueRecord(String rawRecord)
    {
    }


    @Override
    public String toString()
    {
        return "Non-Issued Sundry (No. " + getFields().get("Transaction Number") + "- " + getFields().get("Fare Amount") + ')';
    }
}
