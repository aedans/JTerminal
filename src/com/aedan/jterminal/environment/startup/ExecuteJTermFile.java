package com.aedan.jterminal.environment.startup;

import acklib.utils.misc.ArgumentParser;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith.
 */

public class ExecuteJTermFile implements StartupArgument {

    @Override
    public void handle(Environment environment, ArgumentParser parser) throws Exception {
        String path = parser.getString("exec");
        if (path != null) {
            environment.getCommandHandler().handleInput("exec \"" + path + "\"");
        }
    }
}
