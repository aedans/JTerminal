package com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.TokenList;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * ParseRule for String Literals
 */

public class StringLiteralRule implements ParseRule {

    @Override
    public char getIdentifier() {
        return '\"';
    }

    @Override
    public int process(Environment environment, String s, int i, TokenList tokenList) throws CommandHandler.CommandHandlerException {
        tokenList.nextToken();
        int j = i+1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new CommandHandler.CommandHandlerException("Could not find matching \"", this);
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
        tokenList.nextToken();
        return j;
    }
}
