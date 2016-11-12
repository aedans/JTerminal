package com.aedan.jterminal.output;

import com.alibaba.fastjson.JSON;

import java.io.PrintStream;

/**
 * Created by Aedan Smith.
 */

public class PrintStreamOutput implements CommandOutput {
    private PrintStream printStream;

    public PrintStreamOutput() {
        this(System.out);
    }

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

    public PrintStream getPrintStream() {
        return printStream;
    }

    @Override
    public String toString() {
        return "PrintStreamOutput:" + JSON.toJSONString(this, true);
    }
}
