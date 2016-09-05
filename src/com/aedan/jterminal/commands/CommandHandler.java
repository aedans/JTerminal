package com.aedan.jterminal.commands;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.Patterns;
import com.aedan.jterminal.variables.Variable;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

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
     * The current List of String literals.
     */
    private ArrayList<String> stringLiterals = new ArrayList<>();

    /**
     * The current List of embedded Commands.
     */
    private ArrayList<String> embeddedCommands = new ArrayList<>();

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
     * @param output The CommandOutput to output to.
     * @throws CommandHandlerException if there is an error handling the Input.
     */
    @NotNull
    public void handleInput(CommandInput input, CommandOutput output) throws CommandHandlerException {
        handleInput(input, input.nextLine(), output);
    }

    /**
     * Handles a line of input.
     *
     * @param input  The String to handle.
     * @param in     The String input.
     * @param output The CommandOutput to output to.
     * @throws CommandHandlerException if there is an error handling the Input.
     */
    @NotNull
    public void handleInput(CommandInput input, String in, CommandOutput output) throws CommandHandlerException {
        // Validates input
        if (in == null) {
            stringLiterals = new ArrayList<>();
            throw new CommandHandlerException("Input is null");
        }

        if (Objects.equals(in, "")) {
            return;
        }

        // Tokenizes Strings
        Matcher m = Patterns.stringLiteralPattern.matcher(in);
        while (m.find()) {
            in = in.replace(m.group(), "&" + stringLiterals.size());
            stringLiterals.add(m.group(1));
        }

        // Tokenizes Embedded Commands.
        m = Patterns.embeddedCommandPattern.matcher(in);
        while (m.find()) {
            in = in.replace("{" + m.group(1) + "}", "~" + embeddedCommands.size());
            embeddedCommands.add(m.group(1));
        }

        // Handles CommandFormats
        for (CommandFormat commandFormat : commandFormats) {
            if (commandFormat.matches(in)) {
                commandFormat.handleInput(this, input, in, output);
                stringLiterals = new ArrayList<>();
                return;
            }
        }

        // Splits input
        String[] args = in.split(" ");
        String identifier = args[0].toLowerCase();
        for (Command command : commands) {
            if (Objects.equals(command.getIdentifier(), identifier)) {
                for (int i = 0; i < args.length; i++) {
                    // Injects String literals
                    for (int j = 0; j < stringLiterals.size(); j++) {
                        args[i] = args[i].replace("&" + j, stringLiterals.get(j));
                    }

                    // Injects embedded Commands
                    final String[] s = {""};
                    OutputStream os = new OutputStream() {
                        @Override
                        public void write(int b) throws IOException {
                            s[0] += (char) b;
                        }
                    };
                    PrintStream ps = new PrintStream(os);
                    CommandOutput o2 = new CommandOutput(ps);
                    for (int j = 0; j < embeddedCommands.size(); j++) {
                        if (args[i].contains("~" + j)) {
                            handleInput(input, embeddedCommands.get(j), o2);
                            args[i] = args[i].replace("~" + j, s[0]);
                            s[0] = "";
                        }
                    }

                    // Injects Variables
                    for (Variable globalVariable : globalVariables) {
                        args[i] = args[i].replaceAll(
                                "\\[" + globalVariable.name + "\\]",
                                globalVariable.value
                        );
                    }
                }

                // Handles command
                command.parse(input, new CommandArgumentList(args), directory, output);
                stringLiterals = new ArrayList<>();
                return;
            }
        }

        stringLiterals = new ArrayList<>();
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
        for (Variable v : globalVariables) {
            if (Objects.equals(v.name, name)) {
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
     * Adds a Command to the CommandHandler.
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
