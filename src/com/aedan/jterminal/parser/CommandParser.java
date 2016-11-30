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
import java.util.Objects;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for the CommandHandler.
 */

public class CommandParser extends Parser<ArgumentList> {
    /**
     * The List of Parsers.
     */
    private LinkedList<Parser<ArgumentList>> parsers = new LinkedList<>();
    private String v = "";

    {
        parsers.add(new CharacterEscapeParser());
        parsers.add(new FlagParser());
        parsers.add(new NumberParser());
        parsers.add(new StringLiteralParser());
    }

    @Override
    public boolean parse(Environment environment, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        for (Parser<ArgumentList> p : parsers) {
            if (!in.hasNext())
                return false;
            if (p.parse(environment, argumentList, in))
                return true;
        }

        if (in.hasNext() && in.peek() != ' ' && in.peek() != '\n') {
            v += in.next();
            return true;
        } else if (!v.isEmpty()) {
            argumentList.add(new Argument(v));
            v = "";
            in.next();
            return true;
        } else {
            in.next();
            return true;
        }
    }

    @Override
    protected void onEndParse(Environment environment, ArgumentList arguments, StringIterator in) {
        if (!Objects.equals(v, ""))
            arguments.add(new Argument(v));
        v = "";
    }

    public void addParser(Parser parser) {
        if (!this.parsers.contains(parser))
            this.parsers.add(parser);
    }

    public LinkedList<Parser<ArgumentList>> getParsers() {
        return parsers;
    }

    @Override
    public String toString() {
        return "\"commandParser\":" + JSON.toJSONString(this, true);
    }
}
