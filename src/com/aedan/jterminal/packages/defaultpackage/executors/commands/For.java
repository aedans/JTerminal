package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.Collection;
import java.util.Iterator;

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
                        "    for each value between [int-begin] and [int-end], setting variable i to the current index.\n" +
                        "for [list-content] [string-command]:\n" +
                        "    Adds the variable s to the CommandHandler, then executes [string-command] once for each item\n" +
                        "    in [list-content], setting variable o to the object at the current index.";
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.matches(Object.class, Object.class) == MatchResult.CORRECT_ARGS) {
            Object[] objects;
            if (args.get(1).get().getClass().isArray()) {
                objects = (Object[]) args.get(1).get();
            } else if (args.get(1).get().getClass().isAssignableFrom(Collection.class)) {
                objects = ((Collection) args.get(1).get()).toArray();
            } else {
                objects = args.get(1).get().toString().split("\n");
            }
            return new Iterator<Object>(){
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < objects.length;
                }

                @Override
                public Object next() {
                    //noinspection unchecked
                    environment.getEnvironmentVariables().put("o", objects[i]);
                    Object o = environment.getCommandHandler().handleInput(args.get(2).toString(), input, output);
                    environment.getEnvironmentVariables().remove("o");
                    i++;
                    return o;
                }

                @Override
                public String toString() {
                    String s = "";
                    while (hasNext()){
                        s += next() + "\n";
                    }
                    return s.substring(0, s.length()-1);
                }
            };
        } else if (args.matches(Number.class, Number.class, String.class) == MatchResult.CORRECT_ARGS) {
            long max = ((Number) args.get(2).get()).longValue();
            return new Iterator<Object>() {
                long i = ((Number) args.get(1).get()).longValue();

                @Override
                public boolean hasNext() {
                    return i < max;
                }

                @Override
                public Object next() {
                    //noinspection unchecked
                    environment.getEnvironmentVariables().put("i", i);
                    Object o = environment.getCommandHandler().handleInput(args.get(3).toString(), input, output);
                    environment.getEnvironmentVariables().remove("i");
                    i++;
                    return o;
                }

                @Override
                public String toString() {
                    String s = "";
                    while (hasNext()){
                        s += next() + "\n";
                    }
                    return s.substring(0, s.length()-1);
                }
            };
        } else {
            throw new JTerminalException("Incorrect arguments given", this);
        }
    }
}
