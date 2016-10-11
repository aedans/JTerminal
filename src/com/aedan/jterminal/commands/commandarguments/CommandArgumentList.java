package com.aedan.jterminal.commands.commandarguments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class CommandArgumentList {

    /**
     * The value of the CommandArgumentList.
     */
    private final ArrayList<CommandArgument> args = new ArrayList<>();

    /**
     * The List of flags in the CommandArgumenList.
     */
    private final LinkedList<String> flags = new LinkedList<>();

    /**
     * Default CommandArgumentList constructor.
     *
     * @param tokens The List of values for the CommandArgumentList.
     */
    public CommandArgumentList(List<String> tokens) {
        args.add(new CommandArgument(tokens.get(0), ArgumentType.COMMANDIDENTIFIER));
        for (int i = 1; i < tokens.size(); i++) {
            if (Objects.equals(tokens.get(i), "-")) {
                i++;
                flags.add(tokens.get(i));
            } else
                args.add(new CommandArgument(tokens.get(i)));
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
        if (args.size() > argumentTypes.length + 1)
            return 1;

        if (args.size() < argumentTypes.length + 1)
            return -1;

        for (int i = 1; i < args.size(); i++) {
            if (argumentTypes[i - 1] != ArgumentType.STRING && !args.get(i).argumentType.isSubset(argumentTypes[i - 1]))
                return -2;
        }

        return 0;
    }

    public boolean isFlagPresent(String flag){
        return flags.contains(flag);
    }

    public int size() {
        return args.size();
    }

    public CommandArgument get(int i) {
        return args.get(i);
    }

    public ArrayList<CommandArgument> getArgs() {
        return args;
    }

    public LinkedList<String> getFlags() {
        return flags;
    }

}
