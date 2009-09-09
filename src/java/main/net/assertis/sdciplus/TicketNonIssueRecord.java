package net.assertis.sdciplus;

/**
 * @author Daniel Dyer
 */
public class TicketNonIssueRecord extends TicketIssueRecord
{
    public TicketNonIssueRecord(String rawRecord)
    {
        super(rawRecord);
    }

    @Override
    public String toString()
    {
        return "Ticket Non-Issue";
    }
}
