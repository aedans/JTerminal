package com.aedan.jterminal.input;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The Input interface for the JTerminal.
 */

public interface CommandInput {

    /**
     * @return The next line for the JTerminal to handle.
     */
    String nextLine();

    /**
     * @return The next long for the JTerminal to handle.
     */
    long nextLong();

    /**
     * @return The next double for the JTerminal to handle.
     */
    double nextDouble();
}
