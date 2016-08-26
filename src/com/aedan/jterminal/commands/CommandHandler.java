package com.aedan.jterminal.commands;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.Directory;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.variables.Variable;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The CommandHandler for the JTerminal.
 */

public class CommandHandler {

    /**
     * The List of Commands for the CommandHandler to Handle.
     */
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * The List of CommandFormats for the CommandHandler to Handle.
     */
    private ArrayList<CommandFormat> commandFormats = new ArrayList<>();

    /**
     * The List of Variables for the CommandHandler to Parse.
     */
    private ArrayList<Variable> globalVariables = new ArrayList<>();

    /**
     * The List of String literals that the CommandHandler is using.
     */
    private ArrayList<String> stringLiterals = new ArrayList<>();

    /**
     * The current Directory of the CommandHandler.
     */
    private Directory directory = new Directory();

    /**
     * CommandHandler constructor for adding custom packages.
     *
     * @param commandPackages The Packages to include.
     */
    public CommandHandler(CommandPackage... commandPackages) {
        for (CommandPackage commandPackage : commandPackages) {
            addPackage(commandPackage);
        }
    }

    /**
     * Handles a line of input.
     *
     * @param input  The String to handle.
     * @param output The Output to output to.
     * @throws CommandHandlerException if there is an error handling the Input.
     */
    @NotNull
    public void handleInput(CommandInput input, Output output) throws CommandHandlerException {
        handleInput(input, input.nextLine(), output);
    }

    /**
     * Handles a line of input.
     *
     * @param input  The String to handle.
     * @param in     The String input.
     * @param output The Output to output to.
     * @throws CommandHandlerException if there is an error handling the Input.
     */
    @NotNull
    public void handleInput(CommandInput input, String in, Output output) throws CommandHandlerException {
        stringLiterals = new ArrayList<>();

        for (Variable v : globalVariables) {
            in = in.replaceAll("\\[" + v.getName() + "\\]", "\"" + v.getValue() + "\"");
        }

        Matcher m = Pattern.compile("\"([^\"]+)\"").matcher(in);
        while (m.find()) {
            in = in.replace(m.group(), "&" + stringLiterals.size());
            stringLiterals.add(m.group(1));
        }

        for (CommandFormat commandFormat : commandFormats) {
            if (commandFormat.matches(in)) {
                commandFormat.handleInput(this, input, in, output);
                return;
            }
        }

        String[] args = in.split(" ");
        String identifier = args[0].toLowerCase();
        for (Command command : commands) {
            if (Objects.equals(command.getIdentifier(), identifier)) {
                for (int i = 0; i < stringLiterals.size(); i++) {
                    for (int j = 0; j < args.length; j++) {
                        args[j] = args[j].replaceAll("&" + i, stringLiterals.get(i));
                    }
                }

                command.parse(input, args, directory, output);
                return;
            }
        }

        throw new CommandHandlerException("Unrecognized Command \"" + identifier + "\"");
    }

    /**
     * Adds a Variable to the CommandHandler.
     *
     * @param variable The Variable to add.
     */
    @NotNull
    public void addVariable(Variable variable) {
        globalVariables.add(variable);
    }

    /**
     * Removes a Variable from the CommandHandler.
     *
     * @param name The name of the Variable to remove.
     */
    public void removeVariable(String name) {
        Variable n = null;
        for (Variable v : globalVariables){
            if (Objects.equals(v.getName(), name)){
                n = v;
            }
        }
        if (n != null)
            globalVariables.remove(n);
    }

    /**
     * Adds a CommandPackage to the CommandHandler.
     *
     * @param commandPackage The CommandPackage to add.
     */
    @NotNull
    public void addPackage(CommandPackage commandPackage) {
        commandPackage.addCommands(this);
    }

    /**
     * Adds a Command to the CommandHandler
     *
     * @param command The Command to add.
     */
    @NotNull
    public void addCommand(Command command) {
        commands.add(command);
        commands.sort((o1, o2) -> o2.getIdentifier().length() - o1.getIdentifier().length());
    }

    /**
     * Adds a CommandFormat to the CommandHandler.
     *
     * @param commandFormat The CommandFormat to add.
     */
    @NotNull
    public void addCommandFormat(CommandFormat commandFormat) {
        commandFormats.add(commandFormat);
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

    /**
     * An Exception thrown when there is an error handling a Command.
     */
    public static class CommandHandlerException extends Exception {

        /**
         * The default CommandHandlerException constructor.
         *
         * @param message The error message to display.
         */
        public CommandHandlerException(String message) {
            super(message);
        }

    }

}
