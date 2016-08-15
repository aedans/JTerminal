package com.aedan.jterminal.commands;

import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.default_commands.Echo;
import com.aedan.jterminal.commands.default_commands.For;
import com.aedan.jterminal.commands.default_commands.Help;
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
    private String directory = "/";

    /**
     * The default CommandHandler constructor.
     */
    public CommandHandler(){
        this.addCommand(new Echo());
        this.addCommand(new For(this));
        this.addCommand(new Help(this));
    }

    /**
     * Handles a line of input.
     *
     * @param in: The String to handle.
     * @param output: The Output to output to.
     * @throws CommandHandlerException if there is an error handling the String.
     */
    public void handleString(String in, Output output) throws CommandHandlerException {
        String identifier = in.split(" ")[0];
        for (Command command : commands){
            if (Objects.equals(command.getIdentifier(), identifier)){
                command.parse(in, directory, output);
                return;
            }
        }

        throw new CommandHandlerException("Unrecognized Command \"" + identifier + "\"");
    }

    /**
     * Adds a Command to the CommandHandler
     *
     * @param command: The Command to add.
     */
    public void addCommand(Command command){
        if (command != null){
            commands.add(command);
            commands.sort((o1, o2) -> o2.getIdentifier().length() - o1.getIdentifier().length());
        }
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

    public String getDirectory() {
        return directory;
    }

    @NotNull
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

}
