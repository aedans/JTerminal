package com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for global variables.
 */

public class GlobalVariableRule implements TokenizerRule {

    private Environment environment;

    /**
     * Default GlobalVariableRule constructor.
     */
    public GlobalVariableRule(Environment environment) {
        this.environment = environment;
    }

    @Override
    public char getIdentifier() {
        return '$';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
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
            throw new CommandHandler.CommandHandlerException("Could not find global variable with name " + varName);
        else
            tokenList.addToken(value.toString());
        return j;
    }
}
