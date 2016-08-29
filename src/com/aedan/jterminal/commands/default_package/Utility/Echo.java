package com.aedan.jterminal.commands.default_package.Utility;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandarguments.CommandArguments;
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
    public void parse(CommandInput input, CommandArguments args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        output.println(args.getArg(1));
    }

}
