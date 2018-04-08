package com.game.lines.entity;

import javax.swing.*;
import java.awt.*;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class Cell extends JLabel {

    private int Xx; // coordinate X
    private int Yy; // coordinate Y
    private int clickCount; // click counter for implementation of game logic

    private Picture picture; // image icon of this cell

    public void setXx(int xx) {
        this.Xx = xx;
    }

    public int getXx() {
        return Xx;
    }

    public void setYy(int yy) {
        this.Yy = yy;
    }

    public int getYy() {
        return Yy;
    }

    public void setDefaultBorder() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void setRedBorder() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    public void setClickCount(int clickCount) {
        if (this.containsImage())
            this.clickCount += clickCount % 3;
        else this.clickCount = 0;
    }

    public int getClickCount() {
        if (clickCount == 3) {
            clickCount = 1;
        }
        return clickCount % 3;
    }

    public String getPictureColor() {
        String iconAbsolutePath = this.getIcon().toString();
        String[] pathArray = iconAbsolutePath.split("/");
        String[] fileName = pathArray[pathArray.length - 1].split("[-]");
        return fileName[0];
    }

    // cell with or without image
    public boolean containsImage() {
        return this.getIcon() != null;
    }

    @Override
    public boolean equals(Object obj) {
        boolean value = false;
        if (!(obj instanceof JButton)) {
            Cell other = (Cell) obj;
            value = this.getXx() == other.getXx() && this.getYy() == other.getYy();
        }
        return value;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime + result + this.Xx;
        result = prime + result + this.Yy;
        return result;
    }
}