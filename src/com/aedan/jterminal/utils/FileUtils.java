package com.aedan.jterminal.utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Utility class for accessing files.
 */

public final class FileUtils {

    /**
     * Opens a File with the OS default file opener.
     *
     * @param file The File to open.
     * @return The output of the function.
     * @throws FileIOException if the file could not be opened.
     */
    public static String open(File file) throws FileIOException {
        try {
            try {
                Desktop.getDesktop().open(file);
                return "Opened file " + file.getAbsolutePath();
            } catch (IOException e) {
                Desktop.getDesktop().edit(file);
                return "Opened file " + file.getAbsolutePath();
            }
        } catch (Exception e) {
            throw new FileIOException(e.getMessage());
        }
    }

    /**
     * Returns the content of a File.
     *
     * @param file The File to read.
     * @return The content of the File.
     * @throws FileIOException if the File cannot be read.
     */
    public static String readFile(File file) throws FileIOException {
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    if (file.canRead()) {
                        String s = "";
                        BufferedReader buffer = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = buffer.readLine()) != null) {
                            s += line + "\n";
                        }
                        buffer.close();
                        return s;
                    } else {
                        throw new FileIOException(file.getAbsolutePath() + " is not readable");
                    }
                } else {
                    throw new FileIOException(file.getAbsolutePath() + " is not a file");
                }
            } else {
                throw new FileIOException("File " + file.getAbsolutePath() + " does not exist");
            }
        } catch (Exception e) {
            throw new FileIOException(e.getMessage());
        }
    }

    /**
     * Removes a File.
     *
     * @param file The File to remove.
     * @return The output of the function.
     * @throws FileIOException if the File cannot be removed.
     */
    public static String removeFile(File file) throws FileIOException {
        if (file.exists()) {
            if (file.isFile()) {
                if (file.delete()) {
                    return "Deleted file at " + file.getAbsolutePath();
                } else {
                    throw new FileIOException("Could not delete file " + file.getAbsolutePath() + " (Unknown cause)");
                }
            } else {
                throw new FileIOException("Directory at " + file.getAbsolutePath() + " is not a file.");
            }
        } else {
            throw new FileIOException("File " + file.getAbsolutePath() + " does not exist");
        }
    }

    /**
     * Creates a directory.
     *
     * @param file The directory File to be created.
     * @return The output of the function.
     * @throws FileIOException if the directory cannot be created.
     */
    public static String createDirectory(File file) throws FileIOException {
        if (!file.exists()) {
            if (file.mkdir()) {
                return "Created directory at " + file.getAbsolutePath();
            } else {
                throw new FileIOException("Could not create directory at " + file.getAbsolutePath() + " (Unknown cause)");
            }
        } else {
            throw new FileIOException("File " + file.getAbsolutePath() + " already exists");
        }
    }

    /**
     * Removes a directory.
     *
     * @param file The directory File to be removed.
     * @return The output of the function.
     * @throws FileIOException if the directory cannot be removed.
     */
    public static String removeDirectory(File file) throws FileIOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                return removeDirectoryR(file).trim();
            } else {
                throw new FileIOException("File " + file.getAbsolutePath() + " is not a directory.");
            }
        } else {
            throw new FileIOException("File " + file.getAbsolutePath() + " does not exist");
        }
    }

    /**
     * Removes a directory recursively.
     *
     * @param file The directory File to be removed.
     * @return The output of the function.
     */
    private static String removeDirectoryR(File file) {
        String s = "";
        File[] subfs = file.listFiles();
        if (subfs == null)
            if (file.delete()) {
                return s + "Removed file at " + file.getAbsolutePath() + "\n";
            } else {
                return s + "Could not remove file at " + file.getAbsolutePath() + "\n";
            }
        else {
            for (File subf : subfs) {
                s += removeDirectoryR(subf);
            }
            if (file.delete()) {
                return s + "Removed directory at " + file.getAbsolutePath() + "\n";
            } else {
                return s + "Could not remove directory at " + file.getAbsolutePath() + "\n";
            }
        }
    }

    /**
     * Exception thrown when FileUtils cannot perform an action.
     */
    public static class FileIOException extends Exception {

        /**
         * Default FileIOException constructor.
         *
         * @param message The error message to display.
         */
        FileIOException(String message) {
            super(message);
        }

    }

}
