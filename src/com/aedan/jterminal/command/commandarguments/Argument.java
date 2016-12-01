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
    public Object value;
    /**
     * The ArgumentType of the Argument.
     */
    private Class<?> argumentType;

    /**
     * Default Argument constructor.
     *
     * @param value The value of the Argument.
     */
    public Argument(Object value) {
        this(value, value == null ? null : value.getClass());
    }

    /**
     * Argument constructor for assigning custom ArgumentTypes.
     *
     * @param value        The value of the Argument.
     * @param argumentType The ArgumentType of the Argument.
     */
    public Argument(Object value, Class<?> argumentType) {
        this.value = value;
        this.argumentType = argumentType;
    }

    public Class<?> getArgumentType() {
        return argumentType;
    }

    @Override
    public String toString() {
        if (value == null)
            return null;
        else
            return value.toString();
    }
}