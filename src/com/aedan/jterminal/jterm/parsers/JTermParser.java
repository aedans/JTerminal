package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.jterm.Function;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class JTermParser implements Parser<StringIterator, HashMap<String, Function>> {
    private FunctionParser functionParser;

    public JTermParser(Environment environment) {
        this.functionParser = new FunctionParser(environment);
    }

    @Override
    public boolean parse(HashMap<String, Function> functions, StringIterator in)
            throws JTerminalException {
        if (in.hasNext(3) && Objects.equals(in.peekString(3), "fn ")){
            in.skip(3);
            return functionParser.parse(functions, in);
        }
        in.next();

        return false;
    }
}
