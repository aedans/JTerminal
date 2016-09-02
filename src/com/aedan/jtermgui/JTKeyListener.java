package com.aedan.jtermgui;

import com.aedan.jterminal.input.CommandInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Aedan Smith on 8/28/2016.
 *
 * KeyListener and CommandInput for the JTerminal GUI.
 */

class JTKeyListener implements KeyListener, CommandInput {

    /**
     * If the enter key is currently down.
     */
    private boolean isEnterDown = false;

    /**
     * The StringList for the KeyListener to modify.
     */
    private JTStringList stringList;

    /**
     * Default JTKeyListener constructor.
     *
     * @param stringList The StringList for the KeyListener to modify.
     */
    JTKeyListener(JTStringList stringList){
        this.stringList = stringList;
    }

    @Override
    public String nextLine() {
        while (true){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isEnterDown) {
                try {
                    isEnterDown = false;
                    stringList.lines += stringList.currentString + "\n";
                    stringList.numLines++;
                    return stringList.currentString;
                } finally {
                    stringList.currentString = "";
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        stringList.snapToInput();
        if (e.getKeyChar() == '\n') {
            isEnterDown = true;
        } else if (e.getKeyChar() == '\b') {
            try {
                this.stringList.currentString = stringList.currentString.substring(0, stringList.currentString.length() - 1);
            } catch (StringIndexOutOfBoundsException ignored) {}
        } else {
            stringList.currentString += e.getKeyChar();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
