package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ConstantArgument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class DefaultParser implements Parser<StringIterator, ArgumentList> {
    @Override
    public boolean parse(ArgumentList arguments, StringIterator in) throws JTerminalException {
        if (in.isInRange('\0', ' ')) {
            in.skip();
            return false;
        }

        StringBuilder s = new StringBuilder();
        while (in.hasNext() &&
                (in.isInRange('A', 'Z') || in.isInRange('a', 'z') || in.isInRange('0', '9') || in.peek() == '_'))
            s.append(in.next());

        if (s.toString().length() == 0) {
            arguments.add(new ConstantArgument(in.until(stringIterator -> !stringIterator.isInRange('\0', ' ')), String.class));
            return true;
        }

        arguments.add(new ConstantArgument(s.toString(), String.class));
        return true;
    }
}
