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

public class CharacterEscapeParser implements Parser {
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '\\')
            return false;

        in.next();
        if (!in.hasNext())
            throw new JTerminalException("Could not find character to escape", this);

        argumentList.add(new Argument(in.next()));
        return true;
    }
}
