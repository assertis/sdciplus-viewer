package net.assertis.sdciplus;

import java.util.List;
import java.util.Collections;

/**
 * A transaction includes all ticket/sundry issues/non-issues, refunds
 * and payments that belong to a single transaction header.
 */
public class Transaction
{
    private final TransactionHeader headerRecord;
    private final List<SDCIPlusRecord> records;

    // Flag this transaction as somehow incorrect.
    private boolean invalid = false;


    public Transaction(TransactionHeader headerRecord,
                       List<SDCIPlusRecord> records)
    {
        this.headerRecord = headerRecord;
        this.records = records;
    }


    public TransactionHeader getHeaderRecord()
    {
        return headerRecord;
    }


    public List<SDCIPlusRecord> getRecords()
    {
        return Collections.unmodifiableList(records);
    }


    public String getTransactionHeaderNumber()
    {
        return getHeaderRecord().getTransactionHeaderNumber();
    }


    public String getPreviousTransactionHeaderNumber()
    {
        String current = getTransactionHeaderNumber();
        if (current.equals("00000"))
        {
            return "99999";
        }
        else
        {
            return String.format("%05d", Integer.parseInt(current) - 1);
        }
    }


    @Override
    public String toString()
    {
        return "Transaction #" + getTransactionHeaderNumber();
    }


    public void setInvalid(boolean invalid)
    {
        this.invalid = invalid;
    }


    public boolean isInvalid()
    {
        return this.invalid;
    }
}
