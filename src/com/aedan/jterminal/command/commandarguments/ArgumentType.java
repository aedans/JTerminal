package com.aedan.jterminal.command.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Enum containing all ArgumentTypes supported by the CommandArgumentList.
 */

public enum ArgumentType {

    BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, STRING, COMMAND_IDENTIFIER;

    /**
     * Returns the ArgumentType of a given String.
     *
     * @param value the String to get the ArgumentType of.
     * @return The ArgumentType of the String.
     */
    public static ArgumentType getArgumentType(String value) {
        for (int i = 0; i < value.length(); i++) {
            if ((value.charAt(i) < '0' || value.charAt(i) > '9') && value.charAt(i) != '.') {
                return STRING;
            }
        }
        if (value.length() <= 3)
            return BYTE;
        if (value.length() <= 5)
            return SHORT;
        if (value.length() <= 10)
            return INTEGER;
        if (value.length() <= 19)
            return LONG;
        if (value.length() <= 40)
            return FLOAT;
        if (value.length() <= 310)
            return DOUBLE;
        return STRING;
    }

    /**
     * Returns true if an ArgumentType is a subset of another ArgumentType.
     *
     * @param argumentType The ArgumentType to test.
     * @return True if an ArgumentType is a subset of another ArgumentType
     */
    @SuppressWarnings("Duplicates")
    public boolean isSubset(ArgumentType argumentType) {
        switch (argumentType) {
            case DOUBLE:
                if (this != STRING) return true;
            case FLOAT:
                if (this == FLOAT) return true;
            case LONG:
                if (this == LONG) return true;
            case INTEGER:
                if (this == INTEGER) return true;
            case SHORT:
                if (this == SHORT) return true;
            case BYTE:
                if (this == BYTE) return true;
                break;
            case STRING:
                return this != COMMAND_IDENTIFIER;
            case COMMAND_IDENTIFIER:
                return this == COMMAND_IDENTIFIER;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
