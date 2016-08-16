package com.aedan.jterminal;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Aedan Smith on 8/14/2016.
 *
 * The Output controller for the JTerminal.
 */

public class Output {

    /**
     * The List of PrintStreams to print to;
     */
    private ArrayList<PrintStream> outputs = new ArrayList<>();

    /**
     * The default Output constructor.
     */
    public Output(){
        this(System.out);
    }

    /**
     * Output constructor for adding custom outputs.
     *
     * @param outputs: The list of PrintStreams to print to.
     */
    public Output(PrintStream... outputs){
        Collections.addAll(this.outputs, outputs);
    }

    /**
     * Output constructor for cloning.
     *
     * @param outputs: The list of PrintStreams to print to.
     */
    private Output(ArrayList<PrintStream> outputs){
        this.outputs = outputs;
    }

    /**
     * Prints an Object to all PrintStreams.
     *
     * @param o: The Object to print.
     */
    public void print(Object o){
        print(o.toString());
    }

    /**
     * Prints an Object to all PrintStreams, followed by a newline.
     *
     * @param o: The Object to print.
     */
    public void println(Object o){
        println(o.toString());
    }

    /**
     * Prints an Object to all PrintStreams, followed by a newline, with a max line length.
     *
     * @param o: The Object to print.
     * @param maxLineLength: The maximum line length.
     * @param indentationAmount: The amount to indent following Strings.
     */
    public void println(Object o, int maxLineLength, int indentationAmount){
        println(o.toString(), maxLineLength, indentationAmount);
    }

    /**
     * Prints a String to all PrintStreams.
     *
     * @param s: The String to print.
     */
    public void print(String s){
        for (PrintStream ps : outputs){
            ps.print(s);
        }
    }

    /**
     * Prints a String to all PrintStreams, followed by a newline.
     *
     * @param s: The Object to print.
     */
    public void println(String s){
        for (PrintStream ps : outputs){
            ps.println(s);
        }
    }

    /**
     * Prints a String to all PrintStreams, followed by a newline, with a max line length.
     *
     * @param s: The String to print.
     * @param maxLineLength: The maximum line length.
     * @param indentationAmount: The amount to indent following Strings.
     */
    public void println(String s, int maxLineLength, int indentationAmount){
        String[] lines = s.split("\\n");
        System.out.println(lines.length);
        for (String ss : lines){
            ss = ss.trim();
            if (ss.length() > maxLineLength){
                println(ss.substring(0, maxLineLength));
                int i;
                for (i = maxLineLength; i < ss.length()-maxLineLength; i+=maxLineLength-indentationAmount) {
                    println(getSpaces(indentationAmount) + ss.substring(i,i + maxLineLength-indentationAmount));
                }
                println(getSpaces(indentationAmount) + ss.substring(i));
            } else {
                println(ss);
            }
        }
    }

    /**
     * Prints an Array of ArrayLists of Objects in a grid format:
     * grid[0].get(0); grid[1].get(0);
     * grid[0].get(1); grid[1].get(1);
     *
     * @param space: The number of spaces to put between each column.
     * @param grid: The List of ArrayLists to print.
     */
    public void printObjGrid(int space, ArrayList<?>... grid){
        ArrayList<String>[] sGrid = new ArrayList[grid.length];
        for (int i = 0; i < grid.length; i++) {
            sGrid[i] = new ArrayList<>();
            for (Object o : grid[i]){
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
     * @param space: The number of spaces to put between each column.
     * @param grid: The List of ArrayLists to print.
     */
    @SafeVarargs
    public final void printGrid(int space, ArrayList<String>... grid){
        int size = grid[0].size();
        for (ArrayList<String> ss : grid){
            if (ss.size() != size) {
                println("Internal Error: could not print grid (Uneven sizes)");
                return;
            }
        }

        int[] longestLines = new int[grid.length];
        for (int i = 0; i < grid.length; i++) {
            int longestLine = 0;
            for (String s : grid[i]) {
                if (s.length() > longestLine)
                    longestLine = s.length();
            }
            longestLines[i] = longestLine;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < grid.length; j++) {
                print(grid[j].get(i) + getSpaces(longestLines[j] - grid[j].get(i).length() + space));
            }
            println("");
        }
    }

    private String getSpaces(int num){
        String s = "";
        for (int i = 0; i < num; i++) {
            s += " ";
        }
        return s;
    }

    /**
     * Adds a PrintStream to the List of PrintStreams.
     *
     * @param output: The PrintStream to add.
     */
    public void addOutput(PrintStream output){
        this.outputs.add(output);
    }

    /**
     * Sets the Output to a given PrintStream.
     *
     * @param output: The PrintStream to set the Output to.
     */
    public void setOutput(PrintStream output){
        this.outputs = new ArrayList<>();
        this.outputs.add(output);
    }

    /**
     * Sets the Outputs to a List of PrintStreams.
     *
     * @param outputs: The PrintStreams to set the Output to.
     */
    public void setOutputs(ArrayList<PrintStream> outputs){
        this.outputs = outputs;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Output clone(){
        return new Output(outputs);
    }

}
