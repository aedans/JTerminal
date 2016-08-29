package com.aedan.jterminal.commands.default_package.Utility;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * Default Command.
 */

class Echo extends Command {

    Echo() {
        super("echo", "Outputs text.");
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING);
        output.println(args.getArg(1));
    }

}
