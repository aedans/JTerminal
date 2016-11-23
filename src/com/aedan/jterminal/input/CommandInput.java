package com.aedan.jterminal.input;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The Input interface for the JTerminal.
 */

public interface CommandInput {
    /**
     * @return The skip line for the JTerminal to execute.
     */
    String nextLine();

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
