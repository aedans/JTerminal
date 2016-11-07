package com.aedan.jterminal.jterm.bashcommandpackage;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;

/**
 * Created by Aedan Smith.
 */

public class JTermPackage implements Package {
    private JTermRuntime bashRuntime;

    public JTermPackage(JTermRuntime bashRuntime) {
        this.bashRuntime = bashRuntime;
    }

    @Override
    public void addTo(Environment environment) {
        environment.addPackage(new DefaultPackage());
        environment.addCommand(new CallFunction(bashRuntime));
        environment.addCommandFormat(new InitializeVariableFormat());
    }
}
