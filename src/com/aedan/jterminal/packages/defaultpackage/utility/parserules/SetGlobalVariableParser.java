package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default Operand.
 */

public class SetGlobalVariableParser implements ParseRule {
    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (s.charAt(i) != '=')
            return -1;

        Object name = environment.getCommandHandler().parser.parse(environment, s.substring(0, i)).get(0).get(0).value;
        Object value = environment.getCommandHandler().parser.parse(environment, s.substring(i + 1)).get(0).get(0).value;

        environment.addGlobalVariable(name.toString(), value);
        argumentList.clear();
        return s.length();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
