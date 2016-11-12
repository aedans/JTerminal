package com.aedan.jterminal.command.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class CommandArgument {
    /**
     * The value of the CommandArgument.
     */
    public String value;
    /**
     * The ArgumentType of the CommandArgument.
     */
    private ArgumentType argumentType;

    /**
     * Default CommandArgument constructor.
     *
     * @param value The value of the CommandArgument.
     */
    CommandArgument(String value) {
        this(value, null);
    }

    /**
     * CommandArgument constructor for assigning custom ArgumentTypes.
     *
     * @param value        The value of the CommandArgument.
     * @param argumentType The ArgumentType of the CommandArgument.
     */
    CommandArgument(String value, ArgumentType argumentType) {
        this.value = value;
        this.argumentType = argumentType;
    }

    public ArgumentType getArgumentType() {
        if (argumentType == null)
            argumentType = ArgumentType.getArgumentType(value);
        return argumentType;
    }

    @Override
    public String toString() {
        return value;
    }
}