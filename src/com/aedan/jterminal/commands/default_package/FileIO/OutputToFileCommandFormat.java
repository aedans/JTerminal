package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.CommandHandler;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Default CommandFormat.
 */

class OutputToFileCommandFormat implements CommandFormat {

    private String outputToFileCommandFormatRegex = "([^>]+)>>(.+)";
    private Pattern outputToFileCommandFormatPattern = Pattern.compile(outputToFileCommandFormatRegex);

    @Override
    public boolean matches(String in) throws CommandHandler.CommandHandlerException {
        return in.matches(outputToFileCommandFormatRegex);
    }

    @Override
    public void handleString(CommandHandler commandHandler, String in, Output output) throws CommandHandler.CommandHandlerException {
        try {
            Matcher m = outputToFileCommandFormatPattern.matcher(in);
            if (m.find()) {
                output.setOutput(new PrintStream(new FileOutputStream(commandHandler.getDirectory().getFile(m.group(2)))));
                commandHandler.handleString(m.group(1), output);
            } else {
                throw new CommandHandler.CommandHandlerException("\"" + in + "\" does not match Output To File format (command >> destination).");
            }
        } catch (Exception e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
