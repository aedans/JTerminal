package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.lang.reflect.Field;

/**
 * Created by Aedan Smith.
 */

public class FieldAccessParser implements Parser {
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        try {
            if (!(in.peek() == ':' && in.peek(1) != ':'))
                return false;

            String name = "";
            while (true) {
                if (in.peek() == ' ') {
                    break;
                } else {
                    name += in.next();
                }
            }
            Field f = argumentList.getLast().value.getClass().getField(name);
            argumentList.setLast(new Argument(f.get(argumentList.getLast().value)));
            return true;
        } catch (Exception e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
