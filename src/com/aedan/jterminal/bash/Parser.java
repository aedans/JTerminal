package com.aedan.jterminal.bash;

import com.aedan.jterminal.command.commandhandler.CommandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for parsing .jterm files.
 */

final class Parser {

    /**
     * Pattern for finding functions in .jterm files.
     */
    private static Pattern functionPattern = Pattern.compile("([a-zA-Z]+)\\(([^()]*)\\) *\\{");

    /**
     * Parsers a .jterm file.
     *
     * @param s       The file to parse.
     * @param runtime The runtime requesting the parser.
     * @return The list of functions in the file.
     * @throws CommandHandler.CommandHandlerException If there was an error parsing the file.
     */
    public static ArrayList<Function> parse(String s, BashRuntime runtime) throws CommandHandler.CommandHandlerException {
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
     * @throws CommandHandler.CommandHandlerException If there was an error parsing the function.
     */
    private static Function parseFunction(String src, String name, String arguments, BashRuntime runtime)
            throws CommandHandler.CommandHandlerException {
        return new Function() {
            @Override
            public String getIdentifier() {
                return name;
            }

            @Override
            public Object apply(Object[] o) throws CommandHandler.CommandHandlerException {
                HashMap<String, String> sVars = runtime.getEnvironment().getGlobalVariables();
                runtime.getEnvironment().setGlobalVariables(new HashMap<>());
                String[] args = arguments.split(",");
                for (int i = 0; i < args.length; i++) {
                    runtime.getEnvironment().addGlobalVariable(args[i].trim(), o[i].toString());
                }
                for (String statement : src.split("\n")) {
                    runtime.getEnvironment().getCommandHandler().handleInput(statement);
                }
                runtime.getEnvironment().setGlobalVariables(sVars);
                return null;
            }
        };
    }
}
