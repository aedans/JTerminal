package com.aedan.jterminal.jterm.bashcommandpackage;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.output.CommandOutput;
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
        environment.addCommand(new WhileCommand());
        environment.addCommandFormat(new InitializeVariableFormat());

        environment.addCommand(new Command("false") {
            @Override
            public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
                    throws CommandHandler.CommandHandlerException {
                output.println("false");
            }
        });
        environment.addCommand(new Command("true") {
            @Override
            public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
                    throws CommandHandler.CommandHandlerException {
                output.println("true");
            }
        });
    }
}
