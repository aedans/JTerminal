package com.aedan.jterminal.environment;

import com.aedan.jterminal.output.StringOutput;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for managinc the Environment's path.
 */

public class EnvironmentPath {
    private Directory directory;

    private HashMap<String, File> files = new HashMap<>();

    public EnvironmentPath(Directory directory) {
        this.directory = directory;
    }

    public void add(File file) {
        files.put(file.getName(), file);
    }

    public File get(String fileName) {
        File file = files.get(fileName);
        if (file != null) {
            return file;
        } else {
            return directory.getFile(fileName);
        }
    }

    @Override
    public String toString() {
        StringOutput stringOutput = new StringOutput();
        files.forEach((s, file) -> stringOutput.println(file));
        return stringOutput.getString().trim();
    }
}
