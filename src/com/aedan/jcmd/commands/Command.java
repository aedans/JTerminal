package com.aedan.jcmd.commands;

import com.aedan.jcmd.Output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/10/16.
 *
 * Abstract class containing necessary functions for the CommandHandler to use.
 */

public abstract class Command {

    /**
     * The Regular Expression to parse the Command.
     */
    private Pattern commandFormat;

    /**
     * The format of the Command.
     */
    private String commandFormatS;

    /**
     * The identifier to identify the command.
     */
    private String identifier;

    /**
     * The expected number of arguments.
     */
    private int argCount;

    /**
     * The default Command constructor.
     *
     * @param commandFormat: The format of the command.
     *                     -s = a String
     *                     -i = an Integer
     * @param identifier: The identifier to identify the command.
     * @param argCount: The expected number of arguments.
     */
    public Command(String commandFormat, String identifier, int argCount){
        this.commandFormatS = commandFormat;
        this.commandFormat = Pattern.compile(commandFormat
                .replaceAll("-s", "([^ ]+)")
                .replaceAll("-i", "([0123456789]+)")
        );
        this.identifier = identifier;
        this.argCount = argCount;
    }

    /**
     * Parses a String.
     *
     * @param in: The String to parse.
     * @param directory: The directory of the CommandHandler.
     * @param output: The output to print to.
     * @throws CommandHandler.CommandHandlerException if the String cannot be parsed.
     */
    public abstract void parse(String in, String directory, Output output) throws CommandHandler.CommandHandlerException;

    /**
     * Determines if a String is a valid command using the Command Format.
     *
     * @param s: The String to validate.
     * @return boolean: True if the String is a valid Command.
     */
    protected boolean isValidCommand(String s){
        return s.matches(commandFormat.pattern());
    }

    /**
     * Returns the matches of this command's Command Format.
     *
     * @param s: The String to Match.
     * @return String[]: The matches of this command's Command Format.
     * @throws InvalidInputException if the Matcher does not Match the command.
     */
    protected String[] getArgValues(String s) throws InvalidInputException {
        String[] matches = new String[argCount];
        Matcher m = commandFormat.matcher(s);
        if (m.find()) {
            for (int i = 0; i < argCount; i++) {
                matches[i] = m.group(i+1);
            }
            return matches;
        }
        throw new InvalidInputException();
    }

    @Override
    public String toString() {
        return "Command \"" + identifier + "\"";
    }

    private class InvalidInputException extends CommandHandler.CommandHandlerException {

        /**
         * The default InvalidInputException constructor.
         */
        public InvalidInputException() {
            super("Input does not match format \"" + commandFormatS + "\"");
        }

    }

    public Pattern getCommandFormat() {
        return commandFormat;
    }

    public String getIdentifier() {
        return identifier;
    }

}
