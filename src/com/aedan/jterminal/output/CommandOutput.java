package com.aedan.jterminal.output;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The CommandOutput controller for the JTerminal.
 */

public class CommandOutput {

    /**
     * The List of PrintStreams to print to.
     */
    private ArrayList<PrintStream> printStreams = new ArrayList<>();

    /**
     * Creates a CommandOutput to the System output.
     */
    public CommandOutput() {
        this(System.out);
    }

    /**
     * CommandOutput constructor for adding custom printStreams.
     *
     * @param printStreams The list of PrintStreams to print to.
     */
    public CommandOutput(PrintStream... printStreams) {
        Collections.addAll(this.printStreams, printStreams);
    }

    /**
     * CommandOutput constructor for cloning.
     *
     * @param printStreams The list of PrintStreams to print to.
     */
    private CommandOutput(ArrayList<PrintStream> printStreams) {
        this.printStreams = printStreams;
    }

    /**
     * Prints an Object to all PrintStreams.
     *
     * @param o The Object to print.
     */
    public void print(Object o) {
        print(String.valueOf(o));
    }

    /**
     * Prints an Object to all PrintStreams, followed by a newline.
     *
     * @param o The Object to print.
     */
    public void println(Object o) {
        println(String.valueOf(o));
    }

    /**
     * Prints a String to all PrintStreams.
     *
     * @param s The String to print.
     */
    public void print(String s) {
        for (PrintStream ps : printStreams) {
            ps.print(s);
        }
    }

    /**
     * Prints a String to all PrintStreams, followed by a newline.
     *
     * @param s The String to print.
     */
    public void println(String s) {
        for (PrintStream ps : printStreams) {
            ps.println(s);
        }
    }

    /**
     * Prints a formatted String to all PrintStreams.
     *
     * @param s       The String to print.
     * @param objects The objects to format.
     */
    public void printf(String s, Object... objects) {
        for (PrintStream ps : printStreams) {
            ps.printf(s, objects);
        }
    }

    /**
     * Prints an Array of Collections of Objects in a grid format:
     * grid[0].get(0); grid[1].get(0);
     * grid[0].get(1); grid[1].get(1);
     *
     * @param space The number of spaces to put between each column.
     * @param grid  The array of Collections to print.
     */
    @SafeVarargs
    public final void printObjGrid(int space, Collection<Object>... grid) {
        //noinspection unchecked
        ArrayList<String>[] sGrid = new ArrayList[grid.length];
        for (int i = 0; i < grid.length; i++) {
            sGrid[i] = new ArrayList<>();
            for (Object o : grid[i]) {
                sGrid[i].add(o.toString());
            }
        }
        printGrid(space, sGrid);
    }

    /**
     * Prints an Array of ArrayLists of Strings in a grid format:
     * grid[0].get(0); grid[1].get(0);
     * grid[0].get(1); grid[1].get(1);
     *
     * @param space The number of spaces to put between each column.
     * @param grid  The List of ArrayLists to print.
     */
    @SafeVarargs
    public final void printGrid(int space, ArrayList<String>... grid) {
        // Validates grid
        int size = grid[0].size();
        for (Collection<String> ss : grid) {
            if (ss.size() != size) {
                println("Could not print grid (Uneven sizes)");
                return;
            }
        }

        // Gets the length of the longest lines
        int[] longestLines = new int[grid.length];
        for (int i = 0; i < grid.length; i++) {
            int longestLine = 0;
            for (String s : grid[i]) {
                if (s.length() > longestLine)
                    longestLine = s.length();
            }
            longestLines[i] = longestLine;
        }

        // Prints the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < grid.length; j++) {
                print(grid[j].get(i) + getSpaces(longestLines[j] - grid[j].get(i).length() + space));
            }
            println("");
        }
    }

    /**
     * Gets a String with the number of spaces.
     *
     * @param num The number of spaces to get.
     * @return The String with the number of spaces.
     */
    private String getSpaces(int num) {
        String s = "";
        for (int i = 0; i < num; i++) {
            s += " ";
        }
        return s;
    }

    public void addPrintStream(PrintStream printStreams) {
        this.printStreams.add(printStreams);
    }

    public void removePrintStream(PrintStream printStreams) {
        this.printStreams.remove(printStreams);
    }

    /**
     * @return Returns a deep clone of the CommandOutput.
     */
    public CommandOutput clone() {
        ArrayList<PrintStream> printStreams = this.printStreams.stream().map(PrintStream::new)
                .collect(Collectors.toCollection(ArrayList::new));
        return new CommandOutput(printStreams);
    }

    public ArrayList<PrintStream> getPrintStreams() {
        return printStreams;
    }

    public void setPrintStreams(PrintStream... printStreams) {
        this.printStreams = new ArrayList<>();
        Collections.addAll(this.printStreams, printStreams);
    }

}
