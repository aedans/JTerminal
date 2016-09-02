package com.aedan.jterminal;

import acklib.utils.misc.ArgumentParseException;
import acklib.utils.misc.ArgumentParser;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.DefaultPackage;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.SystemInput;
import com.aedan.jterminal.output.CommandOutput;
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
    private CommandOutput output;

    /**
     * The CommandHandler for the JTerminal.
     */
    private CommandHandler commandHandler;

    /**
     * The default JTerminal constructor.
     *
     * @param args The list of arguments for the JTerminal.
     */
    public JTerminal(String args) {
        this(
                args == null ? "" : args,
                new DefaultPackage()
        );
    }

    /**
     * JTerminal constructor for custom CommandPackages.
     *
     * @param args            The list of arguments for the JTerminal.
     * @param commandPackages The CommandPackages to use.
     */
    public JTerminal(String args, CommandPackage... commandPackages) {
        this(
                args == null ? "" : args,
                new SystemInput(),
                new CommandOutput(System.out),
                commandPackages
        );
    }

    /**
     * JTerminal constructor for custom CommandPackages, Inputs and Outputs.
     *
     * @param args            The list of arguments for the JTerminal.
     * @param input           The CommandInput for the JTerminal to use.
     * @param output          The CommandOutput for the JTerminal to use.
     * @param commandPackages The CommandPackages for the JTerminal to use.
     */
    public JTerminal(String args, CommandInput input, CommandOutput output, CommandPackage... commandPackages) {
        this.input = input;
        this.output = output;
        this.commandHandler = new CommandHandler(commandPackages);
        try {
            ArgumentParser parser = new ArgumentParser();
            parser.parseArguments(args);

            try {
                if (parser.getString("directory") != null) {
                    commandHandler.setDirectory(new Directory(parser.getString("directory")));
                }
            } catch (Exception e) {
                output.print("Fatal error: ");
                output.getPrintStreams().forEach(e::printStackTrace);
            }

            try {
                if (parser.getString("startup") != null) {
                    for (String s : FileUtils.readFile(new File(parser.getString("startup"))).split("\n")) {
                        commandHandler.handleInput(input, s, output.clone());
                    }
                }
            } catch (FileUtils.FileIOException e) {
                output.printf("(Startup error) %s\n", e.getMessage());
            } catch (CommandHandler.CommandHandlerException e) {
                output.printf("(Startup error) Could not handle command (%s)\n", e.getMessage());
            } catch (Exception e) {
                output.print("Fatal error: ");
                output.getPrintStreams().forEach(e::printStackTrace);
            }

        } catch (ArgumentParseException e) {
            output.printf("Could not parse args \"%s\": %s\n", args, e.getMessage());
        } catch (Exception e) {
            output.println("Fatal error: Could not parse arguments: ");
            output.getPrintStreams().forEach(e::printStackTrace);
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
                output.printf("Could not handle command (%s)\n", e.getMessage());
            } catch (Exception e) {
                output.print("Fatal error: ");
                output.getPrintStreams().forEach(e::printStackTrace);
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

    public CommandOutput getOutput() {
        return output;
    }

    @NotNull
    public void setOutput(CommandOutput output) {
        this.output = output;
    }

}
