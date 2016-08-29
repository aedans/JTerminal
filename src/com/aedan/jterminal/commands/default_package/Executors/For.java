package com.aedan.jterminal.commands.default_package.Executors;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandArgument;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.variables.Variable;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

class For extends Command {

    private CommandHandler commandHandler;

    For(CommandHandler commandHandler) {
        super("for", "Iterates a command and stores the current iteration in a variable.");
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, CommandArgument[] args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        for (int i = Integer.parseInt(args[1].value); i < Integer.parseInt(args[2].value); i++) {
            commandHandler.addVariable(new Variable(args[3].value, String.valueOf(i)));
            commandHandler.handleInput(
                    input,
                    args[4].value,
                    output
            );
            commandHandler.removeVariable(args[3].value);
        }
    }

}
