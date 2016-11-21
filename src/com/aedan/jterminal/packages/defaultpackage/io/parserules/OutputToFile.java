package com.aedan.jterminal.packages.defaultpackage.io.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.output.StringOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Operand.
 */

public class OutputToFile implements ParseRule {
    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s) throws JTerminalException {
        try {
            if (s.charAt(i) != '>')
                return -1;

            String fileName = environment.getCommandHandler().handleInput(
                    s.substring(i + 1),
                    environment.getInput(),
                    environment.getOutput()
            ).toString();

            File f = environment.getDirectory().subFile(fileName.trim());
            if (f.exists())
                f.delete();
            FileUtils.createFile(f);

            Object object = environment.getCommandHandler().handleInput(
                    s.substring(0, i),
                    environment.getInput(),
                    environment.getOutput()
            );
            StringOutput stringOutput = new StringOutput();
            stringOutput.println(object);
            FileUtils.writeToFile(f, stringOutput.getString().trim());

            argumentList.clear();
            return s.length();
        } catch (Exception e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
