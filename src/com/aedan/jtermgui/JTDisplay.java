package com.aedan.jtermgui;

import javax.swing.*;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Display for the JTerminal GUI.
 */

public class JTDisplay extends JFrame {

    /**
     * The StringList for the Display to render.
     */
    private JTStringList stringList = new JTStringList(this);

    /**
     * The KeyListener for the Display.
     */
    private JTKeyListener keyListener = new JTKeyListener(stringList);

    /**
     * Default JTDisplay constructor.
     *
     * @param xRes The JTDisplay x resolution.
     * @param yRes The JTDisplay y resolution.
     */
    public JTDisplay(int xRes, int yRes){
        this.setTitle("JTerminal");
        this.add(stringList);
        this.addKeyListener(keyListener);
        this.addMouseWheelListener(stringList);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(xRes, yRes);
        this.setVisible(true);
        this.requestFocus();
    }

    public JTKeyListener getJTKeyListener(){
        return keyListener;
    }

    public JTStringList getJTStringList() {
        return stringList;
    }

}
