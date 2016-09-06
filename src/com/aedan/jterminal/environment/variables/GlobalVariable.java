package com.aedan.jterminal.environment.variables;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Class for the Environment to use to handle Variables.
 */

public class GlobalVariable implements Variable {

    /**
     * The name of the Variable
     */
    private String name;

    /**
     * The value of the GlobalVariable.
     */
    private String value;

    /**
     * Default GlobalVariable constructor.
     *
     * @param name  The GlobalVariable name.
     * @param value The GlobalVariable value.
     */
    public GlobalVariable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setValue(String newValue){
        this.value = newValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

}
