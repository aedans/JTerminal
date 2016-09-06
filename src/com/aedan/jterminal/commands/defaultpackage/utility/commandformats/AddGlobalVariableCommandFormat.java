package com.aedan.jterminal.commands.defaultpackage.utility.commandformats;

import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.environment.variables.GlobalVariable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default CommandFormat.
 */

public class AddGlobalVariableCommandFormat implements CommandFormat {

    private final String addGlobalVariableCommandFormatRegex = "([^=]+)=> *(.+)";
    private final Pattern addGlobalVariableCommandFormatPattern = Pattern.compile(addGlobalVariableCommandFormatRegex);

    @Override
    public boolean matches(String in) throws CommandHandler.CommandHandlerException {
        return in.matches(addGlobalVariableCommandFormatRegex);
    }

    @Override
    public void handleInput(Environment environment, CommandInput input, String in, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            Matcher m = addGlobalVariableCommandFormatPattern.matcher(in);
            if (m.find()) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(os);
                environment.handleInput(input, m.group(1), new CommandOutput(ps));
                environment.addGlobalVariable(new GlobalVariable(environment.compute(input, m.group(2)), os.toString("UTF8").trim()));
                output.println("Created variable " + m.group(2));
            } else {
                throw new CommandHandler.CommandHandlerException(
                        "\"" + in + "\" does not match Add Global GlobalVariable format (command => destination).");
            }
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
