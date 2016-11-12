package com.aedan.jterminal.command;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.input.parser.TokenList;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.executors.commands.ExecuteJTermFile;

import java.io.File;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/10/16.
 * <p>
 * The CommandHandler for the JTerminal.
 */

public class CommandHandler {
    /**
     * The Environment containing the CommandHandler.
     */
    private Environment environment;

    /**
     * The Parser for the CommandHandler.
     */
    private Parser parser = new Parser();

    /**
     * Default CommandHandler constructor.
     *
     * @param environment The Environment containing the CommandHandler.
     */
    public CommandHandler(Environment environment) {
        this.environment = environment;
    }

    /**
     * Handles a line of input.
     *
     * @param s             The String to handle.
     * @param commandInput  The CommandInput to read the input from.
     * @param commandOutput The CommandOutput to write the output to.
     */
    public void handleInput(CommandInput commandInput, CommandOutput commandOutput, String s) {
        try {
            this.handleInput(commandInput, commandOutput, parser.parse(environment, s));
        } catch (JTerminalException e) {
            commandOutput.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    /**
     * Handles a pre-parsed line of input.
     *
     * @param input  The CommandInput to read the input from.
     * @param output The CommandOutput to write the output to.
     * @param tokens The list of parsed Tokens.
     */
    public void handleInput(CommandInput input, CommandOutput output, TokenList tokens) {
        try {
            if (tokens == null || input == null || output == null) {
                throw new IllegalArgumentException("Input is null");
            }
            if (tokens.isEmpty()) {
                return;
            }

            for (Command command : environment.getCommands()) {
                if (Objects.equals(command.getIdentifier(), tokens.get(0))) {
                    command.parse(new CommandArgumentList(tokens), input, output, environment);
                    return;
                }
            }

            File file = environment.getPath().get(tokens.get(0) + ".jterm");
            if (file != null) {
                ExecuteJTermFile.execute(tokens, input, output, environment);
                return;
            }

            if (tokens.size() == 1) {
                output.println(tokens.get(0));
                return;
            }

            throw new JTerminalException("Unrecognized Command \"" + tokens.get(0) + "\"", this);
        } catch (JTerminalException e) {
            output.printf("Could not handle command (%s)\n", e.getMessage());
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}