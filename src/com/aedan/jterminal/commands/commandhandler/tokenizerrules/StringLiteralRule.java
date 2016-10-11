package com.aedan.jterminal.commands.commandhandler.tokenizerrules;

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
    public int process(TokenList tokenList, String s, int i) {
        int j = i+1;
        label:
        for (; true; j++) {
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
