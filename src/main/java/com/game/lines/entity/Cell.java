package com.game.lines.entity;

import com.game.lines.gui.MainFrame;
import javafx.util.Pair;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class Cell extends AbstractCell {

    private int Xx; // Положение ячейки по оси координат X.
    private int Yy; // Положение ячейки по оси координат Y.

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

    /**
     * Реализация абстрактного метода.
     * @return - объект Pair c координатами ячейки (x,y).
     */
    @Override
    public Pair<Integer, Integer> getCoordinates() {
        return new Pair<>(getXx(), getYy());
    }

    /**
     * Реализация абстрактного метода
     * @return - список ячеек, находящихся по соседству данной ячейки.
     */
    @Override
    public List<? extends JLabel> getNeigbors() {
        List<Cell> neighborsList = new LinkedList<>();
        int lenghtOfGridSide = MainFrame.getGridLength();
        return null;
    }

    // Переопределение методов equals() и hashCode().
    @Override
    public boolean equals(Object obj) {
        boolean value = false;
        if (!(obj instanceof JButton)) {
            Cell other = (Cell) obj;
            value = (this.getXx() == other.getXx() && this.getYy() == other.getYy() );
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