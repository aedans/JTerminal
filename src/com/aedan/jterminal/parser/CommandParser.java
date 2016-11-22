package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.parsers.CharacterEscapeParser;
import com.aedan.jterminal.parser.parsers.FlagParser;
import com.aedan.jterminal.parser.parsers.NumberParser;
import com.aedan.jterminal.parser.parsers.StringLiteralParser;
import com.alibaba.fastjson.JSON;
import javafx.util.Pair;

import java.util.LinkedList;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for the CommandHandler.
 */

public class CommandParser implements Parser {
    /**
     * The List of Parsers.
     */
    private LinkedList<Parser> parsers = new LinkedList<>();

    {
        parsers.add(new CharacterEscapeParser());
        parsers.add(new FlagParser());
        parsers.add(new NumberParser());
        parsers.add(new StringLiteralParser());
    }

    /**
     * Parses a string.
     *
     * @param environment The Environment containing the Parser.
     * @param s           The string to parse.
     * @return The List of Arguments.
     */
    public ArgumentList parse(Environment environment, String s) throws JTerminalException {
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
    public ArgumentList parseUntil(Environment environment, String s, char end) throws JTerminalException {
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
    public Pair<ArgumentList, Integer> nestedParse(Environment environment, String s, char beginNest, char endNest)
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

    @Override
    public int process(Environment environment, CommandParser commandParser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        int j;
        for (Parser p : parsers) {
            j = p.process(environment, this, i, argumentList, s);
            if (j != -1) {
                i = j;
                return i;
            }
        }

        String v = "";
        for (; i < s.length(); i++) {
            if (s.charAt(i) == ' ')
                break;
            v += s.charAt(i);
        }
        if (!v.isEmpty())
            argumentList.add(new Argument(v, String.class));

        return i;
    }

    public LinkedList<Parser> getParsers() {
        return parsers;
    }

    @Override
    public String toString() {
        return "\"commandParser\":" + JSON.toJSONString(this, true);
    }
}
