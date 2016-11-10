package com.aedan.jterminal.command.commandarguments;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.input.parser.TokenList;

import java.util.ArrayList;
import java.util.LinkedList;

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
    public CommandArgumentList(TokenList tokens) {
        args.add(new CommandArgument(tokens.get(0).toString(), ArgumentType.COMMAND_IDENTIFIER));
        for (int i = 1; i < tokens.size(); i++) {
            if (tokens.get(i).toString().length() > 1 && tokens.get(i).toString().charAt(0) == '-'
                    && Character.isAlphabetic(tokens.get(i).toString().charAt(1))) {
                flags.add(tokens.get(i).toString());
            } else
                args.add(new CommandArgument(tokens.get(i).toString()));
        }
    }

    /**
     * Checks to see if the CommandArgumentList matches the given format, and errors with the correct message if it doesn't.
     *
     * @param argumentTypes The format for the CommandArgumentList.
     * @throws CommandHandler.CommandHandlerException If the format does not match.
     */
    public void checkMatches(Command command, ArgumentType... argumentTypes) throws CommandHandler.CommandHandlerException {
        MatchResult matchResult = matches(argumentTypes);
        if (matchResult == MatchResult.CORRECT_ARGS)
            return;
        throw new CommandHandler.CommandHandlerException(matchResult.getMessage(), command);
    }

    /**
     * Checks to see if the CommandArgumentList matches the given format.
     *
     * @param argumentTypes The format for the CommandArgumentList.
     * @return The MatchResult.
     */
    public MatchResult matches(ArgumentType... argumentTypes) {
        if (args.size() > argumentTypes.length + 1)
            return MatchResult.MORE_ARGS;

        if (args.size() < argumentTypes.length + 1)
            return MatchResult.LESS_ARGS;

        for (int i = 1; i < args.size(); i++) {
            if (argumentTypes[i - 1] != ArgumentType.STRING && !args.get(i).getArgumentType().isSubset(argumentTypes[i - 1]))
                return MatchResult.INCORRECT_ARGS;
        }

        return MatchResult.CORRECT_ARGS;
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
