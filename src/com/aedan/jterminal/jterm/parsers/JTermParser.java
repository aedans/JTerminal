package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.Function;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class JTermParser implements Parser<HashMap<String, Function>> {
    private FunctionParser functionParser;

    public JTermParser() {
        this.functionParser = new FunctionParser();
    }

    @Override
    public boolean parse(Environment environment, HashMap<String, Function> functions, StringIterator in)
            throws JTerminalException {
        if (in.hasNext(3) && Objects.equals(in.peekString(3), "fn ")){
            in.skip(3);
            return functionParser.parse(environment, functions, in);
        }
        in.next();

        return false;
    }
}
