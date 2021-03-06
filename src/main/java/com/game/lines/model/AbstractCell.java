package com.game.lines.model;

import com.game.lines.logic.Clickable;
import com.game.lines.logic.State;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Абстрактная ячейка.
 */
public abstract class AbstractCell extends JLabel implements Clickable {
    private int Xx; // положение ячейки по оси координат X
    private int Yy; // положение ячейки по оси координат Y
    Logger cellLogger = Logger.getLogger(Cell.class.getName());
    private static Map<Pair<Integer, Integer>, Cell> cellMap = new HashMap<>();
    /**
     * Список пустых ячеек (состояние которых {@link State#EMPTY} либо {@link Cell#containsImage()}  == false})
     * которые могут быть заполнены изображениями.
     */
    private static List<Cell> emptyCells = new ArrayList<>();
    static Cell previousCell; // Предыдущая нажатая ячейка.
    private State state; // Состояние ячейки.

    AbstractCell() {
        addMouseListener(this);
    }

    public static Map<Pair<Integer, Integer>, Cell> getCellMap() {
        return cellMap;
    }

    public static List<Cell> getEmptyCells() {
        return emptyCells;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getXx() {
        return Xx;
    }

    void setXx(int xx) {
        Xx = xx;
    }

    public int getYy() {
        return Yy;
    }

    void setYy(int yy) {
        Yy = yy;
    }

    /**
     * Возвращаемое значение метода, будет использоваться для поиска изображения по названию цвета, содержащегося
     * в названии файла изображения.
     *
     * @return название цвета картинки.
     */
    public String getImageColor() {
        String iconAbsolutePath = this.getIcon().toString();
        String[] pathArray = iconAbsolutePath.split("/");
        String[] fileName = pathArray[pathArray.length - 1].split("[-]");
        return fileName[0];
    }

    /**
     * @return объект {@link Pair} c координатами ячейки X {@link #Xx}, и Y {@link #Yy}.
     */
    public Pair<Integer, Integer> getCoordinates() {
        return new Pair<>(getXx(), getYy());
    }

    public abstract List<? extends JLabel> getNeighbors();

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public boolean equals(Object obj) {
        boolean value = false;
        if ( !(obj instanceof JButton) && !(obj instanceof JPanel)) {
            try {
                Cell other = (Cell) obj;
                value = (this.Xx == other.getXx() && this.Yy == other.getYy() );
            } catch (ClassCastException e) {
                e.getMessage();
            }
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