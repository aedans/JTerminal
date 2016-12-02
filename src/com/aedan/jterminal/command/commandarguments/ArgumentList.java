package com.aedan.jterminal.command.commandarguments;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.utils.ClassUtils;
import com.sun.org.apache.xpath.internal.operations.Number;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing Arguments to Commands.
 */

public class ArgumentList extends ArrayList<Argument> {
    /**
     * The List of flags in the ArgumentList.
     */
    public final LinkedList<String> flags = new LinkedList<>();

    /**
     * Checks to see if the ArgumentList matches the given format, and errors with the correct message if it doesn't.
     *
     * @param args The format for the ArgumentList.
     * @throws JTerminalException If the format does not match.
     */
    public void checkMatches(Command command, Class<?>... args) throws JTerminalException {
        MatchResult matchResult = matches(args);
        if (matchResult == MatchResult.CORRECT_ARGS)
            return;
        throw new JTerminalException(matchResult.getMessage(), command);
    }

    /**
     * Checks to see if the ArgumentList matches the given format.
     *
     * @param args The format for the ArgumentList.
     * @return The MatchResult.
     */
    public MatchResult matches(Class<?>... args) {
        if (this.size() > args.length + 1)
            return MatchResult.MORE_ARGS;

        if (this.size() < args.length + 1)
            return MatchResult.LESS_ARGS;

        for (int i = 1; i < this.size(); i++) {
            if (args[i - 1] == String.class) {
                continue;
            }
            if (args[i - 1] == Number.class && ClassUtils.isNumber(args[i - 1])) {
                continue;
            }
            if (!args[i - 1].equals(String.class) && !(args[i - 1].isAssignableFrom(this.get(i).getArgumentType()))) {
                return MatchResult.INCORRECT_ARGS;
            }
        }

        return MatchResult.CORRECT_ARGS;
    }

    public void add(String string) {
        if (string.length() > 1 && string.charAt(0) == '-')
            flags.add(string.substring(1));
        else {
            this.add(new ConstantArgument(string));
        }
    }

    public Argument getLast() {
        if (this.size() != 0) {
            return this.get(this.size() - 1);
        } else {
            return null;
        }
    }

    public void setLast(Argument argument) {
        if (this.size() == 0)
            this.add(argument);
        else
            this.set(this.size() - 1, argument);
    }

    public Argument removeLast() {
        if (this.size() != 0)
            return this.remove(this.size() - 1);
        else return null;
    }
}
