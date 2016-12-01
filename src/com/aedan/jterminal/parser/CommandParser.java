package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.parsers.*;
import com.aedan.parser.*;
import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for the CommandHandler.
 */

public class CommandParser extends LinkedParser<ArgumentList> {
    public CommandParser(){
        super(new DefaultParser(),
                new CharacterEscapeParser(),
                new NumberParser(),
                new FlagParser(),
                new StringLiteralParser()
        );
    }

    @SafeVarargs
    public CommandParser(Parser<ArgumentList>... parsers){
        super(new DefaultParser(), parsers);
    }

    @Override
    public String toString() {
        return "\"commandParser\":" + JSON.toJSONString(this, true);
    }
}
