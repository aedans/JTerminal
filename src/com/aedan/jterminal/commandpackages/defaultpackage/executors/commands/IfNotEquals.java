package com.aedan.jterminal.commandpackages.defaultpackage.executors.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.Objects;

/**
 * Created by Aedan Smith on 8/16/2016.
 * <p>
 * Default Command.
 */

public class IfNotEquals extends Command {

    public IfNotEquals() {
        super("!=");
        this.properties[0] = "Executes a Command if two Strings are not equal.";
        this.properties[1] =
                "!= [string-test1] [string-test2] [string-command]:\n" +
                        "    Executes [string-command] if [string-test1] and [string-test2] are not equal.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.matches(ArgumentType.STRING, ArgumentType.STRING, ArgumentType.STRING) != 0)
            throw new CommandHandler.CommandHandlerException("Incorrect arguments given");

        if (!Objects.equals(args.get(1).value, args.get(2).value)) {
            environment.getCommandHandler().handleInput(args.get(3).value);
        }
    }

}
