package com.aedan.jterminal;

import com.aedan.jterminal.command.CommandHandler;
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
     * The input for the JTerminal.
     */
    private CommandInput input;

    /**
     * The Outputs for the JTerminal.
     */
    private CommandOutput output;

    /**
     * The Environment for the JTerminal.
     */
    private Environment environment;

    /**
     * The default JTerminal constructor.
     *
     * @param args The list of arguments for the JTerminal.
     * @throws Exception If there was an error whilst initializing the JTerminal.
     */
    public JTerminal(String... args) throws Exception {
        this(args == null ? new String[]{} : args, null, null, null);
    }

    /**
     * JTerminal constructor for custom CommandPackages, Inputs and Outputs.
     *
     * @param args        The list of arguments for the JTerminal.
     * @param input       The CommandInput for the JTerminal to use.
     * @param output      The CommandOutput for the JTerminal to use.
     * @param environment The Environment for the JTerminal to use.
     * @throws Exception If there was an error whilst initializing the JTerminal.
     */
    public JTerminal(String[] args, CommandInput input, CommandOutput output, Environment environment)
            throws Exception {
        if (args == null)
            args = new String[]{};
        if (input == null)
            input = new ScannerInput();
        if (output == null)
            output = new PrintStreamOutput(System.out);
        if (environment == null)
            environment = new Environment(args, input, output, new DefaultPackage());

        this.input = input;
        this.output = output;
        this.environment = environment;
    }

    /**
     * Main function for the JTerminal.jar.
     *
     * @param args The arguments for the JTerminal.
     * @throws Exception If there was an error with the JTerminal.
     */
    public static void main(String[] args) throws Exception {
        new JTerminal(args).run();
    }

    /**
     * Starts the JTerminal.
     */
    public void run() {
        //set CARET "echo \"\";+ [+ %USERNAME% @] %USERDOMAIN_ROAMINGPROFILE%;echo \"$\""
        environment.getEnvironmentVariables().put("CARET", "+ %DIR% \">\"");
        StringOutput caret = new StringOutput();
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                environment.getCommandHandler().handleInput(caret, environment.getEnvironmentVariables().get("CARET").toString());
                output.print(caret.getString().trim());
                caret.flush();
                handleString(input.nextLine());
            } catch (Exception e) {
                output.print("Fatal error: ");
                for (StackTraceElement s : e.getStackTrace()) {
                    output.println(s);
                }
            }
        }
    }

    /**
     * Handles a given String.
     *
     * @param s The String to handle.
     */
    public void handleString(String s) throws CommandHandler.CommandHandlerException {
        environment.getCommandHandler().handleInput(s);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public CommandInput getInput() {
        return input;
    }

    public void setInput(CommandInput input) {
        this.input = input;
    }

    public CommandOutput getOutput() {
        return output;
    }

    public void setOutput(CommandOutput output) {
        this.output = output;
    }
}
