package com.aedan.jterminal;

/**
 * Created by Aedan Smith.
 */

public class JTerminalException extends RuntimeException {
    /**
     * The default JTerminalException constructor.
     *
     * @param message The error message to display.
     */
    public JTerminalException(String message, Object source) {
        super(message + " (" + (source != null ? source.getClass().getSimpleName() : "null") + ")");
    }
}
