package com.aedan.jterminal.output;

import com.alibaba.fastjson.JSON;

/**
 * Created by Aedan Smith.
 */

public class StringOutput implements CommandOutput {
    private String string = "";
    private boolean closed = false;

    @Override
    public void print(String s) {
        if (!closed)
            this.string += s;
    }

    @Override
    public void close() {
        closed = true;
    }

    public void flush() {
        string = "";
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "StringOutput:" + JSON.toJSONString(this, true);
    }
}
