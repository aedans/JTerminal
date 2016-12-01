package com.aedan.jterminal.output;

/**
 * Created by Aedan Smith.
 */

public abstract class PrintWrapper {
    public abstract void print(CommandOutput output);

    @Override
    public String toString() {
        StringOutput output = new StringOutput();
        output.print(this);
        return output.getString();
    }
}
