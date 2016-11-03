package com.aedan.jterminal.environment.startup;

import com.aedan.argparser.ArgumentParser;
import com.aedan.argparser.ParseResult;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith.
 */

public class ExecuteJTermFile implements StartupArgument {

    @Override
    public void addTo(ArgumentParser argumentParser) {
        argumentParser.addKey("exec");
    }

    @Override
    public void handle(Environment environment, ParseResult parseResult) throws Exception {
        String command = parseResult.getKeyValue("exec");
        environment.getCommandHandler().handleInput(command);
    }
}
