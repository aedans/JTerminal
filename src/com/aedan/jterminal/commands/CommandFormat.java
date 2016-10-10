package com.aedan.jterminal.commands;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for creating Command Formats.
 */

public interface CommandFormat {

    /**
     * Returns if the String matches the Command format.
     *
     * @param in The String to test.
     * @return True if the String matches the Command format.
     * @throws CommandHandler.CommandHandlerException if there is an error matching the String.
     */
    boolean matches(String in) throws CommandHandler.CommandHandlerException;

    /**
     * Handles a String that checkMatches the Command format.
     *
     * @param environment The Environment that isSubset this CommandFormat.
     * @param input       The input from the JTerminal.
     * @param in          The String that matches the Command format.
     * @param output      The CommandOutput of the JTerminal.
     * @throws CommandHandler.CommandHandlerException if there is an error handling the String.
     */
    void handleInput(Environment environment, CommandInput input, String in, CommandOutput output)
            throws CommandHandler.CommandHandlerException;

}
