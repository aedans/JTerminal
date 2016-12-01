package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.jterm.parsers.ConditionalParser;
import com.aedan.jterminal.jterm.parsers.FunctionCallParser;
import com.aedan.jterminal.jterm.parsers.IfStatementParser;
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
        environment.addCommand(new CallFunction(jTermRuntime));
//        environment.addCommand(new IfElseCommand());
//        environment.addCommand(new WhileCommand());
//        environment.addCommand(new IfNotEqual());
//        environment.addCommand(new LessThan());
//        environment.addCommand(new GreaterThan());
        environment.addCommand(new Return());
        environment.addCommand(new If());
        environment.addCommand(new IfEquals());
        environment.getCommandHandler().getParser().addParser(new FunctionCallParser(jTermRuntime));
        environment.getCommandHandler().getParser().addParser(new ConditionalParser(jTermRuntime));
        environment.getCommandHandler().getParser().addParser(new IfStatementParser(jTermRuntime));
    }

    @Override
    public String toString() {
        return "JTermPackage";
    }
}
