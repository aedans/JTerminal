package com.aedan.jterminal.input.parser.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class FlagParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return s.charAt(i) == '-';
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        String name = "";
        for (i++; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                break;
            } else {
                name += s.charAt(i);
            }
        }
        argumentList.flags.add(name);
        return i;
    }
}
