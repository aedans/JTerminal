package com.aedan.jterminal.commands;

import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.List;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for creating Command Formats.
 */

public interface CommandFormat {

    /**
     * Returns if the tokens match the Command format.
     *
     * @param tokens The tokens to test.
     * @return True if the tokens matche the Command format.
     * @throws CommandHandler.CommandHandlerException if there is an error matching the tokens.
     */
    boolean matches(List<String> tokens) throws CommandHandler.CommandHandlerException;

    /**
     * Handles tokens that matches the Command format.
     *
     * @param environment The Environment of this CommandFormat.
     * @param input       The input from the JTerminal.
     * @param output      The output of the JTerminal.
     * @param tokens      The tokens that match the Command format.
     * @throws CommandHandler.CommandHandlerException if there is an error handling the tokens.
     */
    void handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException;

}
