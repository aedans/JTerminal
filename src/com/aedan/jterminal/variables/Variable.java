package com.aedan.jterminal.variables;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Class for the CommandHandler to use to find Variables.
 */

public class Variable {

    /**
     * The value of the Variable.
     */
    public String value;

    /**
     * The name of the variable
     */
    public final String name;

    /**
     * Default Variable constructor.
     *
     * @param name The Variable name.
     * @param value The Variable value.
     */
    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
