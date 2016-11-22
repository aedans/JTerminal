package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.CommandParser;
import com.aedan.jterminal.parser.Parser;

import java.lang.reflect.Field;

/**
 * Created by Aedan Smith.
 */

public class FieldAccessParser implements Parser {
    @Override
    public int process(Environment environment, CommandParser commandParser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        try {
            if (!(s.charAt(i) == ':' && s.charAt(i + 1) != ':'))
                return -1;

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