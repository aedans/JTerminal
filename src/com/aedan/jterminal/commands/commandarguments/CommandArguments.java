package com.aedan.jterminal.commands.commandarguments;

import com.aedan.jterminal.commands.CommandHandler;

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
        args[0] = new CommandArgument(strings[0], ArgumentType.COMMANDIDENTIFIER);
        for (int i = 1; i < args.length; i++) {
            args[i] = new CommandArgument(strings[i]);
        }
    }

    public void checkMatches(ArgumentType... argumentTypes) throws CommandHandler.CommandHandlerException {
        cCheckMatches(argumentTypes[0] == ArgumentType.COMMANDIDENTIFIER, argumentTypes);
    }

    private void cCheckMatches(boolean ci, ArgumentType... argumentTypes) throws CommandHandler.CommandHandlerException {
        if (args.length > (ci ? argumentTypes.length : argumentTypes.length+1))
            throw new CommandHandler.CommandHandlerException("More arguments given then required " +
                    "(given: " + (args.length-1) + ", required: " + (ci ? argumentTypes.length-1 : argumentTypes.length) + ")");

        if (args.length < (ci ? argumentTypes.length : argumentTypes.length+1))
            throw new CommandHandler.CommandHandlerException("Less arguments given then required " +
                    "(given: " + (args.length-1) + ", required: " + (ci ? argumentTypes.length-1 : argumentTypes.length) + ")");

        for (int i = ci ? 0 : 1; i < args.length; i++) {
            if (argumentTypes[ci ? i : i-1] != ArgumentType.STRING && args[i].argumentType != argumentTypes[ci ? i : i-1])
                throw new CommandHandler.CommandHandlerException(
                        "Found " + args[i].argumentType + ", expected " + argumentTypes[ci ? i : i-1]);
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
