package com.aedan.jterminal.commands.defaultpackage.executors.commands;

import com.aedan.jterminal.environment.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
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

    private final Environment environment;

    public IfNotEquals(Environment environment) {
        super("!=");
        this.properties[0] = "Executes a Command if two Strings are not equal.";
        this.properties[1] =
                "!= [string-test1] [string-test2] [string-command]:\n" +
                "   Executes [string-command] if [string-test1] and [string-test2] are not equal.";
        this.environment = environment;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING, ArgumentType.STRING, ArgumentType.STRING);

        if (!Objects.equals(args.get(1).value, args.get(2).value)){
            environment.handleInput(input, args.get(3).value, output);
        }
    }

}
