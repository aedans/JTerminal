package com.aedan.jterminal.commands.default_package.Executors;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.Output;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

class For extends Command {

    private CommandHandler commandHandler;

    For(CommandHandler commandHandler) {
        super("for -i -i -s -s", "for", 4, "Iterates a command and stores the current iteration in a variable.");
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        String[] args = getArgValues(in);
        for (int i = Integer.parseInt(args[0]); i < Integer.parseInt(args[1]); i++) {
            commandHandler.handleInput(
                    input,
                    args[3].replaceAll("\\[" + args[2] + "\\]", String.valueOf(i)),
                    output
            );
        }
    }

}
