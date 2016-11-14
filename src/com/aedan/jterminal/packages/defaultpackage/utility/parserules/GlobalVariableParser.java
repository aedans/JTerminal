package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * ParseRule for global variables.
 */

public class GlobalVariableParser implements ParseRule {
    @Override
    public char getIdentifier() {
        return '$';
    }

    @Override
    public int process(Environment environment, String s, int i, ArgumentList tokenList) throws JTerminalException {
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
            tokenList.add(value.toString());
        return j;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
