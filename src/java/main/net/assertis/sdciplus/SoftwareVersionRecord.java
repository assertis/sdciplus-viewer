package net.assertis.sdciplus;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Daniel Dyer
 */
public class SoftwareVersionRecord extends SDCIPlusRecord
{
    // Reverse date format is used for software version records.
    private static final DateFormat VERSION_RECORD_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public SoftwareVersionRecord(String rawRecord) throws ParseException
    {
        String major = rawRecord.substring(0, 2);
        String minor = rawRecord.substring(2, 4);
        String micro = rawRecord.substring(4, 6);
        String version = major + '.' + minor + '.' + micro;
        addField("Version", version);
        addField("Installation Date", VERSION_RECORD_DATE_FORMAT.parse(rawRecord.substring(6, 18)));
    }


    @Override
    public String toString()
    {
        return "Software Version " + getFields().get("Version");
    }
}
