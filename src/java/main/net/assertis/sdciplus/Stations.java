package net.assertis.sdciplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for mapping NLC codes to station names.
 * @author Daniel Dyer
 */
public class Stations
{
    private static final Map<String, String> STATION_NAMES_BY_NLC = new HashMap<String, String>();

    static
    {
        try
        {
            InputStream stationsStream = Stations.class.getResourceAsStream("stations.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stationsStream));
            for (String line = reader.readLine(); line != null; line = reader.readLine())
            {
                String nlc = line.substring(0, 4); // First 4 chars are NLC (UIC4).
                String name = line.substring(5); // Name is after tab separator.
                STATION_NAMES_BY_NLC.put(nlc, name);
            }
        }
        catch (IOException ex)
        {
            throw new IllegalStateException("Failed to load stations list.", ex);
        }
    }


    private Stations()
    {
        // Private constructor prevents instantiation of utility class.
    }

    public static String getStationName(String nlc)
    {
        return STATION_NAMES_BY_NLC.get(nlc);
    }
}
