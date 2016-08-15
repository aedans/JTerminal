package com.aedan.jterminal.commands.default_commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Default Command.
 */

public class ChangeDirectory extends Command {

    public ChangeDirectory() {
        super("cd -s", "cd", 1, "Changes the active directory.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        String dir = getArgValues(in)[0];
        if (Objects.equals(dir, "..")){
            directory.goToSuperDirectory();
        } else if (Objects.equals(dir, ".")){
            // Do nothing.
        } else if (dir.matches("\\w:.+|/.+|\\\\.+")) {
            directory.goToDirectory(dir);
        } else {
            Path p = Paths.get(directory.toString(), dir).toAbsolutePath();
            directory.goToDirectory(p.toString());
        }
    }

}
