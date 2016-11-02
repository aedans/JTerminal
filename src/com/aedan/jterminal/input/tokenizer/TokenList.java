package com.aedan.jterminal.input.tokenizer;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for maintaining a List of Tokens
 */

public class TokenList {

    /**
     * The current List of Tokens.
     */
    private ArrayList<String> tokens = new ArrayList<>();

    private String currentToken = "";

    /**
     * Appends a character to the current token.
     *
     * @param c The character to append.
     */
    public void append(char c){
        currentToken += c;
    }

    /**
     * Ends the current token and begins the next one.
     */
    public void nextToken(){
        if (!Objects.equals(currentToken, "")) {
            tokens.add(currentToken);
            currentToken = "";
        }
    }

    public void addToken(String s) {
        nextToken();
        tokens.add(s);
    }

    public void addToken(char c) {
        nextToken();
        tokens.add(String.valueOf(c));
    }

    /**
     * Trims the current token.
     */
    public void trimToken() {
        currentToken = currentToken.trim();
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }

    public String getCurrentToken() {
        return currentToken;
    }
}