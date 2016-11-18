package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;

import java.lang.reflect.Field;

/**
 * Created by Aedan Smith.
 */

public class FieldAccessParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return s.charAt(i) == ':' && s.charAt(i + 1) != ':';
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        try {
            String name = "";
            for (i++; i < s.length(); i++) {
                if (s.charAt(i) == ' ')
                    break;
                else if (s.charAt(i) == '.' || s.charAt(i) == ':') {
                    i--;
                    break;
                } else
                    name += s.charAt(i);
            }
            Field f = argumentList.getLast().value.getClass().getField(name);
            argumentList.setLast(new Argument(f.get(argumentList.getLast().value)));
            return i;
        } catch (Exception e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
