package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
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
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.matches(ArgumentType.STRING, ArgumentType.STRING) == MatchResult.CORRECT_ARGS) {
            StringHolder holder = new StringHolder("");
            environment.addGlobalVariable("s", holder);
            for (String s : args.get(1).value.split("\n")) {
                holder.setS(s);
                environment.getCommandHandler().handleInput(args.get(2).value, input, output);
            }
        } else if (args.matches(ArgumentType.LONG, ArgumentType.LONG, ArgumentType.STRING, ArgumentType.STRING) == MatchResult.CORRECT_ARGS) {
            LongHolder i = new LongHolder(Long.parseLong(args.get(1).value));
            long max = Long.parseLong(args.get(2).value);
            environment.addGlobalVariable(args.get(3).value, i);
            for (; i.getI() < max; i.increment()) {
                environment.getCommandHandler().handleInput(args.get(4).value, input, output);
            }
            environment.removeGlobalVariable(args.get(3).value);
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

class LongHolder {
    private long i;

    LongHolder(long i) {
        this.i = i;
    }

    public long getI() {
        return i;
    }

    void increment() {
        ++i;
    }

    @Override
    public String toString() {
        return Long.toString(i);
    }
}