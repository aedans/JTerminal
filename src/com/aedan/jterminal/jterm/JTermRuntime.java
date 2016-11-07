package com.aedan.jterminal.jterm;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.jterm.bashcommandpackage.JTermPackage;
import com.aedan.jterminal.output.CommandOutput;

import java.util.HashMap;

/**
 * Created by Aedan Smith.
 * <p>
 * The runtime for a .jterm file.
 */

public class JTermRuntime {

    /**
     * The list of functions to be executed in the JTermRuntime.
     */
    private HashMap<String, Function> functions = new HashMap<>();

    /**
     * The Environment for the JTermRuntime.
     */
    private Environment environment;

    /**
     * Default JTermRuntime constructor.
     *
     * @param src    The source to compile the JTermRuntime from.
     * @param input  The CommandInput for the JTermRuntime.
     * @param output The CommandOutput for the JTermRuntime.
     * @throws Exception If there was an error compiling the JTermRuntime.
     */
    public JTermRuntime(String src, CommandInput input, CommandOutput output) throws Exception {
        this.environment = new Environment(new String[]{""}, input, output, new JTermPackage(this));
        for (Function function : Parser.parse(src, this)) {
            functions.put(function.getIdentifier(), function);
        }
    }

    /**
     * Runs the JTermRuntime.
     *
     * @param args An array of arguments for the JTermRuntime main function.
     * @throws CommandHandler.CommandHandlerException If there was an error running the JTermRuntime.
     */
    public void run(String... args) throws CommandHandler.CommandHandlerException {
        functions.get("main").apply(args);
    }

    public void add(Function function) {
        this.functions.put(function.getIdentifier(), function);
    }

    public HashMap<String, Function> getFunctions() {
        return functions;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
