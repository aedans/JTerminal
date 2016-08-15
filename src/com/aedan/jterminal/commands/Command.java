package com.aedan.jterminal.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.Output;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/10/16.
 *
 * Abstract class containing necessary functions for the CommandHandler to use.
 */

public abstract class Command {

    /**
     * The Pattern to search commands for flags.
     */
    private static Pattern flagPattern = Pattern.compile(" -(\\w+)");

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
     * A Quick Description of the Command for the Help command.
     */
    private String quickDescription;

    /**
     * The default Command constructor.
     *
     * @param commandFormat: The format of the command.
     *                     -s = a String
     *                     -i = an Integer
     *                     * = optional
     * @param identifier: The identifier to identify the command.
     * @param argCount: The expected number of arguments.
     */
    public Command(String commandFormat, String identifier, int argCount, String quickDescription){
        this.commandFormatS = commandFormat;
        this.commandFormat = Pattern.compile(commandFormat
                .replaceAll(" ", " *")
                .replaceAll("-s", " *(\"[^\"]+\"|[^ ]+)")
                .replaceAll("-i", " *([0123456789]+)")
        );
        this.identifier = identifier;
        this.argCount = argCount;
        this.quickDescription = quickDescription;
    }

    /**
     * Parses a String.
     *
     * @param in: The String to parse.
     * @param directory: The directory of the CommandHandler.
     * @param output: The output to print to.
     * @throws CommandHandler.CommandHandlerException if the String cannot be parsed.
     */
    public abstract void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException;

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
     * @param in: The input to Match.
     * @return String[]: The matches of this command's Command Format.
     * @throws InvalidInputException if the Matcher does not Match the command.
     */
    protected String[] getArgValues(String in) throws InvalidInputException {
        String[] matches = new String[argCount];
        Matcher m = commandFormat.matcher(in);
        if (m.find()) {
            for (int i = 0; i < argCount; i++) {
                if (m.group(i+1) != null) {
                    matches[i] = m.group(i + 1).replaceAll("\"([^\"]+)\"", "$1");
                } else {
                    matches[i] = "";
                }
            }
            return matches;
        }
        throw new InvalidInputException();
    }

    /**
     * Returns a List of flags for the Command. Does not include the - in the String.
     *
     * @param in: The input to get the flags for.
     * @return ArrayList<String>: The List of flags (notated -r, -exec, etc.)
     */
    protected ArrayList<String> getFlags(String in) {
        ArrayList<String> flags = new ArrayList<>();
        Matcher m = flagPattern.matcher(in);
        while (m.find()){
            flags.add(m.group(1));
        }
        return flags;
    }

    /**
     * Returns if a given flag is present in the String.
     *
     * @param in: The String to look for the flag in.
     * @param flag: The flag to look for.
     * @return boolean: If the flag is present in the String.
     */
    protected boolean isFlagPresent(String in, String flag){
        Matcher m = flagPattern.matcher(in);
        while (m.find()){
            if (m.group(1).matches(flag))
                return true;
        }
        return false;
    }

    public String getQuickDescription(){
        return quickDescription;
    }

    @Override
    public String toString() {
        return "Command \"" + identifier + "\"";
    }

    /**
     * Exception thrown when the input does not match the Command Format.
     */
    private class InvalidInputException extends CommandHandler.CommandHandlerException {

        /**
         * The default InvalidInputException constructor.
         */
        public InvalidInputException() {
            super("Input does not match format \"" + commandFormatS + "\"");
        }

    }

    public String getCommandFormat() {
        return commandFormatS;
    }

    public String getIdentifier() {
        return identifier;
    }

}
