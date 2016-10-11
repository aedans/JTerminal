package com.aedan.jterminal.commands.commandhandler.tokenizerrules;

import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for global variables.
 */

public class GlobalVariableRule implements TokenizerRule {

    /**
     * The HashMap of global variables.
     */
    private HashMap<String, String> globalVariables;

    /**
     * Default GlobalVariableRule constructor.
     *
     * @param globalVariables The HashMap of global variables.
     */
    public GlobalVariableRule(HashMap<String, String> globalVariables) {
        this.globalVariables = globalVariables;
    }

    @Override
    public char getIdentifier() {
        return '[';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
        String varName = "";
        int j = i + 1, depth = 1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new CommandHandler.CommandHandlerException("Could not find matching ]");
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    varName += s.charAt(j);
                    break;
                case '[':
                    depth++;
                    varName += '[';
                    break;
                case ']':
                    depth--;
                    if (depth == 0)
                        break label;
                default:
                    varName += s.charAt(j);
                    break;
            }
        }
        String value = globalVariables.get(varName);
        if (value == null)
            throw new CommandHandler.CommandHandlerException("Could not find global variable with name " + varName);
        else
            tokenList.addToken(value);
        return j;
    }

}
