package com.aedan.jterminal.commands;

import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

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
     */
    protected Command(String identifier) {
        this.identifier = identifier;
    }

    protected Command(String identifier, String...properties) {
        this(identifier);
        this.properties = Arrays.copyOf(properties, numProperties);
    }

    /**
     * Parses a String.
     *
     * @param input       The Input for the JTerminal.
     * @param args        The CommandArgumentList to parse.
     * @param environment The Environment of the Command.
     * @param output      The output to print to.
     * @throws CommandHandler.CommandHandlerException if the String cannot be parsed.
     */
    public abstract void parse(CommandInput input, CommandArgumentList args, Environment environment,
                               CommandOutput output) throws CommandHandler.CommandHandlerException;

    @Override
    public String toString() {
        return "Command \"" + identifier + "\"";
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getProperty(int id) throws InvalidPropertyException {
        if (properties.length < id)
            throw new InvalidPropertyException(
                    "Command \"" + identifier + "\" does not support more than " + properties.length + " properties");
        if (properties[id] == null)
            throw new InvalidPropertyException("Command \"" + identifier + "\" has not assigned property " + id);
        return properties[id];
    }

    /**
     * Exception thrown if the JTerminal tries to access an invalid property.
     */
    public static class InvalidPropertyException extends Exception {

        /**
         * The default InvalidPropertyException constructor.
         *
         * @param message The error message to display.
         */
        public InvalidPropertyException(String message) {
            super(message);
        }

    }

}
