package com.aedan.jterminal;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.ScannerInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.PrintStreamOutput;
import com.aedan.jterminal.output.StringOutput;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;

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
        //set CARET "echo \"\";+ [+ %USERNAME% @] [+ %USERDOMAIN_ROAMINGPROFILE% \" ~\"];echo \\$"
        StringOutput caret = new StringOutput();
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                environment.getCommandHandler().handleInput(
                        environment.getInput(),
                        caret,
                        environment.getEnvironmentVariables().get("CARET").toString()
                );
                environment.getOutput().print(caret.getString().trim());
                caret.flush();
                handleString(environment.getInput().nextLine());
            } catch (Throwable e) {
                environment.getOutput().print("Fatal error: ");
                for (StackTraceElement s : e.getStackTrace()) {
                    environment.getOutput().println(s);
                }
            }
        }
    }

    /**
     * Handles a given String.
     *
     * @param s The String to handle.
     */
    public void handleString(String s) throws JTerminalException {
        environment.getCommandHandler().handleInput(environment.getInput(), environment.getOutput(), s);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
