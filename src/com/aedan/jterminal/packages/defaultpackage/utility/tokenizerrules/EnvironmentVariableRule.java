package com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for environment variables.
 */

public class EnvironmentVariableRule implements TokenizerRule {

    private Environment environment;

    /**
     * Default EnvironmentVariableRule constructor.
     */
    public EnvironmentVariableRule(Environment environment) {
        this.environment = environment;
    }

    @Override
    public char getIdentifier() {
        return '%';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
        tokenList.nextToken();
        String varName = "";
        int j = i+1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new CommandHandler.CommandHandlerException("Could not find matching %", this);
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    varName += s.charAt(j);
                    break;
                case '%':
                    break label;
                default:
                    varName += s.charAt(j);
                    break;
            }
        }
        Object value = environment.getEnvironmentVariables().get(varName);
        if (value == null)
            throw new CommandHandler.CommandHandlerException("Could not find environment variable with name " + varName, this);
        else
            tokenList.addToken(value.toString());
        return j;
    }
}
