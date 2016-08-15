package com.aedan.jterminal;

import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.SystemInput;
import com.sun.istack.internal.NotNull;

import java.util.Objects;

/**
 * Created by Aedan Smith on 8/10/16.
 *
 * The main JTerminal class.
 */

public class JTerminal implements Runnable {

    /**
     * The input for the JTerminal.
     */
    private CommandInput input = new SystemInput();

    /**
     * The CommandHandler for the JTerminal
     */
    private CommandHandler commandHandler = new CommandHandler();

    /**
     * The Output for the JTerminal.
     */
    private Output output = new Output();

    /**
     * The default JTerminal constructor.
     */
    public JTerminal(){

    }

    /**
     * Starts the JTerminal. Can be exited by entering "exit".
     */
    public void run(){
        String in;
        while (true){
            try {
                output.print(commandHandler.getDirectory() + "> ");
                in = input.nextLine();
                if (Objects.equals(in.trim(), "exit")) {
                    break;
                } else {
                    commandHandler.handleString(in.replaceAll(" {2,}", " "), output);
                }
            } catch (CommandHandler.CommandHandlerException e) {
                output.println("Could not handle command (" + e.getMessage() + ")");
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
