package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class TicketNonIssueRecord extends TicketIssueRecord
{
    public TicketNonIssueRecord(String rawRecord) throws ParseException
    {
        super(rawRecord);
    }

    @Override
    public String toString()
    {
        return "Non-Issued Ticket (No. " + getFields().get("Transaction Number") + "- " + getFields().get("Fare Amount") + ')';
    }
}
