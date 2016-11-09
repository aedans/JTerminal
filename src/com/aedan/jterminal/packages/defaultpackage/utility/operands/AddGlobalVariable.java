package com.aedan.jterminal.packages.defaultpackage.utility.operands;

import com.aedan.jterminal.command.Operand;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.StringOutput;

import java.util.List;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default Operand.
 */

public class AddGlobalVariable implements Operand {

    public AddGlobalVariable(Environment environment){
        environment.getCommandHandler().getTokenizer().addReservedChar('=');
    }

    @Override
    public boolean handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException {
        int index = tokens.indexOf("=");
        if (index == -1)
            return false;

        StringOutput value = new StringOutput(), name = new StringOutput();
        environment.getCommandHandler().handleInput(input, value, tokens.subList(index+1, tokens.size()));
        environment.getCommandHandler().handleInput(input, name, tokens.subList(0, index));

        environment.addGlobalVariable(name.getString().trim(), value.getString().trim());
        return true;
    }
}
