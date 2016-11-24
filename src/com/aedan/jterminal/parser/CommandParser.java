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
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        for (Parser p : parsers) {
            if (!in.hasNext())
                return true;
            if (p.apply(environment, this, argumentList, in))
                return true;
        }

        String v = "";
        while (in.hasNext() && in.peek() != ' ' && in.peek() != '\n') {
            v += in.next();
        }

        if (!v.isEmpty()) {
            argumentList.add(new Argument(v, String.class));
            return true;
            // TODO: Better EOS detection
        } else if (in.peek() != ' ' && in.peek() != '\n') {
            v += in.next();
            argumentList.add(new Argument(v, String.class));
            return true;
        } else {
            in.next();
            return false;
        }
    }

    public LinkedList<Parser> getParsers() {
        return parsers;
    }

    @Override
    public String toString() {
        return "\"commandParser\":" + JSON.toJSONString(this, true);
    }
}
