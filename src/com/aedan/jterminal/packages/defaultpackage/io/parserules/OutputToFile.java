package com.aedan.jterminal.packages.defaultpackage.io.parserules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.TokenList;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.PrintStreamOutput;
import com.aedan.jterminal.output.StringOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Operand.
 */

public class OutputToFile implements ParseRule {

    @Override
    public char getIdentifier() {
        return '>';
    }

    @Override
    public int process(Environment environment, String s, int i, TokenList tokenList) throws CommandHandler.CommandHandlerException {
        try {
            StringOutput fileName = new StringOutput();
            environment.getCommandHandler().handleInput(fileName, s.substring(i+1));

            File f = environment.getDirectory().subFile(fileName.getString().trim());
            if (f.exists())
                f.delete();
            FileUtils.createFile(f);
            CommandOutput fileOut = new PrintStreamOutput(new PrintStream(new FileOutputStream(f)));

            environment.getCommandHandler().handleInput(fileOut, s.substring(0, i));
            fileOut.close();
            tokenList.clear();
            return s.length();
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage(), this);
        }
    }
}
