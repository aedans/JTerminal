package com.aedan.jterminal.command.commandhandler;

import com.aedan.jterminal.command.Command;
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
    private CommandInput commandInput;

    /**
     * The CommandOutput for the CommandHandler.
     */
    private CommandOutput commandOutput;

    /**
     * The Parser for the CommandHandler.
     */
    private Parser tokenizer = new Parser();

    /**
     * Default CommandHandler constructor.
     *
     * @param environment   The Environment containing the CommandHandler.
     * @param commandInput  The CommandInput for the CommandHandler.
     * @param commandOutput The CommandOutput for the CommandHandler.
     */
    public CommandHandler(Environment environment, CommandInput commandInput, CommandOutput commandOutput) {
        this.environment = environment;
        this.commandInput = commandInput;
        this.commandOutput = commandOutput;
    }

    /**
     * Handles a line of input.
     *
     * @param s The String to handle.
     */
    public void handleInput(String s) {
        try {
            this.handleInput(commandInput, commandOutput, tokenizer.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a line of input with a custom CommandInput.
     *
     * @param s The String to handle.
     * @param commandInput The CommandInput to read the input from.
     */
    public void handleInput(CommandInput commandInput, String s) {
        try {
            this.handleInput(commandInput, commandOutput, tokenizer.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a line of input with a custom CommandOutput.
     *
     * @param s The String to handle.
     * @param commandOutput The CommandOutput to write the output to.
     */
    public void handleInput(CommandOutput commandOutput, String s) {
        try {
            this.handleInput(commandInput, commandOutput, tokenizer.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a line of input with a custom CommandInput and CommandOutput
     *
     * @param s The String to handle.
     * @param commandInput The CommandInput to read the input from.
     * @param commandOutput The CommandOutput to write the output to.
     */
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, String s){
        try {
            this.handleInput(commandInput, commandOutput, tokenizer.parse(environment, s));
        } catch (CommandHandler.CommandHandlerException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a pre-parsed line of input.
     *
     * @param tokens        The list of parsed Tokens.
     */
    public void handleInput(TokenList tokens) {
        this.handleInput(commandInput, commandOutput, tokens);
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
        } catch (CommandHandlerException e){
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Parser getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(Parser tokenizer) {
        this.tokenizer = tokenizer;
    }

    public CommandInput getCommandInput() {
        return commandInput;
    }

    public void setCommandInput(CommandInput commandInput) {
        this.commandInput = commandInput;
    }

    public CommandOutput getCommandOutput() {
        return commandOutput;
    }

    public void setCommandOutput(CommandOutput commandOutput) {
        this.commandOutput = commandOutput;
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
