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
     * Sets the Output to a given PrintStream.
     *
     * @param outputs: The PrintStream to set the Output to.
     */
    public void setOutput(PrintStream outputs){
        this.outputs = new ArrayList<>();
        this.outputs.add(outputs);
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
