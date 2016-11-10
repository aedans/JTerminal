package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.TokenList;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * ParseRule for String Literals
 */

public class StringLiteralParser implements ParseRule {

    @Override
    public char getIdentifier() {
        return '\"';
    }

    @Override
    public int process(Environment environment, String s, int i, TokenList tokenList) throws JTerminalException {
        tokenList.nextToken();
        int j = i + 1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new JTerminalException("Could not find matching \"", this);
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    tokenList.append(s.charAt(j));
                    break;
                case '\"':
                    break label;
                default:
                    tokenList.append(s.charAt(j));
                    break;
            }
        }
        if (tokenList.getCurrentToken().isEmpty()) {
            tokenList.add("");
        } else {
            tokenList.nextToken();
        }
        return j;
    }
}
