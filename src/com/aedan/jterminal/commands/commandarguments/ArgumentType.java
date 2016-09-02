package com.aedan.jterminal.commands.commandarguments;

import com.aedan.jterminal.utils.Patterns;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Enum containing all ArgumentTypes supported by the CommandArgumentList.
 */

public enum ArgumentType {

    STRING, BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, COMMANDIDENTIFIER;

    /**
     * Returns the ArgumentType of a given String.
     *
     * @param value the String to get the ArgumentType of.
     * @return The ArgumentType of the String.
     */
    public static ArgumentType getArgumentType(String value) {
        if (value.trim().matches(Patterns.bytePattern.pattern())){
            return BYTE;
        }
        if (value.trim().matches(Patterns.shortPattern.pattern())){
            return SHORT;
        }
        if (value.trim().matches(Patterns.integerPattern.pattern())) {
            return INTEGER;
        }
        if (value.trim().matches(Patterns.longPattern.pattern())){
            return LONG;
        }
        if (value.trim().matches(Patterns.floatPattern.pattern())){
            return FLOAT;
        }
        if (value.trim().matches(Patterns.doublePattern.pattern())) {
            return DOUBLE;
        }
        return STRING;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
