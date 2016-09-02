package com.aedan.jterminal.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * Abstract class containing necessary functions for the CommandHandler to use.
 */

public abstract class Command {

    /**
     * The identifier to identify the command.
     */
    private final String identifier;

    /**
     * A quick description of the Command for the Help command.
     */
    private final String quickDescription;

    /**
     * The default Command constructor.
     *
     * @param identifier       The identifier to identify the Command.
     * @param quickDescription A quick description of the Command.
     */
    protected Command(String identifier, String quickDescription) {
        this.identifier = identifier;
        this.quickDescription = quickDescription;
    }

    /**
     * Parses a String.
     *
     * @param input     The Input for the CommandHandler.
     * @param args      The CommandArgumentList to parse.
     * @param directory The directory of the CommandHandler.
     * @param output    The output to print to.
     * @throws CommandHandler.CommandHandlerException if the String cannot be parsed.
     */
    public abstract void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException;

    /**
     * Checks to see if a String[] contains a flag.
     *
     * @param args The String[] to check. Args[0] is always the identifier.
     * @param flag The flag to check for.
     * @return If the String[] contains the flag.
     */
    protected boolean containsFlag(String[] args, String flag) {
        for (String s : args) {
            if (s.contains("-" + flag))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Command \"" + identifier + "\"";
    }

    public String getQuickDescription() {
        return quickDescription;
    }

    public String getIdentifier() {
        return identifier;
    }

}
