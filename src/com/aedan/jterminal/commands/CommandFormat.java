package com.aedan.jterminal.commands;

import com.aedan.jterminal.Output;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Interface for creating custom Command formats.
 */

public interface CommandFormat {

    /**
     * Returns if the String matches the Command format.
     *
     * @param in: The String to test.
     * @return boolean: True if the String matches the Command format.
     * @throws CommandHandler.CommandHandlerException if there is an error matching the String.
     */
    boolean matches(String in) throws CommandHandler.CommandHandlerException;

    /**
     * Handles a String that matches the Command format.
     *
     * @param commandHandler: The CommandHandler that contains this CommandFormat.
     * @param in: The String that matches the Command format.
     * @param output: The Output of the CommandHandler.
     * @throws CommandHandler.CommandHandlerException if there is an error handling the String.
     */
    void handleString(CommandHandler commandHandler, String in, Output output) throws CommandHandler.CommandHandlerException;

}
