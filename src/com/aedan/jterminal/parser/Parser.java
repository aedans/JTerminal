package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.parserules.FlagParser;
import com.aedan.jterminal.parser.parserules.NumberParser;
import com.aedan.jterminal.parser.parserules.StringLiteralParser;
import com.alibaba.fastjson.JSON;

import java.util.LinkedList;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * tokenizer for the CommandHandler.
 */

public class Parser {
    /**
     * The List of TokenizerRules.
     */
    private LinkedList<ParseRule> parseRules = new LinkedList<>();

    {
        parseRules.add(new FlagParser());
        parseRules.add(new NumberParser());
        parseRules.add(new StringLiteralParser());
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
        for (int i = 0, j; i < s.length(); i++) {
            charSwitch:
            switch (s.charAt(i)) {
                case '\\':
                    i++;
                    if (i == s.length())
                        throw new JTerminalException("Could not find character to escape", this);
                    argumentLists.getLast().add(new Argument(s.charAt(i)));
                    break;
                case ';':
                    argumentLists.addLast(new ArgumentList());
                    s = s.substring(i + 1);
                    i = 0;
                    break;
                default:
                    for (ParseRule parseRule : parseRules) {
                        j = parseRule.process(environment, this, i, argumentLists.getLast(), s);
                        if (j != -1) {
                            i = j;
                            break charSwitch;
                        }
                    }

                    String literal = "";
                    for (; i < s.length(); i++) {
                        if (s.charAt(i) == ' ')
                            break;
                        literal += s.charAt(i);
                    }
                    if (!literal.isEmpty())
                        argumentLists.getLast().add(new Argument(literal, String.class));
                    break;
            }
        }

        return argumentLists;
    }

    public LinkedList<ParseRule> getParseRules() {
        return parseRules;
    }

    @Override
    public String toString() {
        return "\"parser\":" + JSON.toJSONString(this, true);
    }
}
