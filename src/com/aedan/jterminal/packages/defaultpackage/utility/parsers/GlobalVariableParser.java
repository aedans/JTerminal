package com.aedan.jterminal.packages.defaultpackage.utility.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.CommandParser;
import com.aedan.jterminal.parser.Parser;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for global variables.
 */

public class GlobalVariableParser implements Parser {
    @Override
    public int process(Environment environment, CommandParser commandParser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (s.charAt(i) != '$')
            return -1;

        String varName = "";
        int j = i + 1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                break;
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    varName += s.charAt(j);
                    break;
                case ' ':
                    break label;
                default:
                    varName += s.charAt(j);
                    break;
            }
        }
        Object value = environment.getGlobalVariables().get(varName);
        if (value == null)
            throw new JTerminalException("Could not find global variable with name " + varName, this);
        else
            argumentList.add(new Argument(value));
        return j;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
