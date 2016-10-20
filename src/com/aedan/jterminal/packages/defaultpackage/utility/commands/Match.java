package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Aedan Smith on 9/5/2016.
 * <p>
 * Default Command.
 */

public class Match extends Command {

    public Match() {
        super("match");
        properties[0] = "Outputs all matches in a String.";
        properties[1] =
                "Match [string-content] [string-pattern]:\n" +
                        "    Outputs the group 1 of all matches of [string-pattern] in [string-content].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            if (args.matches(ArgumentType.STRING, ArgumentType.STRING) != 0)
                throw new CommandHandler.CommandHandlerException("Incorrect arguments given");

            Matcher m = Pattern.compile(args.get(2).value).matcher(args.get(1).value);
            while (m.find()) {
                output.println(m.group(1));
            }
        } catch (PatternSyntaxException e) {
            throw new CommandHandler.CommandHandlerException("Invalid pattern: " + e.getMessage());
        }
    }

}
