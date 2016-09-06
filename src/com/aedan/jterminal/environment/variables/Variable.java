package com.aedan.jterminal.environment.variables;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Class for the CommandHandler to use to handle Variables.
 */

public class Variable {

    /**
     * The name of the variable
     */
    public final String name;

    /**
     * The value of the Variable.
     */
    public String value;

    /**
     * Default Variable constructor.
     *
     * @param name  The Variable name.
     * @param value The Variable value.
     */
    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
