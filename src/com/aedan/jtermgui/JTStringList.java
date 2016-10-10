package com.aedan.jtermgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for rendering Strings to the JTDisplay.
 */

class JTStringList extends JComponent implements MouseWheelListener {

    /**
     * The Colors for the StringList to draw.
     */
    public Color fontColor = Color.WHITE, backgroundColor = Color.BLACK;

    /**
     * The lines for the StringList to render.
     */
    String lines = "";

    /**
     * The number of lines currently being displayed.
     */
    int numLines = 0;

    /**
     * The current StringList string.
     */
    private StringBuilder currentString = new StringBuilder();

    /**
     * The index of the cursor.
     */
    private int cursorIndex = 0;

    /**
     * The amount to translate the font Y.
     */
    private int fontTransY = -3;

    /**
     * The current font for the StringList.
     */
    private Font currentFont = new Font("Monospaced", Font.PLAIN, 18);

    /**
     * The current font size for the StringList.
     */
    private int currentFontSize = currentFont.getSize();

    /**
     * The current width of the font.
     */
    private int currentFontWidth;

    /**
     * The current Display for the StringList.
     */
    private JTDisplay jtDisplay;

    /**
     * Default JTStringList constructor
     *
     * @param jtDisplay The Display for the StringList to display to.
     */
    JTStringList(JTDisplay jtDisplay) {
        this.jtDisplay = jtDisplay;
    }

    @Override
    public void paint(Graphics g1) {
        Graphics2D g = ((Graphics2D) g1);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        currentFontWidth =
                (int) currentFont.getStringBounds("0", g.getFontRenderContext()).getMaxX();

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(fontColor);
        g.setFont(currentFont);
        String[] lines = (this.lines + "\000").split("\n");
        int i;
        for (i = 0; i < lines.length - 1; i++) {
            g.drawString(lines[i], 5, currentFontSize + (i * currentFontSize) + fontTransY);
        }
        g.drawString(lines[i] + currentString, 5, currentFontSize + (i * currentFontSize) + fontTransY);
        g.fillRect(
                (int) (lines[i].length() * currentFontWidth + cursorIndex * currentFontWidth - currentFontWidth * .6f),
                (i * currentFontSize) + fontTransY + 3,
                2,
                currentFontSize
        );

        repaint();
    }

    void snapToInput() {
        fontTransY = -currentFontSize * numLines + jtDisplay.getHeight() - 35 - currentFontSize * 2;
        if (fontTransY > -3) fontTransY = -3;
    }

    void incrementCursorIndex() {
        if (cursorIndex < currentString.length())
            cursorIndex++;
    }

    void decrementCursorIndex() {
        if (cursorIndex != 0)
            cursorIndex--;
    }

    void insertCharAtCursor(char c) {
        currentString.insert(cursorIndex, c);
    }

    void removeCurrentStringLastChar() {
        if (currentString.length() > 0)
            currentString.deleteCharAt(cursorIndex - 1);
    }

    public String getCurrentString() {
        return currentString.toString();
    }

    void setCurrentString(String s) {
        currentString = new StringBuilder(s);
        cursorIndex = s.length();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        fontTransY -= e.getWheelRotation() * currentFontSize * 2;
        if (fontTransY > -3) fontTransY = -3;
    }

}
