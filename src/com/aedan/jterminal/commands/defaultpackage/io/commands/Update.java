package com.aedan.jterminal.commands.defaultpackage.io.commands;

import com.aedan.jterminal.environment.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/16/2016.
 * <p>
 * Default Command.
 */

public class Update extends Command {

    public Update() {
        super("update");
        this.properties[0] = "Updates the JTerminal source to the most recent version.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        output.println("Update command is currently not implemented.");

//        File dir = directory.getPath(args[1]);
//        dir = directory.getPath(dir.getAbsolutePath() + "/com/aedan/jterminal/");
//
//        if (dir.exists()) {
//            try {
//                FileUtils.removeDirectory(dir);
//            } catch (FileUtils.FileIOException e) {
//                throw new CommandHandler.CommandHandlerException(e.getMessage());
//            }
//        } else {
//            throw new CommandHandler.CommandHandlerException("No installation found at " + dir.getAbsolutePath());
//        }
//        try {
//            URL website = new URL("https://github.com/Ukn0wnSoldure/JTerminal/archive/master.zip");
//            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
//            FileOutputStream fos = new FileOutputStream("JTerminal.zip");
//            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//            fos.close();
//
//            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("JTerminal.zip"));
//            ZipEntry zipEntry = zipInputStream.getNextEntry();
//            for (int i = 0; i < 4; i++) {
//                zipInputStream.getNextEntry();
//            }
//            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
//                if (zipEntry.isDirectory()) {
//                    FileUtils.createDirectory(new File(dir + zipEntry.getName().substring(40)));
//                } else {
//                    FileOutputStream fileOutputStream = new FileOutputStream(dir + zipEntry.getName().substring(40));
//                    int n;
//                    byte[] buf = new byte[1024];
//                    while ((n = zipInputStream.read(buf, 0, 1024)) > -1)
//                        fileOutputStream.write(buf, 0, n);
//                }
//            }
//            zipInputStream.close();
//        } catch (Exception e) {
//            try {
//                FileUtils.removeFile(new File("JTerminal.zip"));
//            } catch (FileUtils.FileIOException e1) {
//                throw new CommandHandler.CommandHandlerException(e.getMessage() + " and " + e1.getMessage());
//            }
//            throw new CommandHandler.CommandHandlerException(e.getMessage());
//        }
    }

}
