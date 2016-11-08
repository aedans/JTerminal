package com.aedan.jterminal.jterm.bashcommandpackage;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.PrintStreamOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class IfCommand extends Command {

    IfCommand() {
        super("if", "Executes a command if a command returns true.");
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING, ArgumentType.STRING);

        final String[] s = {""};
        environment.getCommandHandler().handleInput(input,
                new PrintStreamOutput(new PrintStream(new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                        s[0] += (char) b;
                    }
                })),
                args.get(1).value
        );

        if (Objects.equals(s[0].trim(), "true")) {
            environment.getCommandHandler().handleInput(input, output, args.get(2).value);
        }
    }
}
