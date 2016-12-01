package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.parser.ParseException;
import com.aedan.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class IfStatementParser implements Parser<StringIterator, ArgumentList> {
    private JTermRuntime jTermRuntime;

    public IfStatementParser(JTermRuntime jTermRuntime){
        this.jTermRuntime = jTermRuntime;
    }

    @Override
    public boolean parse(ArgumentList args, StringIterator in) throws ParseException {
        if (in.peek() != '?')
            return false;
        in.skip();

        args.add("if");

        in.until('(');
        in.skip();

        ArgumentList arguments = new ArgumentList();
        // TODO: Nested
        jTermRuntime.getEnvironment().getCommandHandler().getParser().parseUntil(
                in, arguments, stringIterator -> stringIterator.peek() != ')'
        );
        in.skip();

        args.add(new Argument(jTermRuntime.getEnvironment().getCommandHandler().handleInput(
                arguments,
                jTermRuntime.getEnvironment().getInput(),
                jTermRuntime.getEnvironment().getOutput()
        )));

        String s = in.until('\n');
        args.add(new Argument(s, String.class));
        return true;
    }
}
