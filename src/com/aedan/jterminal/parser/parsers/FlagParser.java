package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class FlagParser implements Parser {
    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (s.charAt(i) != '-')
            return -1;

        String name = "";
        for (i++; i < s.length(); i++) {
            if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))
                name += s.charAt(i);
            else
                return -1;
        }
        argumentList.flags.add(name);
        return i;
    }
}
