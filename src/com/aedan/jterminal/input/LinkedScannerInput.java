package com.aedan.jterminal.input;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for linking multiple inputs.
 */

public class LinkedScannerInput implements CommandInput {

    /**
     * The current stack of input.
     */
    private final Stack<String> input = new Stack<>();
    /**
     * The List of scanners to read from.
     */
    private LinkedList<Scanner> scanners = new LinkedList<>();

    @Override
    public String nextLine() {
        if (input.size() != 0)
            return input.pop();
        else {
            while (input.size() == 0) ;
            return nextLine();
        }
    }

    @Override
    public long nextLong() {
        return 0;
    }

    @Override
    public double nextDouble() {
        return 0;
    }

    public void add(Scanner scanner) {
        this.scanners.add(scanner);
        new Thread(() -> {
            while (scanner.hasNext()) input.add(scanner.nextLine());
        }).start();
    }

    public LinkedList<Scanner> getScanners() {
        return scanners;
    }

    public Stack<String> getInput() {
        return input;
    }
}
