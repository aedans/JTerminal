package com.aedan.jterminal.environment.startup;

import acklib.utils.misc.ArgumentParser;
import com.aedan.jterminal.environment.Environment;

import java.nio.file.Paths;

/**
 * Created by Aedan Smith.
 */

public class SetDirectory implements StartupArgument {

    @Override
    public void handle(Environment environment, ArgumentParser parser) throws Exception {
        String path = parser.getString("directory");
        if (path != null)
            environment.setDirectoryPath(Paths.get(path));
    }
}
