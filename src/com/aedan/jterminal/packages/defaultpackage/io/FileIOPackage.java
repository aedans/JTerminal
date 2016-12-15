package com.aedan.jterminal.packages.defaultpackage.io;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.packages.defaultpackage.io.commands.*;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default io Commands.
 */

public class FileIOPackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.setEnvironmentVariable("DIR", new Directory());
        environment.addCommand(new Concatenate());
        environment.addCommand(new ChangeDirectory());
        environment.addCommand(new ListSubdirectories());
        environment.addCommand(new MakeDirectory());
        environment.addCommand(new RemoveDirectory());
        environment.addCommand(new RemoveFile());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
