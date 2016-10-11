package com.aedan.jterminal.input.tokenizer;

import com.aedan.jterminal.commands.commandhandler.CommandHandler;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * tokenizer for the CommandHandler.
 */

public class Tokenizer {

    /**
     * The List of reserved characters for the Tokenizer.
     */
    private LinkedList<Character> reservedChars = new LinkedList<>();

    /**
     * The List of TokenizerRules.
     */
    private LinkedList<TokenizerRule> tokenizerRules = new LinkedList<>();

    /**
     * Tokenizes a String.
     *
     * @param s The String to tokenize.
     * @return The List of Tokens.
     */
    public ArrayList<String> tokenize(String s) throws CommandHandler.CommandHandlerException {
        TokenList tokenList = new TokenList();
        for (int i = 0; i < s.length(); i++) {
            charSwitch:
            switch (s.charAt(i)){
                case '\\':
                    i++;
                    tokenList.append(s.charAt(i));
                    break;
                case ' ':
                    tokenList.nextToken();
                    break;
                default:
                    for (TokenizerRule tokenizerRule : tokenizerRules) {
                        if (tokenizerRule.getIdentifier() == s.charAt(i)) {
                            i = tokenizerRule.process(tokenList, s, i);
                            break charSwitch;
                        }
                    }
                    if (reservedChars.contains(s.charAt(i))){
                        tokenList.addToken(s.charAt(i));
                        break;
                    } else {
                        tokenList.append(s.charAt(i));
                        break;
                    }
            }
        }
        tokenList.nextToken();

        return tokenList.getTokens();
    }

    /**
     * Adds a character to the reserved characters list.
     *
     * @param c The character to reserve.
     */
    public void addReservedChar(char c){
        if (!reservedChars.contains(c))
            reservedChars.add(c);
    }

    /**
     * Adds a TokenizerRule to the List of TokenizerRules.
     *
     * @param tokenizerRule The TokenizerRule to add.
     */
    public void addTokenizerRule(TokenizerRule tokenizerRule){
        tokenizerRules.add(tokenizerRule);
    }

}
