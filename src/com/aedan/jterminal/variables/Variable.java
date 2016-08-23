package com.aedan.jterminal.variables;

/**
 * Created by Aedan Smith on 8/22/2016.
 *
 * Class for the CommandHandler to use to find Variables.
 */

public class Variable {

    private String name, value;

    public Variable(String name, String value){
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
