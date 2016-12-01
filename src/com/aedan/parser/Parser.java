package com.aedan.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.StringIterator;

import java.util.function.Predicate;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Class for creating CommandParser rules.
 */

// TODO: Error handling
public interface Parser<T> {
    /**
     * Parses a string.
     *
     * @param s The string to parse.
     * @param t The object to parse to.
     */
    default void parse(String s, T t) throws JTerminalException {
        StringIterator in = new StringIterator(s);
        onBeginParse(t, in);
        while (in.hasNext()) {
            this.parse(t, in);
        }
        onEndParse(t, in);
    }

    /**
     * Parses a string until a character.
     *
     * @param in   The string input to parse.
     * @param t    The object to parse to.
     * @param test The Predicate to test the end of parser.
     * @throws JTerminalException If there was an error parsing the string.
     */
    default void parseUntil(StringIterator in, T t, Predicate<StringIterator> test) throws JTerminalException {
        onBeginParse(t, in);
        while (in.hasNext() && test.test(in)) {
            this.parse(t, in);
        }
        onEndParse(t, in);
    }

    default void onBeginParse(T t, StringIterator in) {
    }

    default void onEndParse(T t, StringIterator in) {
    }

    /**
     * Processes a string.
     *
     * @param t  The object to parse to.
     * @param in The original String.
     * @throws JTerminalException If there is an error parsing the string.
     */
    boolean parse(T t, StringIterator in) throws JTerminalException;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
