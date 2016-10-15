package com.aedan.jterminal.environment;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Created by Aedan Smith on 9/6/2016.
 * <p>
 * Class containing the Environment of the JTerminal.
 */

public class Environment {

    /**
     * The List of Commands in the Environment.
     */
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * The List of CommandFormats in the Environment.
     */
    private ArrayList<CommandFormat> commandFormats = new ArrayList<>();

    /**
     * The HashMap of environment variables in the Environment.
     */
    private HashMap<String, Supplier<String>> environmentVariables = new HashMap<>();

    /**
     * The HashMap of global variables in the Environment.
     */
    private HashMap<String, String> globalVariables = new HashMap<>();

    /**
     * The Directory of the Environment.
     */
    private Directory directory;

    /**
     * The CommandHandler for the Environment.
     */
    private CommandHandler commandHandler;

    /**
     * Default Environment constructor.
     *
     * @param commandPackages The List of CommandPackages for the Environment.
     */
    public Environment(CommandInput commandInput, CommandOutput commandOutput, CommandPackage... commandPackages) {
        this.commandHandler = new CommandHandler(this, commandInput, commandOutput);
        for (CommandPackage c : commandPackages) {
            this.addCommandPackage(c);
        }
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            environmentVariables.put(envName, () -> env.get(envName));
        }
        this.environmentVariables.put("DIR", this.directory = new Directory());
    }

    /**
     * Adds a GlobalVariable to the Environment.
     *
     * @param name The name of the variable.
     * @param value The value of the variable.
     */
    public void addGlobalVariable(String name, String value) {
        removeGlobalVariable(name);
        globalVariables.put(name, value);
    }

    /**
     * Removes a GlobalVariable from the Environment.
     *
     * @param name The name of the GlobalVariable to remove.
     */
    public void removeGlobalVariable(String name) {
        globalVariables.remove(name);
    }

    /**
     * Adds a CommandPackage to the Environment.
     *
     * @param commandPackage The CommandPackage to add.
     */
    public void addCommandPackage(CommandPackage commandPackage) {
        commandPackage.addCommands(this);
    }

    /**
     * Adds a CommandFormat to the Environment.
     *
     * @param commandFormat The CommandFormat to add.
     */
    public void addCommandFormat(CommandFormat commandFormat) {
        commandFormats.add(commandFormat);
    }

    /**
     * Adds a Command to the Environment.
     *
     * @param command The Command to add.
     */
    public void addCommand(Command command) {
        commands.add(command);
        commands.sort((o1, o2) -> o2.getIdentifier().length() - o1.getIdentifier().length());
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

    public HashMap<String, Supplier<String>> getEnvironmentVariables() {
        return environmentVariables;
    }

    public HashMap<String, String> getGlobalVariables() {
        return globalVariables;
    }

}
