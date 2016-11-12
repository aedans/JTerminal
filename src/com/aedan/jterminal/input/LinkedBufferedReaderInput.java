package com.aedan.jterminal.input;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for linking multiple inputs.
 */

public class LinkedBufferedReaderInput implements CommandInput {
    /**
     * The current stack of input.
     */
    private final Stack<String> input = new Stack<>();

    /**
     * The List of BufferedReaders to read from.
     */
    private LinkedList<BufferedReader> bufferedReaders = new LinkedList<>();

    @Override
    public String nextLine() {
        if (input.size() != 0)
            return input.pop();
        else {
            while (input.size() == 0) ;
            return nextLine();
        }
    }

    public void add(BufferedReader bufferedReader) {
        this.bufferedReaders.add(bufferedReader);
        new Thread(() -> {
            while (true) try {
                input.add(bufferedReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }).start();
    }

    public LinkedList<BufferedReader> getBufferedReaders() {
        return bufferedReaders;
    }

    @Override
    public String toString() {
        return "\"linkedBufferedReaderInput\":" + JSON.toJSONString(this, true);
    }
}
