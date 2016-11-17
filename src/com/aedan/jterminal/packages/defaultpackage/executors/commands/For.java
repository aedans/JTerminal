package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.ArrayList;

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
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.matches(String.class, String.class) == MatchResult.CORRECT_ARGS) {
            ArrayList<Object> objects = new ArrayList<>();
            StringHolder holder = new StringHolder("");
            environment.addGlobalVariable("s", holder);
            for (String s : args.get(1).toString().split("\n")) {
                holder.setS(s);
                objects.add(environment.getCommandHandler().handleInput(args.get(2).toString(), input, output));
            }
            return objects;
        } else if (args.matches(Number.class, Number.class, String.class) == MatchResult.CORRECT_ARGS) {
            ArrayList<Object> objects = new ArrayList<>();
            int max = (int) args.get(2).value;

            for (int i = (int) args.get(1).value; i < max; i++) {
                environment.addGlobalVariable("i", i);
                objects.add(environment.getCommandHandler().handleInput(args.get(3).toString(), input, output));
                environment.removeGlobalVariable("i");
            }

            return objects;
        } else {
            throw new JTerminalException("Incorrect arguments given", this);
        }
    }
}

class StringHolder {
    private String s;

    StringHolder(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
