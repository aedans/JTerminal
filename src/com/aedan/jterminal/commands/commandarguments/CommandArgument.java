package com.aedan.jterminal.commands.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class CommandArgument {

    /**
     * The ArgumentType of the CommandArgument.
     */
    public final ArgumentType argumentType;
    /**
     * The value of the CommandArgument.
     */
    public String value;

    /**
     * Default CommandArgument constructor.
     *
     * @param value The value of the CommandArgument.
     */
    public CommandArgument(String value) {
        this(value, ArgumentType.getArgumentType(value));
    }

    /**
     * CommandArgument constructor for assigning custom ArgumentTypes.
     *
     * @param value        The value of the CommandArgument.
     * @param argumentType The ArgumentType of the CommandArgument.
     */
    public CommandArgument(String value, ArgumentType argumentType) {
        this.value = value;
        this.argumentType = argumentType;
    }

    @Override
    public String toString() {
        return value;
    }

}