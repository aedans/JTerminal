package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Aedan Smith on 8/15/2016.
 */

class Open extends Command{

    Open() {
        super("open -s", "open", 1, "Opens a given file with the default application");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            try {
                Desktop.getDesktop().open(directory.getFile(getArgValues(in)[0]));
            } catch (IOException e) {
                Desktop.getDesktop().edit(directory.getFile(getArgValues(in)[0]));
            }
        } catch (Exception e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
