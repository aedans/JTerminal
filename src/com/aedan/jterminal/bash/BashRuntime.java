package com.aedan.jterminal.bash;

import com.aedan.jterminal.bash.bashcommandpackage.BashPackage;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.HashMap;

/**
 * Created by Aedan Smith.
 * <p>
 * The runtime for a .jterm file.
 */

public class BashRuntime {

    /**
     * The list of functions to be executed in the BashRuntime.
     */
    private HashMap<String, Function> functions = new HashMap<>();

    /**
     * The Environment for the BashRuntime.
     */
    private Environment environment;

    /**
     * Default BashRuntime constructor.
     *
     * @param src    The source to compile the BashRuntime from.
     * @param input  The CommandInput for the BashRuntime.
     * @param output The CommandOutput for the BashRuntime.
     * @throws Exception If there was an error compiling the BashRuntime.
     */
    public BashRuntime(String src, CommandInput input, CommandOutput output) throws Exception {
        this.environment = new Environment(new String[]{""}, input, output, new BashPackage(this));
        for (Function function : Parser.parse(src, this)) {
            functions.put(function.getIdentifier(), function);
        }
    }

    /**
     * Runs the BashRuntime.
     *
     * @param args An array of arguments for the BashRuntime main function.
     * @throws CommandHandler.CommandHandlerException If there was an error running the BashRuntime.
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
