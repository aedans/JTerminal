package com.aedan.jterminal.commands.commandhandler.tokenizerrules;

import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for environment variables.
 */

public class EnvironmentVariableRule implements TokenizerRule {

    /**
     * The HashMap of environment variables.
     */
    private HashMap<String, Supplier<String>> environmentVariables = new HashMap<>();

    /**
     * Default EnvironmentVariableRule constructor.
     *
     * @param environmentVariables The HashMap of environment variables.
     */
    public EnvironmentVariableRule(HashMap<String, Supplier<String>> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    @Override
    public char getIdentifier() {
        return '%';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
        String varName = "";
        int j = i+1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new CommandHandler.CommandHandlerException("Could not find matching %");
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
        Supplier<String> value = environmentVariables.get(varName);
        if (value == null)
            throw new CommandHandler.CommandHandlerException("Could not find environment variable with name " + varName);
        else
            tokenList.addToken(value.get());
        return j;
    }

}
