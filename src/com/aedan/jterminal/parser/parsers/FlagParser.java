package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith.
 */

public class FlagParser implements Parser<ArgumentList> {
    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '-' || (in.peek(1) <= '9' && in.peek(1) >= '0'))
            return false;
        in.next();

        String name = "";
        while (in.hasNext()) {
            if ((in.peek() >= 'a' && in.peek() <= 'z') || (in.peek() >= 'A' && in.peek() <= 'Z'))
                name += in.next();
            else
                return false;
        }
        argumentList.flags.add(name);
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
