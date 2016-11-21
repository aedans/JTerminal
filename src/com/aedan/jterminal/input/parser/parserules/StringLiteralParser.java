package com.aedan.jterminal.input.parser.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * ParseRule for String Literals
 */

public class StringLiteralParser implements ParseRule {
    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (s.charAt(i) != '\"')
            return -1;

        int j = i + 1;
        String literal = "";
        label:
        for (; ; j++) {
            if (j >= s.length())
                throw new JTerminalException("Could not find matching \"", this);
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    literal += s.charAt(j);
                    break;
                case '\"':
                    break label;
                default:
                    literal += s.charAt(j);
                    break;
            }
        }
        argumentList.add(literal);
        return j;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
