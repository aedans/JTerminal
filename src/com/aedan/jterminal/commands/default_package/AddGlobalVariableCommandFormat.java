package com.aedan.jterminal.commands.default_package;

import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.variables.Variable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/22/2016.
 *
 * Default CommandFormat
 */

public class AddGlobalVariableCommandFormat implements CommandFormat {

    private String addGlobalVariableCommandFormatRegex = "([^=]+)=> *(.+)";
    private Pattern addGlobalVariableCommandFormatPattern = Pattern.compile(addGlobalVariableCommandFormatRegex);

    @Override
    public boolean matches(String in) throws CommandHandler.CommandHandlerException {
        return in.matches(addGlobalVariableCommandFormatRegex);
    }

    @Override
    public void handleString(CommandHandler commandHandler, String in, Output output) throws CommandHandler.CommandHandlerException {
        try {
            Matcher m = addGlobalVariableCommandFormatPattern.matcher(in);
            if (m.find()){
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(os);
                commandHandler.handleString(m.group(1), new Output(ps));
                String vValue = os.toString("UTF8").trim();
                commandHandler.addVariable(new Variable(m.group(2), vValue));
                output.println("Created variable " + m.group(2));
            } else {
                throw new CommandHandler.CommandHandlerException("\"" + in + "\" does not match Output To File format (command => destination).");

            }
        } catch (Exception e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
