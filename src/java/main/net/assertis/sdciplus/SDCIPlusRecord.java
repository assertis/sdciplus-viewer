package net.assertis.sdciplus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

/**
 * Abstract base class for all record types.
 * @author Daniel Dyer
 */
public abstract class SDCIPlusRecord
{
    protected static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("ddMMyyyyHHmm");
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");
    protected static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("£#########0.00");

    private final Map<String, Object> fields = new LinkedHashMap<String, Object>();


    protected void addField(String name, Object value)
    {
        fields.put(name, value);
    }


    /**
     * @return A read-only view of this record's important fields.
     */
    public Map<String, Object> getFields()
    {
        return Collections.unmodifiableMap(fields);
    }


    /**
     * Converts a string containing a number of pence into a properly-formatted
     * amount in pounds Sterling.
     */
    protected String formatCurrencyField(String currencyField)
    {
        return CURRENCY_FORMAT.format(Long.parseLong(currencyField) / 100.0d);        
    }
}
