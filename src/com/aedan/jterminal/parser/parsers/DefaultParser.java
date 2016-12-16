package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ConstantArgument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.parser.Parser;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Aedan Smith.
 */

public class DefaultParser implements Parser<StringIterator, ArgumentList> {
    public ArrayList<Character> reserved = new ArrayList<>();

    {
        reserved.add('_');
        reserved.add('-');
    }

    @Override
    public boolean parse(ArgumentList arguments, StringIterator in) throws JTerminalException {
        if (!in.hasNext())
            return false;

        if (in.isInRange('\0', ' ')) {
            in.skip();
            return false;
        }

        String s = in.until(stringIterator -> stringIterator.hasNext()
                && (stringIterator.isInRange('A', 'Z')
                || stringIterator.isInRange('a', 'z')
                || reserved.contains(in.peek())));

        if (s.length() == 0) {
            arguments.add(new ConstantArgument(in.until(stringIterator ->
                    stringIterator.hasNext() && !stringIterator.isInRange('\0', ' ')), String.class));
            return true;
        }

        arguments.add(new ConstantArgument(s, String.class));
        return true;
    }
}
