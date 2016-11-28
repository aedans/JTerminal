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

public class GetGlobalVariableParser extends Parser {
    private HashMap<String, Object> variables;

    public GetGlobalVariableParser(HashMap<String, Object> variables) {
        this.variables = variables;
    }

    @Override
    public boolean parse(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '$')
            return false;
        in.next();

        // TODO: Remove getLast()
        String varName = parser.parseUntil(environment, in, ' ').getLast().toString();

        Object value = variables.get(varName);
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
