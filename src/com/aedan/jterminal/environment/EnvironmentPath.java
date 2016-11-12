package com.aedan.jterminal.environment;

import com.alibaba.fastjson.JSON;

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

    public Directory getDirectory() {
        return directory;
    }

    public HashMap<String, File> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "\"environmentPath\":" + JSON.toJSONString(this, true);
    }
}
