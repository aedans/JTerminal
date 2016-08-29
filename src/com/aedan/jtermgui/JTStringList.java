package com.aedan.jtermgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * Class for rendering Strings to the JTDisplay.
 */

class JTStringList extends JComponent implements MouseWheelListener {

    /**
     * The lines for the StringList to render.
     */
    String lines = "";

    /**
     * The current StringList string.
     */
    String currentString = "";

    /**
     * The current font size for the StringList.
     */
    private int currentFontSize = 18;

    /**
     * The amount to translate the font Y.
     */
    private int fontTransY = -3;

    /**
     * The current font for the StringList.
     */
    private Font currentFont = new Font("Monospaced", Font.PLAIN, currentFontSize);

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(255, 255, 255));
        g.setFont(currentFont);
        String[] lines = (this.lines + "\000").split("\n");
        int i;
        for (i = 0; i < lines.length-1; i++) {
            g.drawString(lines[i], 5, currentFontSize+(i*currentFontSize)+fontTransY);
        }
        g.drawString(lines[i] + currentString, 5, currentFontSize+(i*currentFontSize)+fontTransY);

        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        fontTransY -= e.getWheelRotation()*currentFontSize*2;
        if (fontTransY > -3) fontTransY = -3;
    }

}
