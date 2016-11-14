package com.aedan.jterminal.command.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class Argument {
    /**
     * The value of the Argument.
     */
    public String value;
    /**
     * The ArgumentType of the Argument.
     */
    private ArgumentType argumentType;

    /**
     * Default Argument constructor.
     *
     * @param value The value of the Argument.
     */
    public Argument(String value) {
        this(value, null);
    }

    /**
     * Argument constructor for assigning custom ArgumentTypes.
     *
     * @param value        The value of the Argument.
     * @param argumentType The ArgumentType of the Argument.
     */
    Argument(String value, ArgumentType argumentType) {
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