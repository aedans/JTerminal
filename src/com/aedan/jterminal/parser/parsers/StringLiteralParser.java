package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for String Literals
 */

public class StringLiteralParser extends Parser {
    @Override
    public boolean parse(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '\"')
            return false;
        in.next();

        String literal = "";
        label:
        while (true) {
            if (!in.hasNext())
                throw new JTerminalException("Could not find matching \"", this);
            switch (in.peek()) {
                // TODO: Parse with CharacterEscapeParser
                case '\\':
                    in.next();
                    literal += in.next();
                    break;
                case '\"':
                    in.next();
                    break label;
                default:
                    literal += in.next();
                    break;
            }
        }
        argumentList.add(literal);
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
