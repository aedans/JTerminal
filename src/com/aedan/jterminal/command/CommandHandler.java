package com.aedan.jterminal.command;

import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.input.parser.TokenList;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.executors.commands.ExecuteJTermFile;

import java.io.File;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The CommandHandler for the JTerminal.
 */

public class CommandHandler {

    /**
     * The Environment containing the CommandHandler.
     */
    private Environment environment;

    /**
     * The CommandInput for the CommandHandler.
     */
    private CommandInput input;

    /**
     * The CommandOutput for the CommandHandler.
     */
    private CommandOutput output;

    /**
     * The Parser for the CommandHandler.
     */
    private Parser parser = new Parser();

    /**
     * Default CommandHandler constructor.
     *
     * @param environment The Environment containing the CommandHandler.
     * @param input       The CommandInput for the CommandHandler.
     * @param output      The CommandOutput for the CommandHandler.
     */
    public CommandHandler(Environment environment, CommandInput input, CommandOutput output) {
        this.environment = environment;
        this.input = input;
        this.output = output;
    }

    /**
     * Handles a line of input.
     *
     * @param s The String to handle.
     */
    public void handleInput(String s) {
        try {
            this.handleInput(input, output, parser.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            output.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a line of input with a custom CommandInput.
     *
     * @param s            The String to handle.
     * @param commandInput The CommandInput to read the input from.
     */
    public void handleInput(CommandInput commandInput, String s) {
        try {
            this.handleInput(commandInput, output, parser.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            output.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a line of input with a custom CommandOutput.
     *
     * @param s             The String to handle.
     * @param commandOutput The CommandOutput to write the output to.
     */
    public void handleInput(CommandOutput commandOutput, String s) {
        try {
            this.handleInput(input, commandOutput, parser.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a line of input with a custom CommandInput and CommandOutput
     *
     * @param s             The String to handle.
     * @param commandInput  The CommandInput to read the input from.
     * @param commandOutput The CommandOutput to write the output to.
     */
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, String s) {
        try {
            this.handleInput(commandInput, commandOutput, parser.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a pre-parsed line of input.
     *
     * @param tokens The list of parsed Tokens.
     */
    public void handleInput(TokenList tokens) {
        this.handleInput(input, output, tokens);
    }

    /**
     * Handles a pre-parsed line of input.
     *
     * @param commandInput  The CommandInput to read the input from.
     * @param commandOutput The CommandOutput to write the output to.
     * @param tokens        The list of parsed Tokens.
     */
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, TokenList tokens) {
        try {
            if (tokens == null || commandInput == null || commandOutput == null) {
                throw new IllegalArgumentException("Input is null");
            }
            if (tokens.isEmpty()) {
                return;
            }

            for (Command command : environment.getCommands()) {
                if (Objects.equals(command.getIdentifier(), tokens.get(0))) {
                    command.parse(new CommandArgumentList(tokens), commandInput, commandOutput, environment);
                    return;
                }
            }

            File file = environment.getPath().get(tokens.get(0) + ".jterm");
            if (file != null) {
                ExecuteJTermFile.execute(tokens, commandInput, commandOutput, environment);
                return;
            }

            if (tokens.size() == 1) {
                commandOutput.println(tokens.get(0));
                return;
            }

            throw new CommandHandlerException("Unrecognized Command \"" + tokens.get(0) + "\"", this);
        } catch (CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public CommandInput getInput() {
        return input;
    }

    public void setInput(CommandInput input) {
        this.input = input;
    }

    public CommandOutput getOutput() {
        return output;
    }

    public void setOutput(CommandOutput output) {
        this.output = output;
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
        public CommandHandlerException(String message, Object source) {
            super(message + " (" + source.getClass().getSimpleName() + ")");
        }
    }
}
