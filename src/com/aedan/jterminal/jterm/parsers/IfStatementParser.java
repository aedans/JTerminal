package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.command.commandarguments.ConstantArgument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.parser.ParseException;
import com.aedan.parser.Parser;

import java.util.Objects;

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
        if (!in.hasNext(2) || !Objects.equals(in.peekString(2), "if"))
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

        args.add(new ConstantArgument(jTermRuntime.getEnvironment().getCommandHandler().handleInput(
                arguments,
                jTermRuntime.getEnvironment().getInput(),
                jTermRuntime.getEnvironment().getOutput()
        )));

        in.until('{');
        in.skip();

        ArgumentList arguments1 = new ArgumentList();
        jTermRuntime.getEnvironment().getCommandHandler().getParser().parseUntil(in, arguments1,
                stringIterator -> stringIterator.hasNext() && stringIterator.peek() != '}');
        args.add(new ConstantArgument(arguments1));

        in.skip();
        return true;
    }
}
