package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * ParseRule for embedded command.
 */

public class EmbeddedCommandsParser implements ParseRule {
    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (s.charAt(i) != '[')
            return -1;

        String command = "";
        int j = i + 1, depth = 1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new JTerminalException("Could not find matching ]", this);
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    command += s.charAt(j);
                    break;
                case '[':
                    depth++;
                    command += '[';
                    break;
                case ']':
                    depth--;
                    if (depth == 0)
                        break label;
                default:
                    command += s.charAt(j);
                    break;
            }
        }
        argumentList.add(new Argument(
                environment.getCommandHandler().handleInput(command, environment.getInput(), environment.getOutput())
        ));
        return j;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}