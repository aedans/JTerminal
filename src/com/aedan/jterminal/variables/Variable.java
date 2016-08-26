package com.aedan.jterminal.variables;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Class for the CommandHandler to use to find Variables.
 */

public class Variable {

    /**
     * The name and value of the Variable.
     */
    private String name, value;

    /**
     * Default Variable constructor.
     *
     * @param name:  The Variable name.
     * @param value: The Variable value.
     */
    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
