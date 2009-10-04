package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class SeasonTicketNonIssueRecord extends TicketIssueRecord
{
    public SeasonTicketNonIssueRecord(String rawRecord) throws ParseException
    {
        super(rawRecord);
    }

    @Override
    public String toString()
    {
        return "Non-Issued Season Ticket (No. " + getFields().get("Transaction Number") + "- " + getFields().get("Fare Amount") + ')';
    }
}
