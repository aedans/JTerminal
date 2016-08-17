package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.utils.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Aedan Smith on 8/16/2016.
 *
 * Default Command.
 */

public class Update extends Command {

    public Update() {
        super("update -s", "update", 1, "Updates the JTerminal source to the most recent version.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            URL website = new URL("https://github.com/Ukn0wnSoldure/JTerminal/archive/master.zip");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("JTerminal.zip");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();

            File dir = directory.getFile(getArgValues(in)[0]);
            dir = directory.getFile(dir.getAbsolutePath() + "/com/aedan/jterminal/");

            FileUtils.removeDirectory(dir);

            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("JTerminal.zip"));
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            for (int i = 0; i < 4; i++) {
                zipInputStream.getNextEntry();
            }
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()){
                    FileUtils.createDirectory(new File(dir + zipEntry.getName().substring(40)));
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream(dir + zipEntry.getName().substring(40));
                    int n;
                    byte[] buf = new byte[1024];
                    while ((n = zipInputStream.read(buf, 0, 1024)) > -1)
                        fileOutputStream.write(buf, 0, n);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
