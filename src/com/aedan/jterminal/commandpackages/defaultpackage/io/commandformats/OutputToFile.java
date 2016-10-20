package com.aedan.jterminal.commandpackages.defaultpackage.io.commandformats;

import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default CommandFormat.
 */

public class OutputToFile implements CommandFormat {

    public OutputToFile(Environment environment){
        environment.getCommandHandler().getTokenizer().addReservedChar('>');
    }

    @Override
    public boolean matches(List<String> tokens) throws CommandHandler.CommandHandlerException {
        return tokens.contains(">");
    }

    @Override
    public void handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException {
        try {
            int setIndex = tokens.indexOf(">");

            List<String> fileTokens = tokens.subList(setIndex + 1, tokens.size());
            String fileName = "";
            for (String s : fileTokens)
                fileName += s + " ";
            fileName = fileName.trim();

            File f = environment.getDirectory().getFile(fileName);
            FileUtils.createFile(f);
            CommandOutput fileOut = new CommandOutput(new PrintStream(new FileOutputStream(f)));

            environment.getCommandHandler().handleInput(input, fileOut, tokens.subList(0, setIndex));

            fileOut.getPrintStreams().forEach(PrintStream::close);
        } catch (Exception e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
