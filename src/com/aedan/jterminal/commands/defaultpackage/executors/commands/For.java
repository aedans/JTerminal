package com.aedan.jterminal.commands.defaultpackage.executors.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.variables.Variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class For extends Command {

    private final CommandHandler commandHandler;

    public For(CommandHandler commandHandler) {
        super("for");
        this.properties[0] = "Iterates a command and stores the current iteration in a variable.";
        this.properties[1] =
                "for [int-begin] [int-end] [string-varname] [string-command]:\n" +
                "   Adds the variable [string-varname] to the CommandHandler, then executes [string-command] once\n" +
                "   for each value between [int-begin] and [int-end].\n" +
                "for [string-content] [string-command]:\n" +
                "   Adds the variable s to the CommandHandler, then executes [string-command] once for each line\n" +
                "   in [string-content], setting variable s to the content of the line.";
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.length() == 3) {
            args.checkMatches(ArgumentType.STRING, ArgumentType.STRING);
            for (String s : args.get(1).value.split("\n")){
                commandHandler.addVariable(new Variable("s", s));
                commandHandler.handleInput(
                        input,
                        args.get(2).value,
                        output
                );
            }
        } else {
            args.checkMatches(ArgumentType.INTEGER, ArgumentType.INTEGER, ArgumentType.STRING, ArgumentType.STRING);
            for (int i = Integer.parseInt(args.get(1).value); i < Integer.parseInt(args.get(2).value); i++) {
                commandHandler.addVariable(new Variable(args.get(3).value, String.valueOf(i)));
                commandHandler.handleInput(
                        input,
                        args.get(4).value,
                        output
                );
                commandHandler.removeVariable(args.get(3).value);
            }
        }
    }

}
