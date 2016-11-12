package com.aedan.jterminal.input;

import java.util.Scanner;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The default CommandInput for the JTerminal.
 */

public class ScannerInput implements CommandInput {
    /**
     * The Scanner for the System Input.
     */
    private Scanner scanner;

    /**
     * Creates a ScannerInput from stdin.
     */
    public ScannerInput() {
        this(new Scanner(System.in));
    }

    /**
     * Creates a ScannerInput from a custom scanner.
     *
     * @param scanner The Scanner to create the ScannerInput from.
     */
    public ScannerInput(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * @return The next line of the System input.
     */
    @Override
    public String nextLine() {
        try {
            return scanner.nextLine();
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

    @Override
    public long nextLong() {
        return scanner.nextLong();
    }

    @Override
    public double nextDouble() {
        return scanner.nextDouble();
    }

}
