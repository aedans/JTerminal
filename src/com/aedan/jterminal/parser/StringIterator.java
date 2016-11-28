package com.aedan.jterminal.parser;

import com.aedan.jterminal.JTerminalException;

import java.util.Iterator;

/**
 * Created by Aedan Smith.
 */

public class StringIterator implements Iterator<Character> {
    private int i = 0;
    private String string;

    public StringIterator(String string) {
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return i < string.length();
    }

    public void skip(int i) {
        this.i += i;
    }

    @Override
    public Character next() {
        if (i >= string.length())
            throw new JTerminalException("Unexpected end of string", this);
        else
            return string.charAt(i++);
    }

    public Character peek() {
        if (i >= string.length())
            throw new JTerminalException("Unexpected end of string", this);
        else
            return string.charAt(i);
    }

    public Character peek(int n) {
        if (i+n >= string.length())
            throw new JTerminalException("Unexpected end of string", this);
        else
            return string.charAt(i + n);
    }

    public String fromCurrent() {
        return string.substring(i);
    }

    public String untilCurrent() {
        return string.substring(0, i - 1);
    }

    public void end() {
        i = string.length();
    }
}
