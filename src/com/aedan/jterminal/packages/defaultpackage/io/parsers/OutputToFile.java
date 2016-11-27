package com.aedan.jterminal.packages.defaultpackage.io.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.output.PrintStreamOutput;
import com.aedan.jterminal.packages.defaultpackage.io.Directory;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Operand.
 */

public class OutputToFile extends Parser {
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

            File f = ((Directory) environment.getEnvironmentVariable("DIR")).subFile(fileName.trim());
            if (f.exists())
                f.delete();
            FileUtils.createFile(f);

            Object object = environment.getCommandHandler().handleInput(
                    in.untilCurrent(),
                    environment.getInput(),
                    environment.getOutput()
            );

            PrintStreamOutput output = new PrintStreamOutput(new PrintStream(new FileOutputStream(f)));
            output.println(object);

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
