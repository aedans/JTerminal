package com.aedan.jterminal.input.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating Parser rules.
 */

public interface ParseRule {
    boolean matches(String s, int i);

    /**
     * Processes a ParseRule.
     *
     * @param environment The Environment for the ParseRule.
     * @param parser
     *@param i           The current index of the Parser.
     * @param argumentList   The TokenList to process.
     * @param s           The original String.    @return The index for the Parser to resume.
     * @throws JTerminalException If there is an error parsing the TokenList.
     */
    int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s) throws JTerminalException;
}
