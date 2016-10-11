package com.aedan.jterminal.commandpackages.defaultpackage.utility.commandformats;

import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default CommandFormat.
 */

public class AddGlobalVariable implements CommandFormat {

    public AddGlobalVariable(Environment environment){
        environment.getCommandHandler().getTokenizer().addReservedChar('=');
    }

    @Override
    public boolean matches(List<String> tokens) throws CommandHandler.CommandHandlerException {
        return tokens.contains("=");
    }

    @Override
    public void handleInput(Environment environment, CommandInput input, CommandOutput output, List<String> tokens)
            throws CommandHandler.CommandHandlerException {
        int setIndex = tokens.indexOf("=");

        final String[] varValue = new String[]{""};
        CommandOutput varOutput = new CommandOutput(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                varValue[0] += (char) b;
            }
        }));
        environment.getCommandHandler().handleInput(input, varOutput, tokens.subList(setIndex+1, tokens.size()));

        List<String> varTokens = tokens.subList(0, setIndex);
        String varName = "";
        for (String s : varTokens)
            varName += s;

        environment.addGlobalVariable(varName, varValue[0].trim());
    }

}
