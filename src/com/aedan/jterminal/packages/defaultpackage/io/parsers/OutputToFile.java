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
    // TODO: Rewrite
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        try {
            if (in.peek() != '>')
                return false;
            in.next();

            // TODO: Remove getLast()
            String fileName = parser.parse(environment, in.fromCurrent()).getLast().toString();

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
            in.end();
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
