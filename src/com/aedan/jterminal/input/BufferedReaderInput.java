package com.aedan.jterminal.input;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The default CommandInput for the JTerminal.
 */

public class BufferedReaderInput implements CommandInput {
    private BufferedReader bufferedReader;

    /**
     * Creates a BufferedReaderInput from stdin.
     */
    public BufferedReaderInput() {
        this(new BufferedReader(new InputStreamReader(System.in)));
    }

    /**
     * Creates a BufferedReaderInput from a custom scanner.
     *
     * @param bufferedReader The BufferedReader to create the BufferedReaderInput from.
     */
    public BufferedReaderInput(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    /**
     * @return The next line of the System input.
     */
    @Override
    public String nextLine() {
        try {
            return bufferedReader.readLine();
        } catch (Exception e) {
            System.err.println("No input found.");
            try {
                Thread.sleep(250);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return "";
        }
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    @Override
    public String toString() {
        return "BufferedReaderInput:" + JSON.toJSONString(this, true);
    }
}
