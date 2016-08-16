package com.aedan.jterminal.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Utility class for accessing files.
 */

public final class FileUtils {

    /**
     * Opens a file with the OS default file opener.
     *
     * @param file: The File to open.
     * @return String: The output of the function.
     * @throws Exception if the file could not be opened.
     */
    public static String open(File file) throws Exception {
        try {
            Desktop.getDesktop().open(file);
            return "Opened file " + file.getAbsolutePath();
        } catch (IOException e) {
            Desktop.getDesktop().edit(file);
            return "Opened file " + file.getAbsolutePath();
        }
    }

    /**
     * Creates a directory.
     *
     * @param file: The directory File to be created.
     * @return String: The output of the function.
     */
    public static String createDirectory(File file) throws Exception {
        if (!file.exists()) {
            if (file.mkdir()) {
                return "Created directory at " + file.getAbsolutePath();
            } else {
                return "Could not create directory at " + file.getAbsolutePath() + " (Unknown cause)";
            }
        } else {
            return "File " + file.getAbsolutePath() + " already exists.";
        }
    }

    /**
     * Removes a directory
     *
     * @param file: The directory File to be removed.
     * @return String: The output of the function.
     */
    public static String removeDirectory(File file) throws Exception {
        return removeDirectoryR(file).trim();
    }

    /**
     * Removes a directory recursively.
     *
     * @param file: The directory File to be removed.
     * @return String: The output of the function.
     */
    private static String removeDirectoryR(File file) throws Exception {
        String s = "";
        File[] subfs = file.listFiles();
        if (subfs == null)
            if (file.delete()){
                return s + "Removed file at " + file.getAbsolutePath() + "\n";
            } else {
                return s + "Could not remove file at " + file.getAbsolutePath() + "\n";
            }
        else {
            for (File subf : subfs) {
                s += removeDirectoryR(subf);
            }
            if (file.delete()){
                return s + "Removed directory at " + file.getAbsolutePath() + "\n";
            } else {
                return s + "Could not remove directory at " + file.getAbsolutePath() + "\n";
            }
        }
    }

}
