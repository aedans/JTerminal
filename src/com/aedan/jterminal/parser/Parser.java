package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import javafx.util.Pair;

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
        for (int i = 0; i < s.length(); i++) {
            i = this.process(environment, this, i, argumentList, s);
        }
        return argumentList;
    }

    /**
     * Parses a string until a character.
     *
     * @param environment The Environment containing the Parser.
     * @param s           The string to parse.
     * @param end         The character that begins a scope.
     * @return The List of Arguments
     * @throws JTerminalException If there was an error parsing the string.
     */
    default ArgumentList parseUntil(Environment environment, String s, char end) throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == end)
                break;
            i = this.process(environment, this, i, argumentList, s);
        }
        return argumentList;
    }

    /**
     * Parses a string until a nested character.
     *
     * @param environment The Environment containing the Parser.
     * @param s           The string to parse.
     * @param beginNest   The character that begins a scope.
     * @param endNest     The character that ends a scope.
     * @return The List of Arguments.
     * @throws JTerminalException If there was an error parsing the string.
     */
    default Pair<ArgumentList, Integer> nestedParse(Environment environment, String s, char beginNest, char endNest)
            throws JTerminalException {
        ArgumentList argumentList = new ArgumentList();
        int depth = 1, i = 0;
        for (; i < s.length(); i++) {
            if (s.charAt(i) == beginNest) {
                depth++;
            } else if (s.charAt(i) == endNest) {
                depth--;
                if (depth == 0)
                    break;
            } else {
                i = this.process(environment, this, i, argumentList, s);
            }
        }
        return new Pair<>(argumentList, i);
    }

    /**
     * Processes a string. If -1 is returned, ignores the Parser instead.
     *
     * @param environment    The Environment for the Parser.
     * @param parser         The CommandParser.
     * @param i              The current index of the Parser.
     * @param argumentList   The TokenList to process.
     * @param s              The original String.
     * @return The index for the Parser to resume, -1 to ignore the Parser.
     * @throws JTerminalException If there is an error parsing the string.
     */
    int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
