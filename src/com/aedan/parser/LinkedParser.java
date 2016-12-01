package com.aedan.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.parser.StringIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aedan Smith.
 */

public class LinkedParser<T> implements Parser<T> {
    private List<Parser<T>> parsers = new ArrayList<>();
    private Parser<T> defaultParser;

    @SafeVarargs
    public LinkedParser(Parser<T> defaultParser, Parser<T>... parsers){
        this.defaultParser = defaultParser;
        Collections.addAll(this.parsers, parsers);
    }

    @Override
    public boolean parse(T t, StringIterator in) throws JTerminalException {
        for (Parser<T> parser : parsers){
            if (parser.parse(t, in))
                return true;
        }
        return defaultParser.parse(t, in);
    }

    public void addParser(Parser<T> parser) {
        if (!this.parsers.contains(parser))
            this.parsers.add(parser);
    }

    public List<Parser<T>> getParsers() {
        return parsers;
    }

    public Parser<T> getDefaultParser() {
        return defaultParser;
    }
}
