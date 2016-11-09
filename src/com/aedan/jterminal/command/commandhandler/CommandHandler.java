package com.aedan.jterminal.command.commandhandler;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.Operand;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.tokenizer.Tokenizer;
import com.aedan.jterminal.output.CommandOutput;

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
     * The Tokenizer for the CommandHandler.
     */
    private Tokenizer tokenizer = new Tokenizer();

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

    /**
     * Handles a pre-parsed line of input.
     *
     * @param tokens        The list of parsed Tokens.
     * @throws CommandHandlerException If there was an error handling the input.
     */
    public void handleInput(List<String> tokens) throws CommandHandlerException {
        this.handleInput(commandInput, commandOutput, tokens);
    }

    /**
     * Handles a pre-parsed line of input.
     *
     * @param commandInput  The CommandInput to read the input from.
     * @param commandOutput The CommandOutput to write the output to.
     * @param tokens        The list of parsed Tokens.
     * @throws CommandHandlerException If there was an error handling the input.
     */
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, List<String> tokens)
            throws CommandHandlerException {
        if (tokens == null || commandInput == null || commandOutput == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (tokens.isEmpty()) {
            return;
        }

        for (Operand operand : environment.getOperands()){
            if (operand.handleInput(environment, commandInput, commandOutput, tokens))
                return;
        }

        for (Command command : environment.getCommands()){
            if (Objects.equals(command.getIdentifier(), tokens.get(0))){
                command.parse(commandInput, new CommandArgumentList(tokens), environment, commandOutput);
                return;
            }
        }

        if (tokens.size() == 1){
            commandOutput.println(tokens.get(0));
            return;
        }

        throw new CommandHandlerException("Unrecognized Command \"" + tokens.get(0) + "\"", this);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(Tokenizer tokenizer) {
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
