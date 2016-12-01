package com.aedan.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.StringIterator;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating Parsers.
 *
 * @param <I> The Iterable input to parse.
 * @param <T> The class to parse to.
 */

public interface Parser<I extends Iterator, T> {
    /**
     * Parses input.
     *
     * @param in The input to parse.
     * @param t  The object to parse to.
     */
    default void parse(I in, T t) throws ParseException {
        onBeginParse(t, in);
        while (in.hasNext()) {
            this.parse(t, in);
        }
        onEndParse(t, in);
    }

    /**
     * Parses input.
     *
     * @param in   The input to parse.
     * @param t    The object to parse to.
     * @param test The Predicate to test the end of parser.
     * @throws JTerminalException If there was an error parsing the input.
     */
    default void parseUntil(I in, T t, Predicate<I> test) throws ParseException {
        onBeginParse(t, in);
        while (in.hasNext() && test.test(in)) {
            this.parse(t, in);
        }
        onEndParse(t, in);
    }

    default void onBeginParse(T t, I in) {
    }

    default void onEndParse(T t, I in) {
    }

    /**
     * Parses input.
     *
     * @param t  The object to parse to.
     * @param in The input.
     * @throws JTerminalException If there is an error parsing the input.
     */
    boolean parse(T t, I in) throws ParseException;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
