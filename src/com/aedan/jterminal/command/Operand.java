package com.aedan.jterminal.command;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.List;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for creating Operands.
 */

public interface Operand {

    /**
     * Handles tokens that matches the Operand format.
     *
     * @param environment The Environment of this Operand.
     * @param input       The input from the JTerminal.
     * @param output      The output of the JTerminal.
     * @param tokens      The tokens that match the Command format.
     * @throws CommandHandler.CommandHandlerException if there is an error handling the tokens.
     */
    boolean handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException;
}
