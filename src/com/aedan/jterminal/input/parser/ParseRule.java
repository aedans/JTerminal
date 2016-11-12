package com.aedan.jterminal.input.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating Parser rules.
 */

public interface ParseRule {
    /**
     * @return The character upon which to call the ParseRule.
     */
    char getIdentifier();

    /**
     * Processes a ParseRule.
     *
     * @param environment The Environment for the ParseRule.
     * @param s           The original String.
     * @param i           The current index of the Parser.
     * @param tokenList   The TokenList to process.
     * @return The index for the Parser to resume.
     * @throws JTerminalException If there is an error parsing the TokenList.
     */
    int process(Environment environment, String s, int i, TokenList tokenList) throws JTerminalException;
}
