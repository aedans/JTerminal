package com.aedan.jterminal.input.tokenizer;

import com.aedan.jterminal.command.commandhandler.CommandHandler;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * Class for creating Tokenizer rules.
 */

public interface TokenizerRule {

    /**
     * @return The character upon which to call the TokenizerRule.
     */
    char getIdentifier();

    /**
     * Processes a TokenList.
     *
     * @param tokenList The TokenList to process.
     * @param s The original String.
     * @param i The current index of the Tokenizer.
     * @return The index for the Tokenizer to resume.
     * @throws CommandHandler.CommandHandlerException If there is an error parsing the TokenList.
     */
    int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException;
}
