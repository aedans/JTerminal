package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating CommandParser rules.
 */

public interface Parser {
    /**
     * Parses a string.
     *
     * @param environment The Environment containing the Parser.
     * @param s           The string to parse.
     * @return The List of Arguments.
     */
    default ArgumentList parse(Environment environment, String s) throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        StringIterator in = new StringIterator(s);
        while (in.hasNext()) {
            this.apply(environment, this, argumentList, in);
        }
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
    default ArgumentList parseUntil(Environment environment, StringIterator in, char end) throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        while (in.hasNext()) {
            if (in.peek() == end)
                break;
            this.apply(environment, this, argumentList, in);
        }
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
    default ArgumentList nestedParse(Environment environment, StringIterator in, char beginNest, char endNest)
            throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
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
                this.apply(environment, this, argumentList, in);
            }
        }
        return argumentList;
    }

    /**
     * Processes a string.
     *
     * @param environment    The Environment for the Parser.
     * @param parser         The CommandParser.
     * @param argumentList   The TokenList to apply.
     * @param in             The original String.
     * @return If the parser applied successfully.
     * @throws JTerminalException If there is an error parsing the string.
     */
    boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
