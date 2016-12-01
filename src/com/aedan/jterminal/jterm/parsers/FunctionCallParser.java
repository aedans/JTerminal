package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.Function;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith.
 */

public class FunctionCallParser implements Parser<ArgumentList> {
    private JTermRuntime jTermRuntime;

    public FunctionCallParser(JTermRuntime jTermRuntime){
        this.jTermRuntime = jTermRuntime;
    }

    @Override
    public boolean parse(ArgumentList args, StringIterator in) throws JTerminalException {
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
        jTermRuntime.getEnvironment().getCommandHandler().getParser().parseUntil(
                in, arguments, stringIterator -> stringIterator.peek() != ')'
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
