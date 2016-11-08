package com.aedan.jterminal.output;

/**
 * Created by Aedan Smith.
 */

public class StringOutput implements CommandOutput {
    private String string = "";
    private boolean closed = false;

    @Override
    public void print(String s) {
        if (!closed)
            this.string += s;
    }

    @Override
    public void close() {
        closed = true;
    }

    public String getString() {
        return string;
    }
}
