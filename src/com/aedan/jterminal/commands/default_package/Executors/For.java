package com.aedan.jterminal.commands.default_package.Executors;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArguments;
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
    public void parse(CommandInput input, CommandArguments args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.INTEGER, ArgumentType.INTEGER, ArgumentType.STRING, ArgumentType.STRING);
        for (int i = Integer.parseInt(args.getArg(1).value); i < Integer.parseInt(args.getArg(2).value); i++) {
            commandHandler.addVariable(new Variable(args.getArg(3).value, String.valueOf(i)));
            commandHandler.handleInput(
                    input,
                    args.getArg(4).value,
                    output
            );
            commandHandler.removeVariable(args.getArg(3).value);
        }
    }

}
