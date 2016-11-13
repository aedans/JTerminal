package com.aedan.jterminal.command;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.input.parser.TokenList;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.executors.commands.ExecuteJTermFile;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The CommandHandler for the JTerminal.
 */

@SuppressWarnings("WeakerAccess")
public class CommandHandler {
    /**
     * The Environment containing the CommandHandler.
     */
    private Environment environment;

    /**
     * The Parser for the CommandHandler.
     */
    private Parser parser = new Parser();

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
     * @param s             The String to execute.
     * @param input  The CommandInput to read the input from.
     * @param output The CommandOutput to write the output to.
     */
    public void handleInput(String s, CommandInput input, CommandOutput output)
            throws JTerminalException {
        try {
            for (TokenList tokenList : parser.parse(environment, s)) {
                this.handleInput(tokenList, input, output);
            }
        } catch (JTerminalException e) {
            onFatalExecution(input, output, e);
        }
    }

    /**
     * Handles a pre-parsed line of input.
     *
     * @param tokens The list of parsed Tokens.
     * @param input  The CommandInput to read the input from.
     * @param output The CommandOutput to write the output to.
     */
    public void handleInput(TokenList tokens, CommandInput input, CommandOutput output) throws JTerminalException {
        try {
            if (!verify(tokens, input, output))
                return;

            if (execute(tokens, input, output)) {
                onSuccessfulExecution(tokens, input, output);
            } else {
                onFailedExecution(tokens, input, output);
            }
        } catch (JTerminalException e) {
            onFatalExecution(input, output, e);
        }
    }

    /**
     * Verifies input.
     *
     * @param tokens The TokenList input.
     * @param input  The CommandInput.
     * @param output The CommandOutput.
     * @return If the input should be ignored.
     * @throws JTerminalException If there is an error with the input.
     */
    protected boolean verify(TokenList tokens, CommandInput input, CommandOutput output) throws JTerminalException {
        if (tokens == null || input == null || output == null)
            throw new IllegalArgumentException("Input is null");
        return !tokens.isEmpty();
    }

    /**
     * Executes a TokenList.
     *
     * @param tokens The TokenList to execute.
     * @param input  The CommandInput.
     * @param output The CommandOutput.
     * @return If the execution was successful.
     * @throws JTerminalException If there was an error during execution.
     */
    protected boolean execute(TokenList tokens, CommandInput input, CommandOutput output) throws JTerminalException {
        for (Command command : environment.getCommands()) {
            if (Objects.equals(command.getIdentifier(), tokens.get(0))) {
                command.parse(new CommandArgumentList(tokens), input, output, environment);
                return true;
            }
        }
        return false;
    }

    /**
     * Hook called upon successful executions.
     */
    protected void onSuccessfulExecution(TokenList tokens, CommandInput input, CommandOutput output)
            throws JTerminalException {
    }

    /**
     * Hook called upon failed executions.
     */
    protected void onFailedExecution(TokenList tokens, CommandInput input, CommandOutput output)
            throws JTerminalException {
        File file = environment.getPath().get(tokens.get(0) + ".jterm");
        if (file != null && file.exists()) {
            ExecuteJTermFile.execute(tokens, input, output, environment);
            return;
        }

        if (tokens.size() == 1) {
            output.println(tokens.get(0));
            return;
        }

        throw new JTerminalException("Unrecognized Command \"" + tokens.get(0) + "\"", this);
    }

    /**
     * Hook called upon fatal executions.
     */
    protected void onFatalExecution(CommandInput input, CommandOutput output, JTerminalException e)
            throws JTerminalException {
        output.printf("Could not execute command (%s)\n", e.getMessage());
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    public String toString() {
        return "\"commandHandler\":" + JSON.toJSONString(this, true);
    }
}
