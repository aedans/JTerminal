package com.aedan.jterminal.commandpackages.defaultpackage.executors.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.environment.variables.GlobalVariable;
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
        if (args.length() == 3) {
            args.checkMatches(ArgumentType.STRING, ArgumentType.STRING);
            for (String s : args.get(1).value.split("\n")){
                environment.addGlobalVariable(new GlobalVariable("s", s));
                environment.handleInput(
                        input,
                        args.get(2).value,
                        output
                );
            }
        } else {
            args.checkMatches(ArgumentType.INTEGER, ArgumentType.INTEGER, ArgumentType.STRING, ArgumentType.STRING);
            for (int i = Integer.parseInt(args.get(1).value); i < Integer.parseInt(args.get(2).value); i++) {
                environment.addGlobalVariable(new GlobalVariable(args.get(3).value, String.valueOf(i)));
                environment.handleInput(
                        input,
                        args.get(4).value,
                        output
                );
                environment.removeGlobalVariable(args.get(3).value);
            }
        }
    }

}
