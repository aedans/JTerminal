package com.aedan.jterminal.input.parser;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.JTerminalException;

import java.util.LinkedList;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * tokenizer for the CommandHandler.
 */

public class Parser {

    /**
     * The List of reserved characters for the Parser.
     */
    private LinkedList<Character> reservedChars = new LinkedList<>();

    /**
     * The List of TokenizerRules.
     */
    private LinkedList<ParseRule> parseRules = new LinkedList<>();

    /**
     * Tokenizes a String.
     *
     * @param environment The Environment to parse for.
     * @param s           The String to parse.
     * @return The List of Tokens.
     */
    public TokenList parse(Environment environment, String s) throws JTerminalException {
        TokenList tokenList = new TokenList();
        for (int i = 0; i < s.length(); i++) {
            charSwitch:
            switch (s.charAt(i)) {
                case '\\':
                    i++;
                    if (i == s.length()) {
                        throw new JTerminalException("Could not find character to escape", this);
                    }
                    tokenList.append(s.charAt(i));
                    break;
                case ' ':
                    tokenList.nextToken();
                    break;
                default:
                    for (ParseRule parseRule : parseRules) {
                        if (parseRule.getIdentifier() == s.charAt(i)) {
                            i = parseRule.process(environment, s, i, tokenList);
                            break charSwitch;
                        }
                    }
                    if (reservedChars.contains(s.charAt(i))) {
                        tokenList.addToken(s.charAt(i));
                        break;
                    } else {
                        tokenList.append(s.charAt(i));
                        break;
                    }
            }
        }
        tokenList.nextToken();

        return tokenList;
    }

    /**
     * Adds a character to the reserved characters list.
     *
     * @param c The character to reserve.
     */
    public void addReservedChar(char c) {
        if (!reservedChars.contains(c))
            reservedChars.add(c);
    }

    /**
     * Adds a ParseRule to the List of TokenizerRules.
     *
     * @param parseRule The ParseRule to addTo.
     */
    public void addTokenizerRule(ParseRule parseRule) {
        parseRules.add(parseRule);
    }
}
