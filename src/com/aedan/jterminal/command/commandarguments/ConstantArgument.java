package com.aedan.jterminal.command.commandarguments;

import java.util.function.Supplier;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for passing arguments to Commands.
 */

public class ConstantArgument implements Argument {
    /**
     * The value of the ConstantArgument.
     */
    private Object value;
    /**
     * The ArgumentType of the ConstantArgument.
     */
    private Class<?> argumentType;

    /**
     * Default ConstantArgument constructor.
     *
     * @param value The value of the ConstantArgument.
     */
    public ConstantArgument(Object value) {
        this(value, value == null ? null : value.getClass());
    }

    /**
     * ConstantArgument constructor for assigning custom ArgumentTypes.
     *
     * @param value        The value of the ConstantArgument.
     * @param argumentType The ArgumentType of the ConstantArgument.
     */
    public ConstantArgument(Object value, Class<?> argumentType) {
        this.value = value;
        this.argumentType = argumentType;
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
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