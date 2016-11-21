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
    /**
     * Processes a ParseRule. If -1 is returned, ignores the ParseRule instead.
     *
     * @param environment    The Environment for the ParseRule.
     * @param parser         The current Parser
     * @param i              The current index of the Parser.
     * @param argumentList   The TokenList to process.
     * @param s              The original String.
     * @return The index for the Parser to resume, -1 to ignore the ParseRule.
     * @throws JTerminalException If there is an error parsing the TokenList.
     */
    int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s) throws JTerminalException;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
