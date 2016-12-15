package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.Environment;

/**
 * Created by Aedan Smith.
 */

public class ReflectionPackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.getCommandHandler().getParser().addParser(new MethodAccessParser(environment));
        environment.getCommandHandler().getParser().addParser(new ConstructorAccessParser(environment));
    }
}
