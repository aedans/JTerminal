package com.aedan.jterminal.commands;

import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for creating custom Command formats.
 */

public interface CommandFormat {

    /**
     * Returns if the String checkMatches the Command format.
     *
     * @param in The String to test.
     * @return True if the String checkMatches the Command format.
     * @throws CommandHandler.CommandHandlerException if there is an error matching the String.
     */
    boolean matches(String in) throws CommandHandler.CommandHandlerException;

    /**
     * Handles a String that checkMatches the Command format.
     *
     * @param commandHandler The CommandHandler that contains this CommandFormat.
     * @param input          The input from the CommandHandler.
     * @param in             The String that checkMatches the Command format.
     * @param output         The CommandOutput of the CommandHandler.
     * @throws CommandHandler.CommandHandlerException if there is an error handling the String.
     */
    void handleInput(CommandHandler commandHandler, CommandInput input, String in, CommandOutput output) throws CommandHandler.CommandHandlerException;

}
