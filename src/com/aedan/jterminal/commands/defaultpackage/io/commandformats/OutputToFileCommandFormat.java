package com.aedan.jterminal.commands.defaultpackage.io.commandformats;

import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default CommandFormat.
 */

public class OutputToFileCommandFormat implements CommandFormat {

    private final String outputToFileCommandFormatRegex = "([^>]+)>> *(.+)";
    private final Pattern outputToFileCommandFormatPattern = Pattern.compile(outputToFileCommandFormatRegex);

    @Override
    public boolean matches(String in) throws CommandHandler.CommandHandlerException {
        return in.matches(outputToFileCommandFormatRegex);
    }

    @Override
    public void handleInput(CommandHandler commandHandler, CommandInput input, String in, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            Matcher m = outputToFileCommandFormatPattern.matcher(in);
            if (m.find()) {
                File f = commandHandler.getDirectory().getFile(m.group(2));
                output.println(FileUtils.createFile(f));
                PrintStream ps = new PrintStream(new FileOutputStream(f));
                output.setPrintStreams(ps);
                commandHandler.handleInput(input, m.group(1), output);
                ps.close();
            } else {
                throw new CommandHandler.CommandHandlerException(
                        "\"" + in + "\" does not match CommandOutput To File format (command >> destination).");
            }
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
