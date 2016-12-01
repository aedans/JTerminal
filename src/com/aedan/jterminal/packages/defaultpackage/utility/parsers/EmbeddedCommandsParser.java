package com.aedan.jterminal.packages.defaultpackage.utility.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for embedded command.
 */

public class EmbeddedCommandsParser implements Parser<StringIterator, ArgumentList> {
    private Environment environment;

    public EmbeddedCommandsParser(Environment environment){
        this.environment = environment;
    }

    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '[')
            return false;
        in.next();

        ArgumentList arguments = new ArgumentList();
        // TODO: Nested predicate
        environment.getCommandHandler().getParser().parseUntil(
                in, arguments, stringIterator -> stringIterator.peek() != ']'
        );
        in.next();

        argumentList.add(new Argument(environment.getCommandHandler().handleInput(
                arguments,
                environment.getInput(),
                environment.getOutput()
        )));
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}