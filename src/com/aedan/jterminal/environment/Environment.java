package com.aedan.jterminal.environment;

import com.aedan.argparser.ArgumentParser;
import com.aedan.argparser.ParseResult;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.CommandFormat;
import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.startup.Execute;
import com.aedan.jterminal.environment.startup.SetDirectory;
import com.aedan.jterminal.environment.startup.StartupArgument;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.input.ScannerInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.PrintStreamOutput;

import java.nio.file.Path;
import java.util.*;

/**
 * Created by Aedan Smith on 9/6/2016.
 * <p>
 * Class containing the Environment of the JTerminal.
 */

public class Environment {

    private ArrayList<Command> commands = new ArrayList<>();

    private ArrayList<CommandFormat> commandFormats = new ArrayList<>();

    private HashMap<String, Object> environmentVariables = new HashMap<>();

    private HashMap<String, Object> globalVariables = new HashMap<>();

    private Directory directory;

    private CommandHandler commandHandler;

    /**
     * Default Environment constructor.
     *
     * @param args     The arguments for the Environment.
     * @param packages The List of Packages for the Environment.
     * @throws Exception If there was an error handling the arguments.
     */
    public Environment(String args[], Package... packages) throws Exception {
        this(args, new ScannerInput(), new PrintStreamOutput(System.out), new StartupArgument[]{
                new SetDirectory(),
                new Execute()
        }, packages);
    }

    /**
     * Default Environment constructor.
     *
     * @param args The arguments for the Environment.
     * @param commandInput The CommandInput for the Environment to read from.
     * @param commandOutput The CommandOutput for the Environment to print to.
     * @param packages The List of Packages for the Environment.
     * @throws Exception If there was an error handling the arguments.
     */
    public Environment(String[] args, CommandInput commandInput, CommandOutput commandOutput, Package... packages)
            throws Exception {
        this(args, commandInput, commandOutput, new StartupArgument[]{
                new SetDirectory(),
                new Execute()
        }, packages);
    }

    /**
     * Default Environment constructor.
     *
     * @param args The arguments for the Environment.
     * @param commandInput The CommandInput for the Environment to read from.
     * @param commandOutput The CommandOutput for the Environment to print to.
     * @param packages The List of Packages for the Environment.
     * @throws Exception If there was an error handling the arguments.
     */
    public Environment(String[] args, CommandInput commandInput, CommandOutput commandOutput, StartupArgument[] arguments,
                       Package... packages)
            throws Exception {
        this.commandHandler = new CommandHandler(this, commandInput, commandOutput);
        for (Package p : packages) {
            this.addPackage(p);
        }
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            environmentVariables.put(envName, env.get(envName));
        }
        this.environmentVariables.put("DIR", this.directory = new Directory());

        ArgumentParser parser = new ArgumentParser();
        for (StartupArgument startupArgument : arguments) {
            startupArgument.addTo(parser);
        }
        ParseResult parseResult = parser.parse(args);
        for (StartupArgument startupArgument : arguments) {
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
    }

    public void addCommandFormat(CommandFormat commandFormat) {
        commandFormats.add(0, commandFormat);
    }

    public void addPackage(Package aPackage) {
        aPackage.addTo(this);
    }

    public void removeCommand(String s){
        LinkedList<Command> toRemove = new LinkedList<>();
        for (Command c : commands){
            if (Objects.equals(c.getIdentifier(), s)){
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

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public ArrayList<CommandFormat> getCommandFormats() {
        return commandFormats;
    }

    public HashMap<String, Object> getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(HashMap<String, Object> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public HashMap<String, Object> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(HashMap<String, Object> globalVariables) {
        this.globalVariables = globalVariables;
    }
}
