package com.aedan.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.parser.StringIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aedan Smith.
 *
 * Class for Linking Parsers.
 *
 * @param <I> The Iterable input to parse.
 * @param <T> The class to parse to.
 */

public class LinkedParser<I extends Iterator, T> implements Parser<I, T> {
    /**
     * The List of Parsers for the LinkedParser to parse.
     */
    private List<Parser<I, T>> parsers = new ArrayList<>();

    /**
     * The default parser.
     */
    private Parser<I, T> defaultParser;

    /**
     * Default LinkedParser constructor.
     *
     * @param defaultParser The default Parser for the LinkedParser.
     * @param parsers       The array of Parsers for the LinkedParser to parse
     */
    @SafeVarargs
    public LinkedParser(Parser<I, T> defaultParser, Parser<I, T>... parsers){
        this.defaultParser = defaultParser;
        Collections.addAll(this.parsers, parsers);
    }

    /**
     * Parses an input, asking each Parser to parse the input and ending once a Parser parses it. If no Parser accepts
     * the input, the default parser parses it instead.
     */
    @Override
    public boolean parse(T t, I in) throws JTerminalException {
        for (Parser<I, T> parser : parsers){
            if (parser.parse(t, in))
                return true;
        }
        return defaultParser.parse(t, in);
    }

    /**
     * Adds a Parser to the LinkedParser.
     */
    public void addParser(Parser<I, T> parser) {
        if (!this.parsers.contains(parser))
            this.parsers.add(parser);
    }

    public List<Parser<I, T>> getParsers() {
        return parsers;
    }

    public Parser<I, T> getDefaultParser() {
        return defaultParser;
    }
}
