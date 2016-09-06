package com.aedan.jterminal.commands.defaultpackage.utility.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Aedan Smith on 9/5/2016.
 *
 * Default Command.
 */

public class Match extends Command {

    public Match() {
        super("match");
        properties[0] = "Outputs all matches in a String.";
        properties[1] =
                "Match [string-content] [string-pattern]:\n" +
                "   Outputs the group 1 of all matches of [string-pattern] in [string-content].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING, ArgumentType.STRING);

            Matcher m = Pattern.compile(args.get(2).value).matcher(args.get(1).value);
            while (m.find()) {
                output.println(m.group(1));
            }
        } catch (PatternSyntaxException e){
            throw new CommandHandler.CommandHandlerException("Invalid pattern: " + e.getMessage());
        }
    }

}
