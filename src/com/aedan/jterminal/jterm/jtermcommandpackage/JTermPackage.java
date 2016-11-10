package com.aedan.jterminal.jterm.jtermcommandpackage;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;

/**
 * Created by Aedan Smith.
 */

public class JTermPackage implements Package {
    private JTermRuntime jTermRuntime;

    public JTermPackage(JTermRuntime jTermRuntime) {
        this.jTermRuntime = jTermRuntime;
    }

    @Override
    public void addTo(Environment environment) {
        environment.addPackage(new DefaultPackage());
        environment.addCommand(new CallFunction(jTermRuntime));
        environment.addCommand(new IfCommand());
        environment.addCommand(new IfElseCommand());
        environment.addCommand(new WhileCommand());
        environment.addCommand(new IfEqual());
        environment.addCommand(new IfNotEqual());
        environment.addCommand(new LessThan());
        environment.addCommand(new GreaterThan());
    }
}
