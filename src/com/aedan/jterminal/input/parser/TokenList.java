package com.aedan.jterminal.input.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for maintaining a List of Tokens
 */

public class TokenList extends ArrayList<String> {

    /**
     * The current token
     */
    private String currentToken = "";

    public TokenList() {
    }

    public TokenList(List<String> objects) {
        this.addAll(objects);
    }

    /**
     * Appends a character to the current token.
     *
     * @param c The character to append.
     */
    public void append(char c) {
        currentToken += c;
    }

    /**
     * Ends the current token and begins the next one.
     */
    public void nextToken() {
        if (!Objects.equals(currentToken, "")) {
            add(currentToken);
            currentToken = "";
        }
    }

    public void addToken(String s) {
        nextToken();
        add(s);
    }

    public void addToken(char c) {
        nextToken();
        add(String.valueOf(c));
    }

    @Override
    public TokenList subList(int fromIndex, int toIndex) {
        return new TokenList(super.subList(fromIndex, toIndex));
    }

    /**
     * Trims the current token.
     */
    public void trimToken() {
        currentToken = currentToken.trim();
    }

    public String getCurrentToken() {
        return currentToken;
    }
}