package com.aedan.parser;

/**
 * Created by Aedan Smith.
 */

public class ParseException extends RuntimeException {
    public ParseException(String message, Object cause){
        super(message + " (" + cause.toString() + ")");
    }
}
