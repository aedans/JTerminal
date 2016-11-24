package com.aedan.jterminal.environment.startup;

import com.aedan.argparser.ArgumentParser;
import com.aedan.argparser.ParseResult;
import com.aedan.jterminal.environment.Directory;
import com.aedan.jterminal.environment.Environment;

import java.nio.file.Paths;

/**
 * Created by Aedan Smith.
 */

public class SetDirectory implements StartupArgument {
    @Override
    public void addTo(ArgumentParser argumentParser) {
        argumentParser.addKey("dir");
    }

    @Override
    public void handle(Environment environment, ParseResult parseResult) throws Exception {
        String path = parseResult.getKeyValue("dir");
        if (path != null)
            ((Directory) environment.getEnvironmentVariable("DIR")).setPath(Paths.get(path));
    }
}
