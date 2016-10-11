package com.aedan.jterminal.commands.commandarguments;

import com.aedan.jterminal.commands.commandhandler.CommandHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class CommandArgumentList {

    /**
     * The value of the CommandArgumentList.
     */
    private final CommandArgument[] args;

    /**
     * Default CommandArgumentList constructor.
     *
     * @param tokens The List of values for the CommandArgumentList.
     */
    public CommandArgumentList(List<String> tokens) {
        args = new CommandArgument[tokens.size()];
        args[0] = new CommandArgument(tokens.get(0), ArgumentType.COMMANDIDENTIFIER);
        for (int i = 1; i < args.length; i++) {
            args[i] = new CommandArgument(tokens.get(i));
        }
    }

    /**
     * Checks to see if the CommandArgumentList matches the given format.
     *
     * @param argumentTypes The format for the CommandArgumentList.
     * @throws CommandHandler.CommandHandlerException If the format for the CommandArgumentList does not match.
     */
    public void checkMatches(ArgumentType... argumentTypes) throws CommandHandler.CommandHandlerException {
        cCheckMatches(argumentTypes[0] == ArgumentType.COMMANDIDENTIFIER, argumentTypes);
    }

    private void cCheckMatches(boolean ci, ArgumentType... argumentTypes) throws CommandHandler.CommandHandlerException {
        if (args.length > (ci ? argumentTypes.length : argumentTypes.length + 1))
            throw new CommandHandler.CommandHandlerException("More arguments given then required " +
                    "(given: " + (args.length - 1) + ", required: " + (ci ? argumentTypes.length - 1 : argumentTypes.length) + ")");

        if (args.length < (ci ? argumentTypes.length : argumentTypes.length + 1))
            throw new CommandHandler.CommandHandlerException("Less arguments given then required " +
                    "(given: " + (args.length - 1) + ", required: " + (ci ? argumentTypes.length - 1 : argumentTypes.length) + ")");

        for (int i = ci ? 0 : 1; i < args.length; i++) {
            if (argumentTypes[ci ? i : i - 1] != ArgumentType.STRING && !args[i].argumentType.isSubset(argumentTypes[ci ? i : i - 1]))
                throw new CommandHandler.CommandHandlerException(
                        "Found " + args[i].argumentType + ", expected " + argumentTypes[ci ? i : i - 1]);
        }
    }

    public int length() {
        return args.length;
    }

    public CommandArgument get(int i) {
        return args[i];
    }

    @Override
    public String toString() {
        return Arrays.toString(args);
    }

}
