package com.aedan.jterminal;

import com.aedan.jterminal.commands.CommandHandler;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for created packages of Commands for a CommandHandler.
 */

public interface CommandPackage {

    /**
     * Adds all Commands to a given CommandHandler
     *
     * @param destCommandHandler The CommandHandler to add the Commands to.
     */
    void addCommands(CommandHandler destCommandHandler);

}
