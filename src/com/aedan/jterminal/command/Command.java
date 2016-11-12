package com.aedan.jterminal.command;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * Abstract class containing necessary functions for the Environment to use.
 */

public abstract class Command {
    /**
     * The number of properties a Command should have.
     */
    public static int numProperties = 2;

    /**
     * The identifier to identify the command.
     */
    private final String identifier;

    /**
     * A list of properties that the Command has.
     */
    public String[] properties = new String[numProperties];

    /**
     * The default Command constructor.
     *
     * @param identifier The identifier to identify the Command.
     * @param properties The list of properties to create the Command with.
     */
    protected Command(String identifier, String... properties) {
        this.identifier = identifier;
        this.properties = Arrays.copyOf(properties, numProperties);
    }

    /**
     * Parses a String.
     *
     * @param args        The CommandArgumentList to parse.
     * @param input       The Input for the JTerminal.
     * @param output      The output to print to.
     * @param environment The Environment of the Command.
     * @throws JTerminalException if the String cannot be parsed.
     */
    public abstract void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment) throws JTerminalException;

    @Override
    public String toString() {
        return identifier + ":" + JSON.toJSONString(this, true);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getProperty(int id) {
        return properties[id];
    }
}
