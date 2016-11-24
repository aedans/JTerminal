package com.aedan.jterminal.packages.defaultpackage.utility.parsers.variables;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;

import java.util.HashMap;

/**
 * Created by Aedan Smith.
 */

public class GlobalVariablePackage implements Package {
    @Override
    public void addTo(Environment environment) {
        HashMap<String, Object> variables = new HashMap<>();
        environment.setEnvironmentVariable("VARS", variables);
        environment.getCommandHandler().getParser().addParser(new GetGlobalVariableParser(variables));
        environment.getCommandHandler().getParser().addParser(new SetGlobalVariableParser(variables));
    }
}
