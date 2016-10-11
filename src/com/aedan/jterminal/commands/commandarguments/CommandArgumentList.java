package com.aedan.jterminal.commands.commandarguments;

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
     * @return The return code of the function, -2 if there is an illegal type, -1 if there are too few arguments,
     *                  0 if the arguments match, and 1 if there are too many arguments.
     */
    public int matches(ArgumentType... argumentTypes) {
        if (args.length > argumentTypes.length + 1)
            return 1;

        if (args.length < argumentTypes.length + 1)
            return -1;

        for (int i = 1; i < args.length; i++) {
            if (argumentTypes[i - 1] != ArgumentType.STRING && !args[i].argumentType.isSubset(argumentTypes[i - 1]))
                return -2;
        }

        return 0;
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
