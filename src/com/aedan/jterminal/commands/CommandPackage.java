package com.aedan.jterminal.commands;

import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for created packages of Commands for a CommandHandler.
 */

public interface CommandPackage {

    /**
     * Adds all Commands to an Environment.
     *
     * @param environment The Environment to add the Commands to.
     */
    void addCommands(Environment environment);

}
