package com.aedan.jcmd.input;

/**
 * Created by Aedan Smith on 8/14/2016.
 *
 * The Input interface for the JTerminal.
 */

public interface CommandInput {

    /**
     * @return The next line for the JTerminal to handle.
     */
    String nextLine();

}
