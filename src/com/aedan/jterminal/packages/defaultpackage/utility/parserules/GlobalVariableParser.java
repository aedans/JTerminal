package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.TokenList;

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
    public int process(Environment environment, String s, int i, TokenList tokenList) throws JTerminalException {
        tokenList.nextToken();
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
            tokenList.addToken(value.toString());
        return j;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
