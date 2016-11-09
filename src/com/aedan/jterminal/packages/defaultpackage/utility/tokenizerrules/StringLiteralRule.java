package com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for String Literals
 */

public class StringLiteralRule implements TokenizerRule {

    @Override
    public char getIdentifier() {
        return '\"';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
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
