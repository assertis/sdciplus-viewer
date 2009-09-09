package net.assertis.sdciplus;

/**
 * @author Daniel Dyer
 */
public class TicketIssueRecord extends SDCIPlusRecord
{
    public TicketIssueRecord(String rawRecord)
    {
        addField("LENNON Type of Ticket", rawRecord.substring(0, 3));
        addField("Fare Method", rawRecord.substring(3, 4));
    }


    @Override
    public String toString()
    {
        return "Ticket Issue";
    }
}
