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
    private Class argumentType;

    /**
     * Default Argument constructor.
     *
     * @param value The value of the Argument.
     */
    public Argument(Object value) {
        this(value, null);
    }

    /**
     * Argument constructor for assigning custom ArgumentTypes.
     *
     * @param value        The value of the Argument.
     * @param argumentType The ArgumentType of the Argument.
     */
    public Argument(Object value, Class argumentType) {
        this.value = value;
        this.argumentType = argumentType;
    }

    private static Class getArgumentType(String value) {
        for (int i = value.charAt(0) != '-' ? 0 : 1; i < value.length(); i++) {
            if ((value.charAt(i) < '0' || value.charAt(i) > '9') && value.charAt(i) != '.' && value.charAt(i) != 'E') {
                return String.class;
            }
        }
        return Number.class;
    }

    public Class getArgumentType() {
        if (argumentType == null)
            argumentType = getArgumentType(value.toString());
        return argumentType;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}