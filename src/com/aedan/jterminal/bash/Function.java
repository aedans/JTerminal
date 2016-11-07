package com.aedan.jterminal.bash;

import com.aedan.jterminal.command.commandhandler.CommandHandler;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for creating functions to be used in the BashRuntime.
 */

public interface Function {

    /**
     * @return The name of the function.
     */
    String getIdentifier();

    /**
     * Applies a function.
     *
     * @param args The arguments of the function.
     * @return The return value of the Function.
     * @throws CommandHandler.CommandHandlerException If there was an error applying the function.
     */
    Object apply(Object[] args) throws CommandHandler.CommandHandlerException;
}
