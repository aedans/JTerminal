package com.aedan.jterminal.jterm;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.StringOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for parsing .jterminal files.
 */

final class Parser {
    /**
     * Pattern for finding functions in .jterminal files.
     */
    private static Pattern functionPattern = Pattern.compile("([ !a-zA-Z]+)\\(([^()]*)\\) *\\{");

    /**
     * Parsers a .jterminal file.
     *
     * @param s       The file to parse.
     * @param runtime The runtime requesting the parser.
     * @return The list of functions in the file.
     * @throws JTerminalException If there was an error parsing the file.
     */
    public static ArrayList<Function> parse(String s, JTermRuntime runtime) throws JTerminalException {
        ArrayList<Function> functions = new ArrayList<>();
        Matcher m = functionPattern.matcher(s);
        while (m.find()) {
            int depth = 0, i = m.start();
            for (; i < s.length(); i++) {
                if (s.charAt(i) == '{')
                    depth++;
                if (s.charAt(i) == '}') {
                    depth--;
                    if (depth == 0) break;
                }
            }
            String fn = s.substring(m.end(), i);
            functions.add(parseFunction(fn, m.group(1), m.group(2), runtime));
        }
        return functions;
    }

    /**
     * Parses a function.
     *
     * @param src       The source to parse the function from.
     * @param name      The name of the function.
     * @param arguments The arguments of the function.
     * @param runtime   The runtime requesting the parse.
     * @return The parsed function.
     * @throws JTerminalException If there was an error parsing the function.
     */
    private static Function parseFunction(String src, String name, String arguments, JTermRuntime runtime)
            throws JTerminalException {
        CommandOutput commandOutput = runtime.getEnvironment().getOutput();
        if (name.startsWith("!")) {
            name = name.substring(1);
            commandOutput = new StringOutput();
        }
        String finalName = name;
        CommandOutput finalCommandOutput = commandOutput;
        String[] args = arguments.split(",");
        if (arguments.isEmpty()) {
            args = new String[0];
        }
        String[] finalArgs = args;
        String[] statements = src.split("\n");
        return new Function() {
            @Override
            public String getIdentifier() {
                return finalName;
            }

            @Override
            public Object apply(Object[] o) throws JTerminalException {
                HashMap<String, Object> sVars = runtime.getEnvironment().getGlobalVariables();
                runtime.getEnvironment().setGlobalVariables(new HashMap<>());
                // Adds function arguments to the stack
                for (int i = 0; i < finalArgs.length; i++) {
                    try {
                        runtime.getEnvironment().addGlobalVariable(finalArgs[i].trim(), o[i].toString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new JTerminalException("Not enough arguments given for function " + finalName, this);
                    }
                }
                // Execute statements
                for (String statement : statements) {
                    runtime.getEnvironment().getCommandHandler().handleInput(
                            statement, runtime.getEnvironment().getInput(), finalCommandOutput
                    );
                }
                // Ends scope
                runtime.getEnvironment().setGlobalVariables(sVars);
                if (finalCommandOutput instanceof StringOutput) {
                    try {
                        return ((StringOutput) finalCommandOutput).getString();
                    } finally {
                        ((StringOutput) finalCommandOutput).flush();
                    }
                } else
                    return null;
            }

            @Override
            public String toString() {
                return finalName;
            }
        };
    }
}
