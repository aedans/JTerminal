package com.aedan.jterminal.input.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;
import com.alibaba.fastjson.JSON;

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
    public LinkedList<Character> reservedChars = new LinkedList<>();

    /**
     * The List of TokenizerRules.
     */
    public LinkedList<ParseRule> parseRules = new LinkedList<>();

    {
        reservedChars.add(';');
    }

    /**
     * Tokenizes a String.
     *
     * @param environment The Environment to parse for.
     * @param s           The String to parse.
     * @return The List of Tokens.
     */
    public LinkedList<TokenList> parse(Environment environment, String s) throws JTerminalException {
        LinkedList<TokenList> tokenLists = new LinkedList<>();
        tokenLists.add(new TokenList());
        for (int i = 0; i < s.length(); i++) {
            charSwitch:
            switch (s.charAt(i)) {
                case '\\':
                    i++;
                    if (i == s.length())
                        throw new JTerminalException("Could not find character to escape", this);
                    tokenLists.getLast().append(s.charAt(i));
                    break;
                case ' ':
                case '\n':
                case '\t':
                    tokenLists.getLast().nextToken();
                    break;
                case ';':
                    tokenLists.getLast().nextToken();
                    tokenLists.addLast(new TokenList());
                    s = s.substring(i);
                    i = 0;
                    break;
                default:
                    for (ParseRule parseRule : parseRules) {
                        if (parseRule.getIdentifier() == s.charAt(i)) {
                            i = parseRule.process(environment, s, i, tokenLists.getLast());
                            break charSwitch;
                        }
                    }
                    if (reservedChars.contains(s.charAt(i))) {
                        tokenLists.getLast().addToken(s.charAt(i));
                        break;
                    } else {
                        tokenLists.getLast().append(s.charAt(i));
                        break;
                    }
            }
        }
        tokenLists.getLast().nextToken();

        return tokenLists;
    }

    @Override
    public String toString() {
        return "\"parser\":" + JSON.toJSONString(this, true);
    }
}
