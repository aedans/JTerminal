package com.aedan.jterminal.output;

import java.io.PrintStream;

/**
 * Created by Aedan Smith.
 */

public class PrintStreamOutput implements CommandOutput {

    private PrintStream printStream;

    public PrintStreamOutput(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void print(String s) {
        printStream.print(s);
    }

    @Override
    public void close() {
        printStream.close();
    }
}
