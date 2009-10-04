package net.assertis.sdciplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.assertis.util.ReflectionUtils;

/**
 * @author Daniel Dyer
 */
public class SDCIPlusReader
{
    private static final Map<String, Class<? extends SDCIPlusRecord>> RECORD_CLASSES
        = new HashMap<String, Class<? extends SDCIPlusRecord>>();
    static
    {
        RECORD_CLASSES.put("2C", SoftwareVersionRecord.class);
        RECORD_CLASSES.put("BE", TicketIssueRecord.class);
        RECORD_CLASSES.put("BF", TicketNonIssueRecord.class);
        RECORD_CLASSES.put("BM", SeasonTicketIssueRecord.class);
        RECORD_CLASSES.put("BN", SeasonTicketNonIssueRecord.class);
        RECORD_CLASSES.put("BP", SundryIssueRecord.class);
        RECORD_CLASSES.put("BR", SundryNonIssueRecord.class);
        RECORD_CLASSES.put("BS", RefundRecord.class);
        RECORD_CLASSES.put("C1", CardPaymentRecord.class);
        RECORD_CLASSES.put("CF", TransactionHeaderRecord.class);
        RECORD_CLASSES.put("DB", ShiftHeaderRecord.class);
        RECORD_CLASSES.put("DD", ShiftTrailerRecord.class);
    }


    public static List<Shift> parseFile(File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder data = new StringBuilder();
        for (String line = reader.readLine(); line != null; line = reader.readLine())
        {
            data.append(line);
        }

        List<SDCIPlusRecord> records = extractRecords(data.toString());
        return extractShifts(records);
    }

    
    private static List<SDCIPlusRecord> extractRecords(String fileContent)
    {
        // Eliminate new lines and split records by record separator char (ASCII 30).
        String[] recordLines = fileContent.replaceAll("\n", "").split("\u001E");

        List<SDCIPlusRecord> records = new ArrayList<SDCIPlusRecord>();
        for (String record : recordLines)
        {
            if (record.length() != 0)
            {
                String recordCode = record.substring(0, 2);
                String recordBody = record.substring(2);
                Class<? extends SDCIPlusRecord> recordClass = RECORD_CLASSES.get(recordCode);
                if (recordClass != null)
                {
                    Constructor<? extends SDCIPlusRecord> constructor = ReflectionUtils.findKnownConstructor(recordClass,
                                                                                                             String.class);
                    records.add(ReflectionUtils.invokeUnchecked(constructor, recordBody));
                }
            }
        }
        return records;
    }


    /**
     * An SDCI+ file may contain multiple shifts.  This method groups records into
     * shifts.
     */
    private static List<Shift> extractShifts(List<SDCIPlusRecord> records)
    {
        List<Shift> shifts = new ArrayList<Shift>();
        int start = 0;
        for (int i = 0; i < records.size(); i++)
        {
            SDCIPlusRecord record = records.get(i);
            if (record instanceof ShiftTrailerRecord)
            {
                shifts.add(new Shift((ShiftHeaderRecord) records.get(start),
                                     (SoftwareVersionRecord) records.get(start + 1),
                                     (ShiftTrailerRecord) record,
                                     extractTransactions(records.subList(start + 2, i))));
                start = i + 1;
            }
        }
        return shifts;
    }


    /**
     * A shift may contain multiple transactions, each with multiple items (ticket
     * issues/non-issues, payments, refunds, etc.).  This method groups records from
     * a shift into transactions.
     */
    private static List<Transaction> extractTransactions(List<SDCIPlusRecord> records)
    {
        List<Transaction> transactions = new ArrayList<Transaction>();

        TransactionHeaderRecord headerRecord = null;
        List<SDCIPlusRecord> transactionRecords = null;
        for (int i = 0; i < records.size(); i++)
        {
            SDCIPlusRecord record = records.get(i);
            if (record instanceof TransactionHeaderRecord)
            {
                if (transactionRecords != null) // Finish any previous transaction.
                {
                    transactions.add(new Transaction(headerRecord, transactionRecords));
                }
                headerRecord = (TransactionHeaderRecord) record;
                transactionRecords = new ArrayList<SDCIPlusRecord>();
            }
            else
            {
                transactionRecords.add(record);               
                if (i == records.size() - 1) // If this is the last record, it must be the end of the transaction.
                {
                    transactions.add(new Transaction(headerRecord, transactionRecords));
                }
            }
        }
        return transactions;
    }
}
