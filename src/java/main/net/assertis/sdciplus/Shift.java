package net.assertis.sdciplus;

import java.util.List;
import java.util.Collections;

/**
 * @author Daniel Dyer
 */
public class Shift
{
    private final ShiftHeaderRecord headerRecord;
    private final SoftwareVersionRecord versionRecord;
    private final ShiftTrailerRecord trailerRecord;
    private final NullShiftRecord nullShiftRecord;
    private final List<Transaction> transactions;


    public Shift(ShiftHeaderRecord headerRecord,
                 SoftwareVersionRecord versionRecord,
                 ShiftTrailerRecord trailerRecord,
                 List<Transaction> transactions)
    {
        this(headerRecord, versionRecord, trailerRecord, null, transactions);
    }


    public Shift(NullShiftRecord nullShiftRecord)
    {
        this(null, null, null, nullShiftRecord, Collections.<Transaction>emptyList());
    }


    private Shift(ShiftHeaderRecord headerRecord,
                  SoftwareVersionRecord versionRecord,
                  ShiftTrailerRecord trailerRecord,
                  NullShiftRecord nullShiftRecord,
                  List<Transaction> transactions)
    {
        this.headerRecord = headerRecord;
        this.versionRecord = versionRecord;
        this.trailerRecord = trailerRecord;
        this.nullShiftRecord = nullShiftRecord;
        this.transactions = transactions;
    }


    public boolean isNullShift()
    {
        return this.nullShiftRecord != null;
    }


    public ShiftHeaderRecord getHeaderRecord()
    {
        return headerRecord;
    }


    public SoftwareVersionRecord getVersionRecord()
    {
        return versionRecord;
    }


    public ShiftTrailerRecord getTrailerRecord()
    {
        return trailerRecord;
    }


    public NullShiftRecord getNullShiftRecord()
    {
        return nullShiftRecord;
    }


    public List<Transaction> getTransactions()
    {
        return Collections.unmodifiableList(transactions);
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (isNullShift())
        {
            builder.append("Null Shift");
            builder.append(" (Machine ");
            builder.append(nullShiftRecord.getFields().get("Machine Number"));
            builder.append(", Window ");
            builder.append(nullShiftRecord.getFields().get("Window Number"));
            builder.append(')');
        }
        else
        {
            builder.append("Shift ");
            builder.append(headerRecord.getFields().get("Shift Number"));
            builder.append(" (Machine ");
            builder.append(headerRecord.getFields().get("Machine Number"));
            builder.append(", Window ");
            builder.append(headerRecord.getFields().get("Window Number"));
            builder.append(')');
        }
        return builder.toString();
    }
}
