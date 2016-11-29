package jterm;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import jterm.jtermpackage.JTermPackage;
import jterm.parsers.JTermParser;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.parser.Parser;
import com.alibaba.fastjson.JSON;

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

    public Parser parser = new JTermParser(functions);

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
        this.environment = new Environment(null, input, output, null, null, new JTermPackage(this));
        this.parser.parse(environment, src);
    }

    /**
     * Runs the JTermRuntime.
     *
     * @param args An array of arguments for the JTermRuntime main function.
     * @throws JTerminalException If there was an error running the JTermRuntime.
     */
    public Object run(Object[] args) throws JTerminalException {
        return this.getFunction("main").apply(args);
    }

    public void add(Function function) {
        this.functions.put(function.getIdentifier(), function);
    }

    public Function getFunction(String name) throws JTerminalException {
        Function f = functions.get(name);
        if (f == null)
            throw new JTerminalException("No function found with name \"" + name + "\"", this);
        else
            return functions.get(name);
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return "\"jtermRuntime\":" + JSON.toJSONString(this, true);
    }
}
