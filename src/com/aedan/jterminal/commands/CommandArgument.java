package com.aedan.jterminal.commands;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Class for passing arguments to Commands.
 */

public class CommandArgument {

    /**
     * The value of the CommandArgument.
     */
    public String value;

    public CommandArgument(String value){
        this.value = value;
    }

    public static CommandArgument[] fromStringArray(String[] strings){
        CommandArgument[] commandArguments = new CommandArgument[strings.length];
        for (int i = 0; i < commandArguments.length; i++) {
            commandArguments[i] = new CommandArgument(strings[i]);
        }
        return commandArguments;
    }

    @Override
    public String toString() {
        return value;
    }

}
