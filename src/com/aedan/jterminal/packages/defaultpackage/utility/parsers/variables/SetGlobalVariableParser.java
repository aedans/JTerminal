package com.aedan.jterminal.packages.defaultpackage.utility.parsers.variables;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default Operand.
 */

public class SetGlobalVariableParser extends Parser {
    @Override
    public boolean parse(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '=')
            return false;
        in.next();

        // TODO: Remove getLast()
        String name = parser.parse(environment, in.untilCurrent()).getLast().toString();
        Object value = parser.parse(environment, in.fromCurrent()).getLast().value;

        ((HashMap<String, Object>) environment.getEnvironmentVariable("VARS")).put(name, value);
        argumentList.clear();
        in.end();
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
