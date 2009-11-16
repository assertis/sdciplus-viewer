package net.assertis.sdciplus;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.GregorianCalendar;
import org.testng.annotations.Test;

/**
 * Unit test for the {@link SoftwareVersionRecordTest}.
 */
public class SoftwareVersionRecordTest
{
    @Test
    public void testDateField() throws ParseException
    {
        String rawRecord = "01002120091021163000008672"; // Record excludes record type field ("2C").
        SoftwareVersionRecord record = new SoftwareVersionRecord(rawRecord);
        Map<String, Object> fields = record.getFields();
        Date date = (Date) fields.get("Installation Date");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        assert calendar.get(Calendar.YEAR) == 2009 : "Wrong year: " + date;
        assert calendar.get(Calendar.MONTH) == 9 : "Wrong month: " + date; // Month is zero-indexed (9=October).
        assert calendar.get(Calendar.DAY_OF_MONTH) == 21 : "Wrong day: " + date;
    }
}
