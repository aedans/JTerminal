package com.aedan.jterminal.environment;

import com.aedan.argparser.ArgumentParser;
import com.aedan.argparser.ParseResult;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.CommandHandler;
import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.startup.Execute;
import com.aedan.jterminal.environment.startup.SetDirectory;
import com.aedan.jterminal.environment.startup.StartupArgument;
import com.aedan.jterminal.input.BufferedReaderInput;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.PrintStreamOutput;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;
import com.alibaba.fastjson.JSON;

import java.nio.file.Path;
import java.util.*;

/**
 * Created by Aedan Smith on 9/6/2016.
 * <p>
 * Class containing the Environment of the JTerminal.
 */

public class Environment {
    private ArrayList<Command> commands = new ArrayList<>();

    private HashMap<String, Object> environmentVariables = new HashMap<>();

    private HashMap<String, Object> globalVariables = new HashMap<>();

    private Directory directory;

    private EnvironmentPath path;

    private CommandHandler commandHandler;

    private CommandInput input;

    private CommandOutput output;

    /**
     * Default Environment constructor.
     *
     * @param args             The arguments for the Environment.
     * @param input            The CommandInput for the Environment.
     * @param output           The CommandOutput for the Environment.
     * @param commandHandler   The CommandHandler for the Environment.
     * @param startupArguments The StartupArguments for the Environment.
     * @param packages         The List of Packages for the Environment.
     * @throws Exception If there was an error handling the arguments.
     */
    public Environment(String[] args, CommandInput input, CommandOutput output, CommandHandler commandHandler,
                       StartupArgument[] startupArguments, Package... packages) throws Exception {
        if (args == null)
            args = new String[]{};
        if (input == null)
            input = new BufferedReaderInput();
        if (output == null)
            output = new PrintStreamOutput();
        if (commandHandler == null)
            commandHandler = new CommandHandler(this);
        if (startupArguments == null)
            startupArguments = new StartupArgument[]{new SetDirectory(), new Execute()};
        if (packages == null)
            packages = new Package[]{new DefaultPackage()};

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            environmentVariables.put(envName, env.get(envName));
        }
        this.environmentVariables.put("DIR", this.directory = new Directory());
        this.environmentVariables.put("PATH", this.path = new EnvironmentPath(directory));
        this.environmentVariables.put("VARS", this.globalVariables);
        this.environmentVariables.put("ENVVARS", this.environmentVariables);
        this.environmentVariables.put("ENV", this);
        this.environmentVariables.put("COMMANDS", this.commands);
        this.environmentVariables.put("IN", input);
        this.environmentVariables.put("OUT", output);
        this.environmentVariables.put("CMDHANDLER", commandHandler);
        this.environmentVariables.put("CARET", "+ %DIR% \\>");

        this.input = input;
        this.output = output;
        this.commandHandler = commandHandler;
        for (Package p : packages) {
            this.addPackage(p);
        }

        ArgumentParser parser = new ArgumentParser();
        for (StartupArgument startupArgument : startupArguments) {
            startupArgument.addTo(parser);
        }
        ParseResult parseResult = parser.parse(args);
        for (StartupArgument startupArgument : startupArguments) {
            try {
                startupArgument.handle(this, parseResult);
            } catch (Exception e) {
                System.out.println("Exception in initialization: " + e.getMessage());
            }
        }
    }

    public void addGlobalVariable(String name, Object value) {
        removeGlobalVariable(name);
        globalVariables.put(name, value);
    }

    public void removeGlobalVariable(String name) {
        globalVariables.remove(name);
    }

    public void addCommand(Command command) {
        commands.add(command);
        commands.sort((o1, o2) -> o2.getIdentifier().length() - o1.getIdentifier().length());
        environmentVariables.put(command.getIdentifier(), command);
    }

    public void addPackage(Package aPackage) {
        aPackage.addTo(this);
    }

    public void removeCommand(String s) {
        LinkedList<Command> toRemove = new LinkedList<>();
        for (Command c : commands) {
            if (Objects.equals(c.getIdentifier(), s)) {
                toRemove.add(c);
            }
        }
        commands.removeAll(toRemove);
    }

    public void setDirectoryPath(Path path) {
        try {
            this.directory.setPath(path);
        } catch (Directory.DirectoryChangeException e) {
            e.printStackTrace();
        }
    }

    public Directory getDirectory() {
        return directory;
    }

    public EnvironmentPath getPath() {
        return path;
    }

    public CommandInput getInput() {
        return input;
    }

    public void setInput(CommandInput input) {
        this.input = input;
    }

    public CommandOutput getOutput() {
        return output;
    }

    public void setOutput(CommandOutput output) {
        this.output = output;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public HashMap<String, Object> getEnvironmentVariables() {
        return environmentVariables;
    }

    public HashMap<String, Object> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(HashMap<String, Object> globalVariables) {
        this.globalVariables = globalVariables;
    }

    @Override
    public String toString() {
        return "\"environment\":" + JSON.toJSONString(this, true);
    }
}
