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
 * Parser for environment variables.
 */

public class EnvironmentVariableParser implements Parser<ArgumentList> {
    private Parser<StringBuilder> parser = (stringBuilder, in) -> {
        stringBuilder.append(in.next());
        return false;
    };

    private Environment environment;

    public EnvironmentVariableParser(Environment environment){
        this.environment = environment;
    }

    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '%')
            return false;
        in.next();

        StringBuilder builder = new StringBuilder();
        parser.parseUntil(in, builder, s ->
                (s.peek() >= 'a' && s.peek() <= 'z')
                        || (s.peek() >= 'A' && s.peek() <= 'Z')
                        || (s.peek() >= '0' && s.peek() <= '9')
                        || s.peek() == '_');
        String varName = builder.toString();

        Object value = environment.getEnvironmentVariable(varName);
        if (value == null)
            throw new JTerminalException("Could not find environment variable with name " + varName, this);
        else
            argumentList.add(new Argument(value));
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
