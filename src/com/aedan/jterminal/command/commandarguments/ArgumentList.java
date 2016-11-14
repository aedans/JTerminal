package com.aedan.jterminal.command.commandarguments;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class ArgumentList extends ArrayList<Argument> {
    /**
     * The List of flags in the ArgumentList.
     */
    public final LinkedList<String> flags = new LinkedList<>();

    /**
     * Checks to see if the ArgumentList matches the given format, and errors with the correct message if it doesn't.
     *
     * @param argumentTypes The format for the ArgumentList.
     * @throws JTerminalException If the format does not match.
     */
    public void checkMatches(Command command, ArgumentType... argumentTypes) throws JTerminalException {
        MatchResult matchResult = matches(argumentTypes);
        if (matchResult == MatchResult.CORRECT_ARGS)
            return;
        throw new JTerminalException(matchResult.getMessage(), command);
    }

    /**
     * Checks to see if the ArgumentList matches the given format.
     *
     * @param argumentTypes The format for the ArgumentList.
     * @return The MatchResult.
     */
    public MatchResult matches(ArgumentType... argumentTypes) {
        if (size() > argumentTypes.length + 1)
            return MatchResult.MORE_ARGS;

        if (size() < argumentTypes.length + 1)
            return MatchResult.LESS_ARGS;

        for (int i = 1; i < size(); i++) {
            if (argumentTypes[i - 1] != ArgumentType.STRING && !get(i).getArgumentType().isSubset(argumentTypes[i - 1]))
                return MatchResult.INCORRECT_ARGS;
        }

        return MatchResult.CORRECT_ARGS;
    }

    public void add(char c) {
        this.add(new Argument(String.valueOf(c)));
    }

    public void add(String string) {
        if (string.length() > 1 && string.charAt(0) == '-')
            flags.add(string.substring(1));
        else
            add(new Argument(string));
    }
}
