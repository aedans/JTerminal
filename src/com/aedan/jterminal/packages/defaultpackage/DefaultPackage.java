package com.aedan.jterminal.packages.defaultpackage;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.executors.ExecutorsPackage;
import com.aedan.jterminal.packages.defaultpackage.io.FileIOPackage;
import com.aedan.jterminal.packages.defaultpackage.math.MathPackage;
import com.aedan.jterminal.packages.defaultpackage.utility.UtilityPackage;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Package containing all Default Packages.
 */

public class DefaultPackage implements Package {

    @Override
    public void addTo(Environment environment) {
        new FileIOPackage().addTo(environment);
        new ExecutorsPackage().addTo(environment);
        new MathPackage().addTo(environment);
        new UtilityPackage().addTo(environment);
    }
}
