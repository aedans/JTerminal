package com.aedan.jterminal.packages.defaultpackage.utility.parsers.variables;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for global variables.
 */

public class GetGlobalVariableParser implements Parser<ArgumentList> {
    private Parser<StringBuilder> parser = (environment, stringBuilder, in) -> {
        stringBuilder.append(in.next());
        return true;
    };

    @Override
    public boolean parse(Environment environment, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '$')
            return false;
        in.next();

        StringBuilder builder = new StringBuilder();
        parser.parseUntil(environment, in, builder, s ->
                (s.peek() >= 'a' && s.peek() <= 'z')
                        || (s.peek() >= 'A' && s.peek() <= 'Z')
                        || (s.peek() >= '0' && s.peek() <= '9')
                        || s.peek() == '_');
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
