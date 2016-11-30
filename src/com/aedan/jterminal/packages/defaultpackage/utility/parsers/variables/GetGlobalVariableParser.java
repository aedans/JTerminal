package com.aedan.jterminal.packages.defaultpackage.utility.parsers.variables;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for global variables.
 */

public class GetGlobalVariableParser extends Parser<ArgumentList> {
    private Parser<StringBuilder> parser = new Parser<StringBuilder>() {
        @Override
        protected boolean parse(Environment environment, StringBuilder stringBuilder, StringIterator in)
                throws JTerminalException {
            stringBuilder.append(in.next());
            return false;
        }
    };

    @Override
    public boolean parse(Environment environment, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '$')
            return false;
        in.next();

        StringBuilder builder = new StringBuilder();
        parser.parseUntil(environment, in, builder, ' ');
        String varName = builder.toString();

        Object value = ((HashMap<String, Object>) environment.getEnvironmentVariable("VARS")).get(varName);
        if (value == null)
            throw new JTerminalException("Could not find global variable with name \"" + varName + "\"", this);
        else
            argumentList.add(new Argument(value));
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
