package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class For extends Command {

    public For() {
        super("for");
        this.properties[0] = "Iterates a command and stores the current iteration in a variable.";
        this.properties[1] =
                "for [int-begin] [int-end] [string-varname] [string-command]:\n" +
                        "    Adds the variable [string-varname] to the CommandHandler, then executes [string-command] once\n" +
                        "    for each value between [int-begin] and [int-end].\n" +
                        "for [string-content] [string-command]:\n" +
                        "    Adds the variable s to the CommandHandler, then executes [string-command] once for each line\n" +
                        "    in [string-content], setting variable s to the content of the line.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.matches(ArgumentType.STRING, ArgumentType.STRING) == MatchResult.CORRECT_ARGS) {
            for (String s : args.get(1).value.split("\n")) {
                environment.addGlobalVariable("s", s);
                environment.getCommandHandler().handleInput(args.get(2).value);
            }
        } else if (args.matches(ArgumentType.INTEGER, ArgumentType.INTEGER, ArgumentType.STRING, ArgumentType.STRING) == MatchResult.CORRECT_ARGS) {
            for (int i = Integer.parseInt(args.get(1).value); i < Integer.parseInt(args.get(2).value); i++) {
                environment.addGlobalVariable(args.get(3).value, String.valueOf(i));
                environment.getCommandHandler().handleInput(args.get(4).value);
            }
            environment.removeGlobalVariable(args.get(3).value);
        } else {
            throw new CommandHandler.CommandHandlerException("Incorrect arguments given");
        }
    }

}