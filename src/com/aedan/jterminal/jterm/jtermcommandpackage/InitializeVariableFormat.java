package com.aedan.jterminal.jterm.jtermcommandpackage;

import com.aedan.jterminal.command.CommandFormat;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.List;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class InitializeVariableFormat implements CommandFormat {
    @Override
    public boolean matches(List<String> tokens) throws CommandHandler.CommandHandlerException {
        return tokens.size() >= 2 && Objects.equals(tokens.get(1), "=");
    }

    @Override
    public void handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException {
        environment.addGlobalVariable(tokens.get(0), tokens.get(2));
    }
}
