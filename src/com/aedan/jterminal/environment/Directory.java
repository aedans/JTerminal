package com.aedan.jterminal.environment;

import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.environment.variables.Variable;
import com.aedan.jterminal.utils.Patterns;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * utility class for managing the CommandHandler path.
 */

public class Directory implements Variable {

    /**
     * The current Directory.
     */
    private Path path;

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
        this.path = Paths.get(directory).toAbsolutePath();
    }

    /**
     * Returns the File of the given String relative to the Directory.
     *
     * @param dir The String to get the Directory of.
     * @return The Directory File.
     * @throws DirectoryFormatException if the String is not a valid Directory.
     */
    public File getFile(String dir) throws DirectoryFormatException {
        return getPath(dir).toFile();
    }

    /**
     * Returns a Path of the given String relative to the Directory.
     *
     * @param dir The String to get the Path of.
     * @return The Directory Path.
     * @throws DirectoryFormatException if the String is not a valid Directory.
     */
    public Path getPath(String dir) throws DirectoryFormatException {
        if (Objects.equals(dir.trim(), "..")) {
            return path.getParent();
        } else if (Objects.equals(dir.trim(), ".")) {
            return path;
        } else if (dir.matches(Patterns.absoluteDirectoryPattern.pattern())) {
            return Paths.get(dir);
        } else {
            try {
                return Paths.get(path.toString(), dir).toAbsolutePath();
            } catch (InvalidPathException e) {
                throw new DirectoryFormatException("Invalid Directory format: " + dir);
            }
        }
    }

    public Path getPath() {
        return path;
    }

    /**
     * Goes to a Directory.
     *
     * @param path The Directory to go to.
     * @throws DirectoryChangeException if the Directory cannot be changed to.
     */
    public void setPath(Path path) throws DirectoryChangeException {
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                this.path = path;
            } else {
                throw new DirectoryChangeException("File \"" + this.path.toString() + "\" is not a path");
            }
        } else {
            throw new DirectoryChangeException("Directory \"" + this.path.toString() + "\" does not exist");
        }
    }

    public File getFile() {
        return path.toFile();
    }

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public String getName() {
        return "PATH";
    }

    @Override
    public String getValue() {
        return path.toString();
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
