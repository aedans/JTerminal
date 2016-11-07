package com.aedan.jterminal.bash.bashcommandpackage;

import com.aedan.jterminal.bash.BashRuntime;
import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;

/**
 * Created by Aedan Smith.
 */

public class BashPackage implements Package {
    private BashRuntime bashRuntime;

    public BashPackage(BashRuntime bashRuntime) {
        this.bashRuntime = bashRuntime;
    }

    @Override
    public void addTo(Environment environment) {
        environment.addPackage(new DefaultPackage());
        environment.addCommand(new CallFunction(bashRuntime));
        environment.addCommandFormat(new InitializeVariableFormat());
    }
}
