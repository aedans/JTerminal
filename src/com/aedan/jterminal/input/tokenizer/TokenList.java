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

    /**
     * The current Token.
     */
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
     * Ends the current token.
     */
    public void nextToken(){
        if (!Objects.equals(currentToken, "")) {
            tokens.add(currentToken);
            currentToken = "";
        }
    }

    /**
     * Adds a token to the TokenList.
     *
     * @param s The token to add.
     */
    public void addToken(String s) {
        nextToken();
        tokens.add(s);
    }

    /**
     * Adds a token to the TokenList.
     *
     * @param c The token to add.
     */
    public void addToken(char c) {
        nextToken();
        tokens.add(String.valueOf(c));
    }

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