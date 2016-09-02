package com.aedan.jcmd;

import com.aedan.jtermgui.JTDisplay;
import com.aedan.jtermgui.JTPrintStream;
import com.aedan.jterminal.JTerminal;
import com.aedan.jterminal.commands.defaultpackage.DefaultPackage;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Main class for JCMD.
 */

public class JCMD {

    /**
     * Main function to launch JCMD from JTerminal.jar.
     */
    public static void main(String[] args){
        JTDisplay display = new JTDisplay(1280, 716);
        new JTerminal(
                "-directory \"C:/Users/Aedan Smith/Desktop\"",
                display.getJTKeyListener(),
                new CommandOutput(new JTPrintStream(display.getJTStringList())),
                new DefaultPackage()
        ).run();
    }

}
