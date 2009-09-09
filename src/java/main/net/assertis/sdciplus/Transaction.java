package net.assertis.sdciplus;

import java.util.List;
import java.util.Collections;

/**
 * A transaction includes all ticket/sundry issues/non-issues, refunds
 * and payments that belong to a single transaction header.
 */
public class Transaction
{
    private final TransactionHeaderRecord headerRecord;
    private final List<SDCIPlusRecord> records;


    public Transaction(TransactionHeaderRecord headerRecord,
                       List<SDCIPlusRecord> records)
    {
        this.headerRecord = headerRecord;
        this.records = records;
    }


    public TransactionHeaderRecord getHeaderRecord()
    {
        return headerRecord;
    }


    public List<SDCIPlusRecord> getRecords()
    {
        return Collections.unmodifiableList(records);
    }


    @Override
    public String toString()
    {
        return "Transaction #" + headerRecord.getFields().get("Transaction Header Number");
    }
}
