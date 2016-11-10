package com.aedan.jterminal.packages.defaultpackage.io.commandformats;

import com.aedan.jterminal.command.Operand;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.PrintStreamOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Operand.
 */

public class OutputToFile implements Operand {

    public OutputToFile(Environment environment){
        environment.getCommandHandler().getTokenizer().addReservedChar('>');
    }
    @Override
    public boolean handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException {
        try {
            int index = tokens.indexOf(">");
            if (index == -1)
                return false;

            List<String> fileTokens = tokens.subList(index + 1, tokens.size());
            String fileName = "";
            for (String s : fileTokens)
                fileName += s + " ";
            fileName = fileName.trim();

            File f = environment.getDirectory().subFile(fileName);
            FileUtils.createFile(f);
            CommandOutput fileOut = new PrintStreamOutput(new PrintStream(new FileOutputStream(f)));

            environment.getCommandHandler().handleInput(input, fileOut, tokens.subList(0, index));

            fileOut.close();
            return true;
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage(), this);
        }
    }
}
