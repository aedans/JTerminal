package com.aedan.jterminal.input;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The Input interface for the JTerminal.
 */

public interface CommandInput {

    /**
     * @return String: The next line for the JTerminal to handle.
     */
    String nextLine();

}
