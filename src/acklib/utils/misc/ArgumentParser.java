package acklib.utils.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hacke
 *         <p>
 *         Basic parser for command line arguments, parsing "-l arg -k arg2 -f file.txt -flag"
 *         into a hashmap of argument name to value
 */
public final class ArgumentParser {

    /*
     * HashMaps to hold name of argument to supplied value, to be retrieved later
     */
    private HashMap<String, String> stringArgumentMap;

    private HashMap<String, Integer> integerArgumentMap;

    private HashMap<String, Boolean> flagArgumentMap;

    public ArgumentParser() {
        this.stringArgumentMap = new HashMap<>();
        this.integerArgumentMap = new HashMap<>();
        this.flagArgumentMap = new HashMap<>();
    }

    /**
     * Parses line with format "-arg value" into a hashmap of keys to values
     *
     * @param args line to parse
     * @throws ArgumentParseException if the string is incorrectly formatted
     */
    public void parseArguments(final String args) throws ArgumentParseException {
        ArrayList<String> argumentList = new ArrayList<>();
        String regex = "(\".+\")|(-\\w+)|([\\w.:;/\\\\]+)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(args);
        while (m.find()) {
            argumentList.add(m.group());
        }
        for (int i = 0; i < argumentList.size(); i++) {
            String argument = argumentList.get(i);
            if (argument.startsWith("-")) {
                String argName = argument.replaceFirst("-", "");
                if (i + 1 >= argumentList.size()) {   /* if at end of argument list, must be a boolean flag */
                    flagArgumentMap.put(argName, true);
                } else {  /* otherwise, check for integers, strings, and other flags */
                    String nextArg = argumentList.get(i + 1);
                    if (nextArg.startsWith("-")) {
                        flagArgumentMap.put(argName, true);
                    } else if (nextArg.matches("(\\d+)")) {
                        integerArgumentMap.put(argName, Integer.parseInt(nextArg));
                        i++;
                    } else {
                        stringArgumentMap.put(argName, nextArg.replaceAll("\"", ""));
                        i++;
                    }
                }
            } else {
                throw new ArgumentParseException("argument \"" + argument + "\" is an invalid argument key");
            }
        }
    }

    /**
     * Gets an integer from the integerArgumentHashMap, null if no value
     *
     * @param argumentName the character that represents the value
     * @return the value with key provided or null if nonexistent
     */
    public Integer getInteger(final String argumentName) {
        if (argumentName == null) throw new IllegalArgumentException("argument name cannot be null");
        return integerArgumentMap.getOrDefault(argumentName, null);
    }

    /**
     * Gets a string from the stringArgumentHashMap, null if no value
     *
     * @param argumentName the character that represents the value
     * @return the value with key provided or null if nonexistent
     */
    public String getString(final String argumentName) {
        if (argumentName == null) throw new IllegalArgumentException("argument name cannot be null");
        return stringArgumentMap.getOrDefault(argumentName, null);
    }

    /**
     * Gets a bool from the flagArgumentHashMap, false if no value
     *
     * @param argumentName the character that represents the value
     * @return the value with key provided or false if nonexistent
     */
    public Boolean getFlag(final String argumentName) {
        if (argumentName == null) throw new IllegalArgumentException("argument name cannot be null");
        return flagArgumentMap.getOrDefault(argumentName, false);
    }
}

