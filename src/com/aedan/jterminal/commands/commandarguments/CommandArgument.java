package com.aedan.jterminal.commands.commandarguments;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Class for passing arguments to Commands.
 */

public class CommandArgument {

    public String value;

    public CommandArgument(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}