package com.aedan.jterminal.parser;

import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.parser.parsers.*;
import com.aedan.parser.LinkedParser;
import com.aedan.parser.Parser;
import com.alibaba.fastjson.JSON;

/**
 * Created by Aedan Smith on 10/10/2016.
 */

public class CommandParser extends LinkedParser<StringIterator, ArgumentList> {
    public CommandParser(){
        this(
                new CharacterEscapeParser(),
                new NumberParser(),
                new FlagParser(),
                new StringLiteralParser()
        );
    }

    @SafeVarargs
    public CommandParser(Parser<StringIterator, ArgumentList>... parsers){
        super(new DefaultParser(), parsers);
    }

    @Override
    public String toString() {
        return "\"commandParser\":" + JSON.toJSONString(this, true);
    }
}
