package com.aedan.jterminal;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;
import com.alibaba.fastjson.JSON;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The main JTerminal class.
 */

public class JTerminal implements Runnable {
    /**
     * The Environment for the JTerminal.
     */
    private Environment environment;

    /**
     * Default JTerminal constructor.
     *
     * @param environment The Environment for the JTerminal to use.
     * @throws Exception If there was an error whilst initializing the JTerminal.
     */
    public JTerminal(Environment environment)
            throws Exception {
        if (environment == null)
            environment = new Environment(null, null, null, null, null, new DefaultPackage());

        this.environment = environment;
    }

    /**
     * Main function for the JTerminal.jar.
     *
     * @param args The arguments for the JTerminal.
     * @throws Exception If there was an error with the JTerminal.
     */
    public static void main(String[] args) throws Exception {
        new JTerminal(null).run();
    }

    /**
     * Starts the JTerminal.
     */
    public void run() {
        this.environment.getEnvironmentVariables().put("RUN", true);
        //noinspection InfiniteLoopStatement
        while ((Boolean) this.environment.getEnvironmentVariables().get("RUN")) {
            try {
                this.printCaret();
                this.handleInput();
            } catch (Throwable t) {
                this.onError(t);
            }
        }
    }

    /**
     * Prints the JTerminal caret.
     *
     * @throws JTerminalException If there was an error printing the caret.
     */
    protected void printCaret() throws JTerminalException {
        environment.getOutput().print(environment.getCommandHandler().handleInput(
                environment.getEnvironmentVariables().get("CARET").toString(),
                environment.getInput(), environment.getOutput()
        ));
    }

    /**
     * Requests and handles a single line of input.
     *
     * @throws JTerminalException If there was an error handling the input.
     */
    protected void handleInput() throws JTerminalException {
        handleString(environment.getInput().nextLine());
    }

    /**
     * Hook called on a caught error.
     *
     * @param throwable The throwable that was caught.
     */
    protected void onError(Throwable throwable) {
        environment.getOutput().println("Fatal error: " + throwable);
        for (StackTraceElement s : throwable.getStackTrace()) {
            environment.getOutput().println(s);
        }
    }

    /**
     * Handles a given String.
     *
     * @param s The String to execute.
     */
    public void handleString(String s) throws JTerminalException {
        Object o = environment.getCommandHandler().handleInput(
                s,
                environment.getInput(),
                environment.getOutput()
        );
        if (o != null)
            environment.getOutput().println(o);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "JTerminal:" + JSON.toJSONString(this, true);
    }
}
