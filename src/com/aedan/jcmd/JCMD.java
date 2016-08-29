package com.aedan.jcmd;

import com.aedan.jtermgui.JTDisplay;
import com.aedan.jtermgui.JTPrintStream;
import com.aedan.jterminal.JTerminal;
import com.aedan.jterminal.commands.default_package.DefaultPackage;
import com.aedan.jterminal.output.CommandOutput;

import java.awt.*;
import java.util.Arrays;

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
        JTerminal jTerminal = new JTerminal(
                "",
                display.getJTKeyListener(),
                new CommandOutput(new JTPrintStream(display.getJTStringList())),
                new DefaultPackage()
        );

        jTerminal.run();
    }

}
