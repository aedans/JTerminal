package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.parser.ParseException;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for String Literals
 */

public class StringLiteralParser implements Parser<StringIterator, ArgumentList> {
    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '\"')
            return false;
        in.next();

        String literal = "";
        label:
        while (true) {
            if (!in.hasNext())
                throw new ParseException("Could not find matching \"", this);
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
