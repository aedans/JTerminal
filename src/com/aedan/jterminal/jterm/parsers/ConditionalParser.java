package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.parser.ParseException;
import com.aedan.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class ConditionalParser implements Parser<StringIterator, ArgumentList> {
    public ConditionalParser(JTermRuntime environment){

    }

    @Override
    public boolean parse(ArgumentList arguments, StringIterator in) throws ParseException {
        return false;
    }
}
