package com.game.lines.entity;

import javax.swing.*;
import java.awt.*;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class Cell extends JLabel {

    private int Xx; // Положение ячейки по оси координат X.
    private int Yy; // Положение ячейки по оси координат Y.
    private int clickCount; // Счетчик кликов по ячейке (используется для реализации игровой логики).

    private Picture picture; // Изображение ячейки.

    // Сеттеры и геттеры координат ячейки.
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

    // Метод устанавливает стандартные границы ячейки, без выделения цветом (когда ячейка не активна).
    public void setDefaultBorder() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    // Метод устанавливает границы ячейки, выделяя их красным цветом (когда ячейка активна).
    public void setRedBorder() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    /**
     * Метод устанавливает значение счетчика кликов от 0 до 2.
     * 0 - ячейка пуста, изображение отсутствует.
     * 1 - ячейка с изображением выделена (активна).
     * 2 - ячейка с изображением сброшена (не активна).
     * @param clickCount - счетчик кликов.
     */
    public void setClickCount(int clickCount) {
        if (this.containsImage())
            this.clickCount += clickCount % 3;
        else this.clickCount = 0;
    }

    // Метод возвращает значение счетчика кликов.
    public int getClickCount() {
        if (clickCount == 3) {
            clickCount = 1;
        }
        return clickCount % 3;
    }

    // Метод возвращает String, содержащий название цвета изображения в ячейке.
    public String getPictureColor() {
        String iconAbsolutePath = this.getIcon().toString();
        String[] pathArray = iconAbsolutePath.split("/");
        String[] fileName = pathArray[pathArray.length - 1].split("[-]");
        return fileName[0];
    }

    // Метод возвращает true или false в зависимости есть ли изображение в ячейке.
    public boolean containsImage() {
        return this.getIcon() != null;
    }

    // Переопределение методов equals() и hashCode().
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