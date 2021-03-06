package com.aedan.jterminal.jterm;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.jterm.jtermpackage.JTermPackage;
import com.aedan.jterminal.jterm.parsers.JTermParser;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.parser.StringIterator;
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

    public JTermParser parser;

    /**
     * The Environment for the JTermRuntime.
     */
    private Environment environment;

    /**
     * Default JTermRuntime constructor.
     *
     * @param environment The Environment to inherit from.
     * @param src         The source to compile the JTermRuntime from.
     * @param input       The CommandInput for the JTermRuntime.
     * @param output      The CommandOutput for the JTermRuntime.
     * @throws Exception If there was an error compiling the JTermRuntime.
     */
    public JTermRuntime(Environment environment, String src, CommandInput input, CommandOutput output) throws Exception {
        this.environment = new Environment(input, output, null, new JTermPackage(this));
        environment.getPackages().forEach(aPackage -> JTermRuntime.this.environment.addPackage(aPackage));
        this.parser = new JTermParser(this.environment);
        this.environment.setEnvironmentVariable("FUNCTIONS", functions);
        this.parser.parse(new StringIterator(src), functions);
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
