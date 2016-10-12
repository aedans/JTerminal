package com.aedan.jterminal.commands.commandhandler;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.commands.commandhandler.tokenizerrules.EnvironmentVariableRule;
import com.aedan.jterminal.commands.commandhandler.tokenizerrules.GlobalVariableRule;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.tokenizer.Tokenizer;
import com.aedan.jterminal.commands.commandhandler.tokenizerrules.EmbeddedCommandsRule;
import com.aedan.jterminal.commands.commandhandler.tokenizerrules.StringLiteralRule;
import com.aedan.jterminal.output.CommandOutput;
import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;
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
     * The tokenizer for the CommandHandler.
     */
    private Tokenizer tokenizer = new Tokenizer();

    /**
     * Default CommandHandler constructor.
     *
     * @param environment The Environment containing the CommandHandler.
     */
    public CommandHandler(Environment environment, CommandInput commandInput, CommandOutput commandOutput) {
        this.environment = environment;
        this.commandInput = commandInput;
        this.commandOutput = commandOutput;

        this.tokenizer.addTokenizerRule(new StringLiteralRule());
        this.tokenizer.addTokenizerRule(new EmbeddedCommandsRule(this));
        this.tokenizer.addTokenizerRule(new GlobalVariableRule(environment.getGlobalVariables()));
        this.tokenizer.addTokenizerRule(new EnvironmentVariableRule(environment.getEnvironmentVariables()));
    }

    /**
     * Handles a line of input.
     *
     * @param s The String to handle.
     * @throws CommandHandlerException If there was an error handling the input.
     */
    public void handleInput(String s) throws CommandHandlerException {
        this.handleInput(commandInput, commandOutput, tokenizer.tokenize(s));
    }

    /**
     * Handles a line of input with a custom CommandInput.
     *
     * @param s The String to handle.
     * @param commandInput The CommandInput to read the input from.
     * @throws CommandHandlerException If there was an error handling the input.
     */
    public void handleInput(CommandInput commandInput, String s) throws CommandHandlerException {
        this.handleInput(commandInput, commandOutput, tokenizer.tokenize(s));
    }

    /**
     * Handles a line of input with a custom CommandOutput.
     *
     * @param s The String to handle.
     * @param commandOutput The CommandOutput to write the output to.
     * @throws CommandHandlerException If there was an error handling the input.
     */
    public void handleInput(CommandOutput commandOutput, String s) throws CommandHandlerException {
        this.handleInput(commandInput, commandOutput, tokenizer.tokenize(s));
    }

    /**
     * Handles a line of input with a custom CommandInput and CommandOutput
     *
     * @param s The String to handle.
     * @param commandInput The CommandInput to read the input from.
     * @param commandOutput The CommandOutput to write the output to.
     * @throws CommandHandlerException If there was an error handling the input.
     */
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, String s)
            throws CommandHandlerException {
        this.handleInput(commandInput, commandOutput, tokenizer.tokenize(s));
    }

    @NotNull
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, List<String> tokens)
            throws CommandHandlerException {
        if (tokens == null || commandInput == null || commandOutput == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (tokens.size() == 0){
            return;
        }

        for (CommandFormat commandFormat : environment.getCommandFormats()){
            if (commandFormat.matches(tokens)){
                commandFormat.handleInput(environment, commandInput, commandOutput, tokens);
                return;
            }
        }

        for (Command command : environment.getCommands()){
            if (Objects.equals(command.getIdentifier(), tokens.get(0))){
                command.parse(commandInput, new CommandArgumentList(tokens), environment, commandOutput);
                return;
            }
        }

        throw new CommandHandlerException("Unrecognized Command \"" + tokens.get(0) + "\"");
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    public CommandInput getCommandInput() {
        return commandInput;
    }

    public CommandOutput getCommandOutput() {
        return commandOutput;
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
