package com.aedan.jterminal.input.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
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
    public LinkedList<ArgumentList> parse(Environment environment, String s) throws JTerminalException {
        LinkedList<ArgumentList> argumentLists = new LinkedList<>();
        argumentLists.add(new ArgumentList());
        String currentToken = "";
        for (int i = 0; i < s.length(); i++) {
            charSwitch:
            switch (s.charAt(i)) {
                case '\\':
                    i++;
                    if (i == s.length())
                        throw new JTerminalException("Could not find character to escape", this);
                    argumentLists.getLast().add(new Argument(s.charAt(i)));
                    break;
                case ' ':
                case '\n':
                case '\t':
                    if (!currentToken.isEmpty()) {
                        argumentLists.getLast().add(currentToken);
                        currentToken = "";
                    }
                    break;
                case ';':
                    if (!currentToken.isEmpty()) {
                        argumentLists.getLast().add(currentToken);
                        currentToken = "";
                    }
                    argumentLists.addLast(new ArgumentList());
                    s = s.substring(i);
                    i = 0;
                    break;
                default:
                    for (ParseRule parseRule : parseRules) {
                        if (parseRule.getIdentifier() == s.charAt(i)) {
                            if (!currentToken.isEmpty()) {
                                argumentLists.getLast().add(currentToken);
                                currentToken = "";
                            }
                            i = parseRule.process(environment, this, i, argumentLists.getLast(), s);
                            break charSwitch;
                        }
                    }
                    if (reservedChars.contains(s.charAt(i))) {
                        argumentLists.getLast().add(new Argument(s.charAt(i)));
                        break;
                    } else {
                        currentToken += s.charAt(i);
                        break;
                    }
            }
        }
        if (!currentToken.isEmpty())
            argumentLists.getLast().add(currentToken);

        return argumentLists;
    }

    @Override
    public String toString() {
        return "\"parser\":" + JSON.toJSONString(this, true);
    }
}
