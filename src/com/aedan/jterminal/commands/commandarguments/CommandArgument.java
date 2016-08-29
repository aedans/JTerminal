package com.aedan.jterminal.commands.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Class for passing arguments to Commands.
 */

public class CommandArgument {

    public String value;

    public final ArgumentType argumentType;

    public CommandArgument(String value) {
        this(value, ArgumentType.getArgumentType(value));
    }

    public CommandArgument(String value, ArgumentType argumentType){
        this.value = value;
        this.argumentType = argumentType;
    }

    @Override
    public String toString() {
        return value;
    }

}