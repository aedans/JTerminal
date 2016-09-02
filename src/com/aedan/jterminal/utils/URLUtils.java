package com.aedan.jterminal.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Aedan Smith on 7/1/2016.
 *
 * Utility class for loading URLs.
 */

public class URLUtils {

    /**
     * Loads the data at a given URL.
     *
     * @param sUrl The URL to load the data from.
     * @return The data at the URL.
     * @throws IOException if there is an error loading the URL data.
     */
    public static String loadURLData(String sUrl) throws IOException {
        return loadURLData(connectToURL(sUrl));
    }

    /**
     * Loads the data from a given URLConnection.
     *
     * @param connection The connection to the URL.
     * @return The data returned from the URLConnection.
     * @throws IOException if there is an error loading the URL data.
     */
    public static String loadURLData(URLConnection connection) throws IOException {
        String s = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            s += inputLine;
        in.close();
        connection.getInputStream().close();
        return s;
    }

    /**
     * Connects to a given URL.
     *
     * @param sUrl The URL to connect to.
     * @return The URLConnection to the given URL.
     * @throws IOException if unable to connect to the URL.
     */
    public static URLConnection connectToURL(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        return url.openConnection();
    }

}
