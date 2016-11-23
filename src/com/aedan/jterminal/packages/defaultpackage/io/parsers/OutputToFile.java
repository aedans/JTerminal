package com.aedan.jterminal.packages.defaultpackage.io.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.output.StringOutput;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Operand.
 */

public class OutputToFile implements Parser {
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        try {
            if (in.peek() != '>')
                return false;
            in.next();

            String fileName = environment.getCommandHandler().handleInput(
                    in.fromCurrent(),
                    environment.getInput(),
                    environment.getOutput()
            ).toString();

            File f = environment.getDirectory().subFile(fileName.trim());
            if (f.exists())
                f.delete();
            FileUtils.createFile(f);

            Object object = environment.getCommandHandler().handleInput(
                    in.untilCurrent(),
                    environment.getInput(),
                    environment.getOutput()
            );

            StringOutput stringOutput = new StringOutput();
            stringOutput.println(object);
            FileUtils.writeToFile(f, stringOutput.getString().trim());

            argumentList.clear();
            return true;
        } catch (Exception e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
