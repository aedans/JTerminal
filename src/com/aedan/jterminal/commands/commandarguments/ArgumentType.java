package com.aedan.jterminal.commands.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Enum containing all ArgumentTypes supported by the CommandArgumentList.
 */

public enum ArgumentType {

    STRING, INTEGER, DOUBLE, COMMANDIDENTIFIER;

    /**
     * Returns the ArgumentType of a given String.
     *
     * @param value the String to get the ArgumentType of.
     * @return The ArgumentType of the String.
     */
    public static ArgumentType getArgumentType(String value) {
        if (value.matches(" *[-+0123456789]+ *")) {
            return INTEGER;
        } else if (value.matches(" *[-+0123456789.] *")) {
            return DOUBLE;
        } else {
            return STRING;
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
