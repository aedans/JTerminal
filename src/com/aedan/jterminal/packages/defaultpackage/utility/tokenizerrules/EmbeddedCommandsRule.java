package com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.tokenizer.TokenList;
import com.aedan.jterminal.input.tokenizer.TokenizerRule;
import com.aedan.jterminal.output.PrintStreamOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Aedan Smith on 10/10/2016.
 *
 * TokenizerRule for embedded command.
 */

public class EmbeddedCommandsRule implements TokenizerRule {

    private Environment environment;

    /**
     * Default EmbeddedCommandsRule constructor.
     */
    public EmbeddedCommandsRule(Environment environment) {
        this.environment = environment;
    }

    @Override
    public char getIdentifier() {
        return '[';
    }

    @Override
    public int process(TokenList tokenList, String s, int i) throws CommandHandler.CommandHandlerException {
        tokenList.nextToken();
        String command = "";
        int j = i + 1, depth = 1;
        label:
        for (; true; j++) {
            if (j >= s.length())
                throw new CommandHandler.CommandHandlerException("Could not find matching }", this);
            switch (s.charAt(j)) {
                case '\\':
                    j++;
                    command += s.charAt(j);
                    break;
                case '[':
                    depth++;
                    command += '[';
                    break;
                case ']':
                    depth--;
                    if (depth == 0)
                        break label;
                default:
                    command += s.charAt(j);
                    break;
            }
        }
        environment.getCommandHandler().handleInput(new PrintStreamOutput(new PrintStream(new OutputStream() {
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