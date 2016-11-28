package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith.
 */

public class CharacterEscapeParser extends Parser {
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '\\')
            return false;
        in.next();

        // TODO: Add escape sequences
        argumentList.add(new Argument(in.next()));
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
