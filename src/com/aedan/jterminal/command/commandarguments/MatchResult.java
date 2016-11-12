package com.aedan.jterminal.command.commandarguments;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for returning the result of CommandArgumentList matches.
 */

public enum MatchResult {
    MORE_ARGS("More arguments given than required"),
    LESS_ARGS("Less arguments given than required"),
    INCORRECT_ARGS("Incorrect arguments given"),
    CORRECT_ARGS("Correct arguments given");

    private String message;

    MatchResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailedMessage() {
        return message + " for command %s (required %i, given %i)";
    }

    @Override
    public String toString() {
        return "MatchResult(" + message + ")";
    }
}
