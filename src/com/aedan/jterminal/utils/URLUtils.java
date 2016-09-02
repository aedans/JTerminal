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
     * Loads the HTML at a given URL.
     *
     * @param sUrl The URL to load the HTML from.
     * @return The HTML at the URL.
     * @throws IOException if there is an error loading the URL HTML.
     */
    public static String loadURLHTML(String sUrl) throws IOException {
        return loadURLHTML(connectToURL(sUrl));
    }

    /**
     * Loads the HTML from a given URLConnection.
     *
     * @param connection The connection to the URL.
     * @return The HTML returned from the URLConnection.
     * @throws IOException if there is an error loading the URL HTML.
     */
    public static String loadURLHTML(URLConnection connection) throws IOException {
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
     * @return The URLConnection.
     * @throws IOException if unable to connect to the URL.
     */
    public static URLConnection connectToURL(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        return url.openConnection();
    }

}
