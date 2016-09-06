package com.aedan.jterminal.commands.defaultpackage.utility.commands;

import com.aedan.jterminal.environment.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
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
                "   Outputs [string].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING);

        output.println(args.get(1));
    }

}
