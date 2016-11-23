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

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
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
