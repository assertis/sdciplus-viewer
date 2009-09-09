package net.assertis.sdciplus;

import java.text.ParseException;

/**
 * @author Daniel Dyer
 */
public class SoftwareVersionRecord extends SDCIPlusRecord
{
    public SoftwareVersionRecord(String rawRecord) throws ParseException
    {
        String major = rawRecord.substring(0, 2);
        String minor = rawRecord.substring(2, 4);
        String micro = rawRecord.substring(4, 6);
        String version = major + '.' + minor + '.' + micro;
        addField("Version", version);
        addField("Installation Date", DATE_TIME_FORMAT.parse(rawRecord.substring(6, 18)));
    }


    @Override
    public String toString()
    {
        return "Software Version " + getFields().get("Version");
    }
}
