package com.aedan.jcmd.commands.default_commands;

import com.aedan.jcmd.commands.Command;
import com.aedan.jcmd.commands.CommandHandler;
import com.aedan.jcmd.Output;

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
    public void parse(String s, String directory, Output output) throws CommandHandler.CommandHandlerException {
        output.println(getArgValues(s)[0]);
    }

}
