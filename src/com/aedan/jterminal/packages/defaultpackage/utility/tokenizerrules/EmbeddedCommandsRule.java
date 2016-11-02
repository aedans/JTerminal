package com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;
import com.aedan.jterminal.output.CommandOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for embedded command.
 */

public class EmbeddedCommandsRule implements TokenizerRule {

    /**
     * The CommandHandler to handle the embedded command.
     */
    private CommandHandler commandHandler;

    /**
     * Default EmbeddedCommandsRule constructor.
     *
     * @param commandHandler The CommandHandler to handle the embedded command.
     */
    public EmbeddedCommandsRule(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public char getIdentifier() {
        return '{';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
        String command = "";
        int j = i + 1, depth = 1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new CommandHandler.CommandHandlerException("Could not find matching }");
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    command += s.charAt(j);
                    break;
                case '{':
                    depth++;
                    command += '{';
                    break;
                case '}':
                    depth--;
                    if (depth == 0)
                        break label;
                default:
                    command += s.charAt(j);
                    break;
            }
        }
        commandHandler.handleInput(new CommandOutput(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                tokenList.append((char) b);
            }
        })), command);
        tokenList.trimToken();
        tokenList.nextToken();
        return j;
    }
}