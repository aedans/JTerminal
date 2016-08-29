package com.aedan.jterminal.commands.commandarguments;

import java.util.Arrays;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Class for passing arguments to Commands.
 */

public class CommandArguments {

    /**
     * The value of the CommandArguments.
     */
    public CommandArgument[] args;

    public CommandArguments(String[] strings) {
        args = new CommandArgument[strings.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = new CommandArgument(strings[i]);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(args);
    }

    public CommandArgument getArg(int i) {
        return args[i];
    }

}
