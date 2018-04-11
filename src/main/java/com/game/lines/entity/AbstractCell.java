package com.game.lines.entity;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Eugene Ivanov on 11.04.18
 */

abstract class AbstractCell extends JLabel implements Clickable {

    private int clickCount; // Счетчик кликов по ячейке (используется для реализации игровой логики).

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

    /**
     * @return - значение счетчика кликов от 0 до 2.
     */
    public int getClickCount() {
        if (clickCount == 3) {
            clickCount = 1;
        }
        return clickCount % 3;
    }

    // Метод устанавливает границы ячейки, выделяя их красным цветом (ячейка выбрана).
    @Override
    public void select() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    // Метод устанавливает стандартные границы ячейки, без выделения цветом (ячейка освобождена).
    @Override
    public void release() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    /**
     * @return - true или false в зависимости есть ли изображение в ячейке.
     */
    public boolean containsImage() {
        return this.getIcon() != null;
    }

    /**
     * Возвращаемое значение будет использоваться для маппинга изображения по названию цвета.
     * @return - String, содержащий название цвета изображения в ячейке.
     */
    public String getImageColor() {
        String iconAbsolutePath = this.getIcon().toString();
        String[] pathArray = iconAbsolutePath.split("/");
        String[] fileName = pathArray[pathArray.length - 1].split("[-]");
        return fileName[0];
    }

    /**
     * @return - координаты ячейки в объекте Pair.
     */
    public abstract Pair<Integer, Integer> getCoordinates();

    /**
     * @return - список ячеек, находящихся по соседству от данной ячейки.
     */
    public abstract List<? extends JLabel> getNeigbors();
}