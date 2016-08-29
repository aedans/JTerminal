package com.aedan.jtermgui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * PrintStream to print to the JTDisplay
 */

public class JTPrintStream extends PrintStream {

    /**
     * Default JTPrintStream constructor.
     *
     * @param stringList The StringList to write to.
     */
    public JTPrintStream(JTStringList stringList) {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                stringList.lines += (char) b;
            }
        });
    }

}
