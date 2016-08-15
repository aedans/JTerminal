package com.aedan.jterminal;

import com.aedan.jterminal.commands.CommandHandler;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Utility class for managing the CommandHandler directory.
 */

public class Directory {

    /**
     * The current Directory.
     */
    private File directory;

    /**
     * The default Directory constructor.
     */
    public Directory(){
        this.directory = new File(String.valueOf(Paths.get("").toAbsolutePath()));
    }

    /**
     * Goes to the directory containing the current directory.
     */
    public void goToSuperDirectory() {
        if (directory.getParent() != null){
            directory = directory.getParentFile();
        }
    }

    /**
     * Goes to a Directory.
     *
     * @param dir: The Directory to go to.
     * @throws DirectoryChangeException if the Directory cannot be changed to.
     */
    public void goToDirectory(String dir) throws DirectoryChangeException {
        File directory = new File(dir);
        if (directory.exists()){
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
     * Exception thrown if an error occurs whilst changing Directory
     */
    public class DirectoryChangeException extends CommandHandler.CommandHandlerException {

        /**
         * The default DirectoryChangeException constructor.
         *
         * @param message : The error message to display.
         */
        public DirectoryChangeException(String message) {
            super(message);
        }

    }

}
