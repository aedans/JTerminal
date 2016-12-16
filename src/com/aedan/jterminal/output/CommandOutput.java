package com.aedan.jterminal.output;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * The CommandOutput controller for the JTerminal.
 */

@SuppressWarnings("unchecked")
public interface CommandOutput {
    /**
     * Prints a String to the CommandOutput's output.
     *
     * @param s The String to print.
     */
    void print(String s);

    default void print(Object o) {
        print(o.toString());
    }

    default void println() {
        print("\n");
    }

    default void println(String s) {
        print(s);
        println();
    }

    default void println(Object o) {
        print(o == null ? "null" : o.toString());
        println();
    }

    default void printf(String s, Object... o) {
        print(String.format(s, o));
    }

    /**
     * Prints an Array of Collections of Objects in a grid format:
     * grid[0].get(0); grid[1].get(0);
     * grid[0].get(1); grid[1].get(1);
     *
     * @param space The number of spaces to put between each column.
     * @param grid  The array of Collections to print.
     */
    default void printObjGrid(int space, Collection<Object>... grid) {
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
    default void printGrid(int space, ArrayList<String>... grid) {
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
    default String getSpaces(int num) {
        String s = "";
        for (int i = 0; i < num; i++) {
            s += " ";
        }
        return s;
    }

    void close();

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
