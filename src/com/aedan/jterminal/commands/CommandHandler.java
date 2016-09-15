package com.aedan.jterminal.commands;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.variables.GlobalVariable;
import com.aedan.jterminal.environment.variables.Variable;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.Patterns;
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
     * The current List of String literals.
     */
    private ArrayList<String> stringLiterals = new ArrayList<>();

    /**
     * The current List of embedded Commands.
     */
    private ArrayList<String> embeddedCommands = new ArrayList<>();

    /**
     * The Environment containing the CommandHandler.
     */
    private Environment environment;

    /**
     * Default CommandHandler constructor.
     *
     * @param environment The Environment containing the CommandHandler.
     */
    public CommandHandler(Environment environment) {
        this.environment = environment;
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
        try {
            handleInput(input, input.nextLine(), output);
        } finally {
            embeddedCommands = new ArrayList<>();
            stringLiterals = new ArrayList<>();
        }
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
            stringLiterals.add(m.group(1).replaceAll("\'", "\""));
        }

        // Tokenizes Embedded Commands.
        m = Patterns.embeddedCommandPattern.matcher(in);
        while (m.find()) {
            in = in.replace("{" + m.group(1) + "}", "~" + embeddedCommands.size());
            embeddedCommands.add(m.group(1));
        }

        // Handles CommandFormats
        for (CommandFormat commandFormat : environment.getCommandFormats()) {
            if (commandFormat.matches(in)) {
                commandFormat.handleInput(environment, input, in, output);
                return;
            }
        }

        // Splits input
        String[] args = in.split(" ");
        String identifier = args[0].toLowerCase();
        for (Command command : environment.getCommands()) {
            if (Objects.equals(command.getIdentifier(), identifier)) {
                // Computes input
                for (int i = 0; i < args.length; i++) {
                    args[i] = compute(input, args[i]);
                }

                // Handles command
                command.parse(input, new CommandArgumentList(args), environment, output);
                return;
            }
        }

        throw new CommandHandlerException("Unrecognized Command \"" + identifier + "\"");
    }

    /**
     * Injects Embedded Commands, Variables, and String literals into a String.
     *
     * @param input The CommandInput for the CommandHandler.
     * @param command The String to compute.
     * @return The computer String.
     * @throws CommandHandlerException if there is an error computing the String.
     */
    public String compute(CommandInput input, String command) throws CommandHandlerException {
        command += " ";

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
            if (command.contains("~" + j)) {
                handleInput(input, embeddedCommands.get(j), o2);
                command = command.replace("~" + j + " ", s[0].trim());
                s[0] = "";
            }
        }

        // Injects global Variables
        for (GlobalVariable variable : environment.getGlobalVariables()) {
            command = command.replace(
                    "[" + variable.getName() + "]",
                    variable.getValue()
            );
        }

        // Injects environment Variables
        for (Variable variable : environment.getEnvironmentVariables()) {
            command = command.replace(
                    "%" + variable.getName() + "%",
                    variable.getValue()
            );
        }

        // Injects String literals
        for (int j = 0; j < stringLiterals.size(); j++) {
            command = command.replace("&" + j + " ", stringLiterals.get(j));
        }

        return command.trim();
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
