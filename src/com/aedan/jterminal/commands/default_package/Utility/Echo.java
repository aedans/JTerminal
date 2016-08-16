package com.aedan.jterminal.commands.default_package.Utility;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.output.Output;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * Default Command.
 */

class Echo extends Command {

    Echo() {
        super("echo -s", "echo", 1, "Outputs text.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        output.println(getArgValues(in)[0]);
    }

}
