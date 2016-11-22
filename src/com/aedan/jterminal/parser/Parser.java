package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating CommandParser rules.
 */

public interface Parser {
    /**
     * Processes a string. If -1 is returned, ignores the Parser instead.
     *
     * @param environment    The Environment for the Parser.
     * @param commandParser  The CommandParser.
     * @param i              The current index of the Parser.
     * @param argumentList   The TokenList to process.
     * @param s              The original String.
     * @return The index for the Parser to resume, -1 to ignore the Parser.
     * @throws JTerminalException If there is an error parsing the string.
     */
    int process(Environment environment, CommandParser commandParser, int i, ArgumentList argumentList, String s)
            throws JTerminalException;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
