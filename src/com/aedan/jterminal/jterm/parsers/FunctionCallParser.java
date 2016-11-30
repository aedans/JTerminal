package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.Function;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;

/**
 * Created by Aedan Smith.
 */

public class FunctionCallParser implements Parser<ArgumentList> {
    private JTermRuntime jTermRuntime;

    public FunctionCallParser(JTermRuntime jTermRuntime){
        this.jTermRuntime = jTermRuntime;
    }

    @Override
    public boolean parse(Environment environment, ArgumentList args, StringIterator in) throws JTerminalException {
        if (in.peek() != '@')
            return false;
        in.skip();

        String name = "";
        while (true) {
            if (in.peek() == '(') {
                in.next();
                break;
            } else {
                name += in.next();
            }
        }

        ArgumentList arguments = new ArgumentList();
        // TODO: Nested
        environment.getCommandHandler().getParser().parseUntil(
                environment, in, arguments, stringIterator -> stringIterator.peek() != ')'
        );
        in.skip();

        Function f = jTermRuntime.getFunction(name);
        if (f != null){
            args.add(new Argument(f.apply(arguments.toArray())));
            return true;
        } else {
            throw new JTerminalException("Could not find function with name " + name, this);
        }
    }
}
