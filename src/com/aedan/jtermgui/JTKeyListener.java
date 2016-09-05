package com.aedan.jtermgui;

import com.aedan.jterminal.input.CommandInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * KeyListener and CommandInput for the JTerminal GUI.
 */

class JTKeyListener implements KeyListener, CommandInput {

    /**
     * The List of recent Commands given to the CommandHandler.
     */
    private ArrayList<String> recentCommands = new ArrayList<>();

    /**
     * The current selected index of recent Commands.
     */
    private int commandIndex = 0;

    /**
     * The StringList for the KeyListener to modify.
     */
    private final JTStringList stringList;

    /**
     * If the enter key is currently down.
     */
    private boolean isEnterDown = false;

    /**
     * Default JTKeyListener constructor.
     *
     * @param stringList The StringList for the KeyListener to modify.
     */
    JTKeyListener(JTStringList stringList) {
        this.stringList = stringList;
    }

    @Override
    public String nextLine() {
        while (true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isEnterDown) {
                try {
                    isEnterDown = false;
                    recentCommands.add(stringList.currentString);
                    commandIndex = recentCommands.size();
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
            } catch (StringIndexOutOfBoundsException ignored) {
            }
        } else {
            stringList.currentString += e.getKeyChar();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (commandIndex != 0) {
                commandIndex--;
                stringList.currentString = recentCommands.get(commandIndex);
                stringList.snapToInput();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (commandIndex < recentCommands.size()-1) {
                commandIndex++;
                stringList.currentString = recentCommands.get(commandIndex);
                stringList.snapToInput();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
