package com.aedan.jterminal.commands;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.Directory;
import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.default_package.DefaultPackage;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/10/16.
 *
 * The CommandHandler for the JTerminal
 */

public class CommandHandler {

    /**
     * The List of Commands for the CommandHandler to Handle.
     */
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * The current Directory of the CommandHandler.
     */
    private Directory directory = new Directory();

    /**
     * The default CommandHandler constructor.
     */
    public CommandHandler(){
        this(new DefaultPackage());
    }

    /**
     * CommandHandler constructor for adding custom packages.
     *
     * @param commandPackages: The Packages to include.
     */
    public CommandHandler(CommandPackage... commandPackages){
        for (CommandPackage commandPackage : commandPackages){
            addPackage(commandPackage);
        }
    }

    /**
     * Handles a line of input.
     *
     * @param in : The String to handle.
     * @param output : The Output to output to.
     * @throws CommandHandlerException if there is an error handling the String.
     */
    public void handleString(String in, Output output) throws CommandHandlerException {
        String identifier = in.split(" ")[0].toLowerCase();
        for (Command command : commands){
            if (Objects.equals(command.getIdentifier(), identifier)){
                command.parse(in, directory, output);
                return;
            }
        }

        throw new CommandHandlerException("Unrecognized Command \"" + identifier + "\"");
    }

    /**
     * Adds a CommandPackage to the CommandHandler.
     *
     * @param commandPackage: The CommandPackage to add.
     */
    private void addPackage(CommandPackage commandPackage) {
        commandPackage.addCommands(this);
    }

    /**
     * Adds a Command to the CommandHandler
     *
     * @param command: The Command to add.
     */
    public void addCommand(Command command){
        commands.add(command);
        commands.sort((o1, o2) -> o2.getIdentifier().length() - o1.getIdentifier().length());
    }

    /**
     * An Exception thrown when there is an error handling a Command.
     */
    public static class CommandHandlerException extends Exception {

        /**
         * The default CommandHandlerException constructor.
         *
         * @param message: The error message to display.
         */
        public CommandHandlerException(String message){
            super(message);
        }

    }

    public Directory getDirectory() {
        return directory;
    }

    @NotNull
    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

}
