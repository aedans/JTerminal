package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating CommandParser rules.
 */

// TODO: Error handling
public abstract class Parser<T> {
    /**
     * Parses a string.
     *
     * @param environment The Environment containing the Parser.
     * @param s           The string to parse.
     * @param t           The object to parse to.
     */
    public void parse(Environment environment, String s, T t) throws JTerminalException {
        StringIterator in = new StringIterator(s);
        onBeginParse(environment, t, in);
        while (in.hasNext()) {
            this.parse(environment, t, in);
        }
        onEndParse(environment, t, in);
    }

    /**
     * Parses a string until a character.
     *
     * @param environment The Environment containing the Parser.
     * @param in          The string input to parse.
     * @param t           The object to parse to.
     * @param end         The character that begins a scope.
     * @throws JTerminalException If there was an error parsing the string.
     */
    public void parseUntil(Environment environment, StringIterator in, T t, char end)
            throws JTerminalException {
        onBeginParse(environment, t, in);
        while (in.hasNext()) {
            if (in.peek() == end) {
                in.next();
                break;
            }
            this.parse(environment, t, in);
        }
        onEndParse(environment, t, in);
    }

    /**
     * Parses a string until a nested character.
     *
     * @param environment The Environment containing the Parser.
     * @param in          The string input to parse.
     * @param t           The object to parse to.
     * @param beginNest   The character that begins a scope.
     * @param endNest     The character that ends a scope.
     * @throws JTerminalException If there was an error parsing the string.
     */
    public void nestedParse(Environment environment, StringIterator in, T t, char beginNest, char endNest)
            throws JTerminalException {
        onBeginParse(environment, t, in);
        int depth = 1;
        while (in.hasNext()) {
            if (in.peek() == beginNest) {
                depth++;
                in.next();
            } else if (in.peek() == endNest) {
                depth--;
                in.next();
                if (depth == 0)
                    break;
            } else {
                this.parse(environment, t, in);
            }
        }
        onEndParse(environment, t, in);
    }

    protected void onBeginParse(Environment environment, T t, StringIterator in) {
    }

    protected void onEndParse(Environment environment, T t, StringIterator in) {
    }

    /**
     * Processes a string.
     *
     * @param environment    The Environment for the Parser.
     * @param t              The object to parse to.
     * @param in             The original String.
     * @throws JTerminalException If there is an error parsing the string.
     */
    protected abstract boolean parse(Environment environment, T t, StringIterator in)
            throws JTerminalException;

    public String getId() {
        return this.getClass().getSimpleName();
    }
}
