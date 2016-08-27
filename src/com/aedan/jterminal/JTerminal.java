package com.aedan.jterminal;

import acklib.utils.misc.ArgumentParseException;
import acklib.utils.misc.ArgumentParser;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.default_package.DefaultPackage;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.SystemInput;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.utils.FileUtils;
import com.sun.istack.internal.NotNull;

import java.io.File;

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
    private Output output;

    /**
     * The CommandHandler for the JTerminal.
     */
    private CommandHandler commandHandler;

    /**
     * The default JTerminal constructor.
     *
     * @param args: The list of arguments for the JTerminal.
     */
    public JTerminal(String args) {
        this(
                args == null ? "" : args,
                new SystemInput(),
                new Output(System.out),
                new DefaultPackage()
        );
    }

    /**
     * JTerminal constructor for custom CommandPackages.
     *
     * @param args: The list of arguments for the JTerminal.
     * @param commandPackages The CommandPackages to use.
     */
    public JTerminal(String args, CommandInput input, Output output, CommandPackage... commandPackages) {
        this.input = input;
        this.output = output;
        commandHandler = new CommandHandler(commandPackages);
        try {
            ArgumentParser parser = new ArgumentParser();
            parser.parseArguments(args);

            try {
                if (parser.getString("startup") != null) {
                    for (String s : FileUtils.readFile(new File(parser.getString("startup"))).split("\n")) {
                        commandHandler.handleInput(input, s, output.clone());
                    }
                }
            } catch (FileUtils.FileIOException e) {
                output.println("(Startup error) " + e.getMessage());
            } catch (CommandHandler.CommandHandlerException e) {
                output.println("(Startup error) Could not handle command (" + e.getMessage() + ")");
            }

        } catch (ArgumentParseException e){
            output.println("Could not handle args \"" + args + "\": " + e.getMessage());
        }
    }

    /**
     * Starts the JTerminal.
     */
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                output.print(commandHandler.getDirectory() + "> ");
                commandHandler.handleInput(input, output.clone());
            } catch (CommandHandler.CommandHandlerException e) {
                output.println("Could not handle command (" + e.getMessage() + ")");
            } catch (Exception e) {
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
