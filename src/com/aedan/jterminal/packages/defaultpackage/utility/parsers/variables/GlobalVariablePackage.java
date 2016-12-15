package com.aedan.jterminal.packages.defaultpackage.utility.parsers.variables;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.Environment;

import java.util.HashMap;

/**
 * Created by Aedan Smith.
 */

public class GlobalVariablePackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.setEnvironmentVariable("VARS", new HashMap<String, Object>());
        environment.getCommandHandler().getParser().addParser(new GetGlobalVariableParser(environment));
    }
}
