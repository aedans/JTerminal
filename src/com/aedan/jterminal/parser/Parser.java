package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating CommandParser rules.
 */

// TODO: Error handling
public abstract class Parser {
    /**
     * Parses a string.
     *
     * @param environment The Environment containing the Parser.
     * @param s           The string to parse.
     * @return The List of Arguments.
     */
    public ArgumentList parse(Environment environment, String s) throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        StringIterator in = new StringIterator(s);
        onBeginParse(environment, argumentList, in);
        while (in.hasNext()) {
            this.parse(environment, this, argumentList, in);
        }
        onEndParse(environment, argumentList, in);
        return argumentList;
    }

    /**
     * Parses a string until a character.
     *
     * @param environment The Environment containing the Parser.
     * @param in          The string input to parse.
     * @param end         The character that begins a scope.
     * @return The List of Arguments
     * @throws JTerminalException If there was an error parsing the string.
     */
    public ArgumentList parseUntil(Environment environment, StringIterator in, char end) throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        onBeginParse(environment, argumentList, in);
        while (in.hasNext()) {
            if (in.peek() == end) {
                in.next();
                break;
            }
            this.parse(environment, this, argumentList, in);
        }
        onEndParse(environment, argumentList, in);
        return argumentList;
    }

    /**
     * Parses a string until a nested character.
     *
     * @param environment The Environment containing the Parser.
     * @param in          The string input to parse.
     * @param beginNest   The character that begins a scope.
     * @param endNest     The character that ends a scope.
     * @return The List of Arguments.
     * @throws JTerminalException If there was an error parsing the string.
     */
    public ArgumentList nestedParse(Environment environment, StringIterator in, char beginNest, char endNest)
            throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        onBeginParse(environment, argumentList, in);
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
                this.parse(environment, this, argumentList, in);
            }
        }
        onEndParse(environment, argumentList, in);
        return argumentList;
    }

    protected void onBeginParse(Environment environment, ArgumentList arguments, StringIterator in) {
    }

    protected void onEndParse(Environment environment, ArgumentList arguments, StringIterator in) {
    }

    /**
     * Processes a string.
     *
     * @param environment    The Environment for the Parser.
     * @param parser         The CommandParser.
     * @param argumentList   The TokenList to parse.
     * @param in             The original String.
     * @return If the parser applied successfully.
     * @throws JTerminalException If there is an error parsing the string.
     */
    protected abstract boolean parse(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException;

    public String getId() {
        return this.getClass().getSimpleName();
    }
}
