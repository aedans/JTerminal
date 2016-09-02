package com.aedan.jterminal;

import com.aedan.jterminal.commands.CommandHandler;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * utility class for managing the CommandHandler directory.
 */

public class Directory {

    /**
     * The current Directory.
     */
    private File directory;

    /**
     * The default Directory constructor.
     */
    public Directory() {
        this("");
    }

    /**
     * Directory constructor to create a Directory at a given path.
     *
     * @param directory The path of the Directory.
     */
    public Directory(String directory) {
        this.directory = new File(String.valueOf(Paths.get(directory).toAbsolutePath()));
    }

    /**
     * Returns a File with directory of the given String relative to the Directory.
     *
     * @param dir The String to get the Directory of.
     * @return The Directory File.
     * @throws DirectoryFormatException if the String is not a valid Directory.
     */
    public File getFile(String dir) throws DirectoryFormatException {
        if (Objects.equals(dir.trim(), "..")) {
            return directory.getParentFile();
        } else if (Objects.equals(dir.trim(), ".")) {
            return directory;
        } else if (dir.matches("\\w:.+|/.+|\\\\.+")) {
            return new File(dir);
        } else {
            try {
                Path p = Paths.get(directory.getPath(), dir).toAbsolutePath();
                return new File(p.toString());
            } catch (InvalidPathException e) {
                throw new DirectoryFormatException("Invalid Directory format: " + dir);
            }
        }
    }

    public File getDirectory() {
        return directory;
    }

    /**
     * Goes to a Directory.
     *
     * @param directory The Directory to go to.
     * @throws DirectoryChangeException if the Directory cannot be changed to.
     */
    public void setDirectory(File directory) throws DirectoryChangeException {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                this.directory = directory;
            } else {
                throw new DirectoryChangeException("File \"" + directory.getAbsolutePath() + "\" is not a directory");
            }
        } else {
            throw new DirectoryChangeException("Directory \"" + directory.getAbsolutePath() + "\" does not exist");
        }
    }

    @Override
    public String toString() {
        return directory.toString();
    }

    /**
     * Exception thrown if a Directory is in an invalid format.
     */
    public class DirectoryFormatException extends CommandHandler.CommandHandlerException {

        /**
         * The default DirectoryFormatException constructor.
         *
         * @param message The error message to display.
         */
        DirectoryFormatException(String message) {
            super(message);
        }

    }

    /**
     * Exception thrown if an error occurs whilst changing Directory.
     */
    public class DirectoryChangeException extends CommandHandler.CommandHandlerException {

        /**
         * The default DirectoryChangeException constructor.
         *
         * @param message The error message to display.
         */
        DirectoryChangeException(String message) {
            super(message);
        }

    }

}
