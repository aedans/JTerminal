package com.aedan.jterminal;

import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.default_package.DefaultPackage;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.SystemInput;
import com.aedan.jterminal.output.Output;
import com.sun.istack.internal.NotNull;

import java.io.PrintStream;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The main JTerminal class.
 */

public class JTerminal implements Runnable {

    /**
     * The input for the JTerminal.
     */
    private CommandInput input = new SystemInput();

    /**
     * The CommandHandler for the JTerminal.
     */
    private CommandHandler commandHandler;

    /**
     * The Outputs for the JTerminal.
     */
    private Output output = new Output(System.out);

    /**
     * The default JTerminal constructor.
     */
    public JTerminal() {
        this(new DefaultPackage());
    }

    /**
     * JTerminal constructor for custom CommandPackages.
     *
     * @param commandPackages: The CommandPackages to use.
     */
    public JTerminal(CommandPackage... commandPackages) {
        commandHandler = new CommandHandler(commandPackages);
    }

    /**
     * Starts the JTerminal.
     */
    public void run() {
        String in;
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                output.print(commandHandler.getDirectory() + "> ");
                in = input.nextLine();
                commandHandler.handleString(in.replaceAll(" {2,}", " "), output);
            } catch (CommandHandler.CommandHandlerException e) {
                output.println("Could not handle command (" + e.getMessage() + ")");
            } catch (Exception e){
                output.print("Fatal error: ");
                output.getOutputs().forEach(e::printStackTrace);
            }
        }
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    @NotNull
    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public CommandInput getInput() {
        return input;
    }

    @NotNull
    public void setInput(CommandInput input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    @NotNull
    public void setOutput(Output output) {
        this.output = output;
    }

}
