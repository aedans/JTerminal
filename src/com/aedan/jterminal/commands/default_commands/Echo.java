package com.aedan.jterminal.commands.default_commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.Output;

/**
 * Created by Aedan Smith on 8/14/2016.
 *
 * Default Command.
 */

public class Echo extends Command {

    public Echo() {
        super("echo -s", "echo", 1);
    }

    @Override
    public void parse(String in, String directory, Output output) throws CommandHandler.CommandHandlerException {
        output.println(getArgValues(in)[0]);
    }

}
