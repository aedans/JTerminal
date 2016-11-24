package com.aedan.jterminal.command;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.parser.CommandParser;
import com.alibaba.fastjson.JSON;

import java.util.Objects;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The CommandHandler for the JTerminal.
 */

@SuppressWarnings("WeakerAccess")
public class CommandHandler {
    /**
     * The CommandParser for the CommandHandler.
     */
    private CommandParser parser = new CommandParser();
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
     * @param s      The String to execute.
     * @param input  The CommandInput to read the input from.
     * @param output The CommandOutput to write the output to.
     */
    public Object handleInput(String s, CommandInput input, CommandOutput output)
            throws JTerminalException {
        try {
            return handleInput(parser.parse(environment, s), input, output);
        } catch (JTerminalException e) {
            return onFatalExecution(input, output, e);
        }
    }

    /**
     * Handles a pre-parsed line of input.
     * @param arguments The list of parsed Tokens.
     * @param input  The CommandInput to read the input from.
     * @param output The CommandOutput to write the output to.
     */
    public Object handleInput(ArgumentList arguments, CommandInput input, CommandOutput output)
            throws JTerminalException {
        if (!verify(arguments, input, output))
            return null;

        if (arguments.isEmpty())
            return ifEmpty(input, output);

        Object o = execute(arguments, input, output);
        if (o != JTerminalException.class) {
            return onSuccessfulExecution(o, arguments, input, output);
        } else {
            return onFailedExecution(arguments, input, output);
        }
    }

    /**
     * Verifies input.
     *
     * @param arguments The TokenList input.
     * @param input     The CommandInput.
     * @param output    The CommandOutput.
     * @return If the input should be ignored.
     * @throws JTerminalException If there is an error with the input.
     */
    protected boolean verify(ArgumentList arguments, CommandInput input, CommandOutput output) throws JTerminalException {
        if (arguments == null || input == null || output == null)
            throw new IllegalArgumentException("Input is null");
        else
            return true;
    }

    /**
     * Executes a TokenList.
     *
     * @param arguments The TokenList to execute.
     * @param input     The CommandInput.
     * @param output    The CommandOutput.
     * @return If the execution was successful.
     * @throws JTerminalException If there was an error during execution.
     */
    protected Object execute(ArgumentList arguments, CommandInput input, CommandOutput output) throws JTerminalException {
        for (Command command : environment.getCommands()) {
            if (Objects.equals(command.getIdentifier(), arguments.get(0).value)) {
                return command.parse(arguments, input, output, environment);
            }
        }
        return JTerminalException.class;
    }

    /**
     * Hook called if the ArgumentList is empty.
     */
    private Object ifEmpty(CommandInput input, CommandOutput output) throws JTerminalException {
        return null;
    }

    /**
     * Hook called upon successful executions.
     */
    protected Object onSuccessfulExecution(Object o, ArgumentList arguments, CommandInput input, CommandOutput output)
            throws JTerminalException {
        return o;
    }

    /**
     * Hook called upon failed executions.
     */
    protected Object onFailedExecution(ArgumentList arguments, CommandInput input, CommandOutput output)
            throws JTerminalException {
        // TODO: Re-implement
//        File file = environment.getPath().get(arguments.get(0).value + ".jterminal");
//        if (file != null && file.exists()) {
//            arguments.add(0, new Argument("exec"));
//            return ExecuteJTermFile.execute(arguments, input, output, environment);
//        }

        if (arguments.size() == 1) {
            return arguments.get(0).value;
        }

        throw new JTerminalException("Unrecognized Command \"" + arguments.get(0) + "\"", this);
    }

    /**
     * Hook called upon fatal executions.
     */
    protected Object onFatalExecution(CommandInput input, CommandOutput output, JTerminalException e)
            throws JTerminalException {
        return String.format("Could not execute command (%s)", e.getMessage());
    }

    public CommandParser getParser() {
        return parser;
    }

    public void setParser(CommandParser commandParser) {
        this.parser = commandParser;
    }

    @Override
    public String toString() {
        return "\"commandHandler\":" + JSON.toJSONString(this, true);
    }
}
