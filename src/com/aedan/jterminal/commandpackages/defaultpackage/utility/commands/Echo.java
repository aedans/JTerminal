package com.aedan.jterminal.commandpackages.defaultpackage.utility.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * Default Command.
 */

public class Echo extends Command {

    public Echo() {
        super("echo");
        this.properties[0] = "Outputs text.";
        this.properties[1] =
                "echo [string]:\n" +
                        "    Outputs [string].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING);

        output.println(args.get(1));
    }

}
