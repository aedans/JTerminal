package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class CharacterEscapeParser implements Parser {
    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (s.charAt(i) != '\\')
            return -1;

        i++;
        if (i == s.length())
            throw new JTerminalException("Could not find character to escape", this);
        argumentList.add(new Argument(s.charAt(i)));
        return i;
    }
}
