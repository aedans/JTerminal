package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.TokenList;

/**
 * Created by Aedan Smith.
 */

public class MultipleCommandParser implements ParseRule {
    @Override
    public char getIdentifier() {
        return ';';
    }

    @Override
    public int process(Environment environment, String s, int i, TokenList tokenList) throws JTerminalException {
        tokenList.nextToken();
        environment.getCommandHandler().handleInput(tokenList);
        tokenList.clear();
        return i;
    }
}
