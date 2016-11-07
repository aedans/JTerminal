package com.aedan.jterminal.utils;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * utility class for accessing files.
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
     * Creates a File.
     *
     * @param file The File to make.
     * @return The output of the function.
     */
    public static String createFile(File file) throws FileIOException {
        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                return "Created file at " + file.toString();
            } catch (IOException e) {
                throw new FileIOException("Could not create file (Unknown cause)");
            }
        } else {
            throw new FileIOException("File " + file.toString() + " already exists");
        }
    }

    /**
     * Writes a String to a File.
     *
     * @param file The File to write to.
     * @param s    The String to write to the File.
     * @return The output of the function.
     * @throws FileIOException if the function fails.
     */
    public static String writeToFile(File file, String s) throws FileIOException {
        String out = "";
        if (!file.exists()) {
            out += createFile(file);
        }
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(s);
            ps.close();
            return out;
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
        return FileUtils.readFile(file, false);
    }

    /**
     * Returns a stream of lines
     *
     * @param file file to read lines from
     * @return a stream of lines
     * @throws FileIOException if file cannot be read
     */
    public static Stream<String> readLines(File file) throws FileIOException {
        try {
            if(!file.exists()){
                throw new FileIOException("File does not exist");
            }
            if(!file.isFile()){
                throw new FileIOException("Cannot read lines from a directory");
            }
            if(!file.canRead()){
                throw new FileIOException("Unable to read from file \"" + file + "\"");
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return reader.lines();
        } catch (FileNotFoundException fnfe){
            throw new FileIOException("Unable to open file, does not exist");
        }
    }

    /**
     * Returns the content of a File.
     *
     * @param file  The File to read.
     * @param bytes True if the reader should read raw byte data.
     * @return The content of the File.
     * @throws FileIOException if the File cannot be read.
     */
    public static String readFile(File file, boolean bytes) throws FileIOException {
        try {
            if (!file.exists()) {
                throw new FileIOException("File " + file.getAbsolutePath() + " does not exist");
            }
            if (!file.isFile()) {
                throw new FileIOException(file.getAbsolutePath() + " is not a file");
            }
            if (!file.canRead()) {
                throw new FileIOException(file.getAbsolutePath() + " is not readable");
            }
            if (!bytes) {
                final String[] content = {""};
                new BufferedReader(new FileReader(file)).lines().forEach(s -> content[0] += s + '\n');
                return content[0];
            } else {
                return new String(Files.readAllBytes(file.toPath()));
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
                throw new FileIOException("Directory at " + file.getAbsolutePath() + " is not a file");
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
                throw new FileIOException("File " + file.getAbsolutePath() + " is not a directory");
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
