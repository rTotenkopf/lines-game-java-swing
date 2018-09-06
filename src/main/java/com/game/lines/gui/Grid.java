package com.game.lines.gui;

import com.game.lines.entity.Cell;
import com.game.lines.logic.State;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Grid {

    private static JLabel[][] grid;

    public static int getGridLength() {
        return grid.length;
    }

    static Grid getInstance() {
        return new Grid();
    }
    /**
     * Создание сетки.
     * @param gridWidth ширина сетки.
     * @param gridHeight высота сетки.
     */
    void createGrid(int gridWidth, int gridHeight, JPanel gridPanel) {
        gridPanel.setLayout(new GridLayout(gridWidth, gridHeight) ); // Установка сетки на панель.
        grid = new Cell[gridWidth][gridHeight]; // Инициализация сетки.
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // Установка границ ячеек сетки.
        // Создание и инициализация ячеек сетки.
        for (int y = gridHeight; y >= 1; y--) {
            for (int x = 1; x <= gridWidth; x++) {
                Cell createdCell = new Cell(x, y); // Создание новой ячейки на сетке.
                initializeCell( createdCell, gridPanel, lineBorder ); // Вызов метода инициализации ячейки.
            }
        }
    }

    /**
     * Метод, в котором происходит инициализация созданной ячейки.
     * @param newCell новая ячейка.
     * @param gridPanel панель-сетка, где будет располагаться ячейка.
     * @param lineBorder границы ячейки.
     */
   private void initializeCell(Cell newCell, JPanel gridPanel, Border lineBorder) {
        int x = newCell.getXx();
        int y = newCell.getYy();
//        newCell.setText("(" + (x) + "," + (y) + ")");
        Cell.cellMap.put(newCell.getCoordinates(), newCell); // Добавление ячейки в карту ячеек.
        newCell.setBorder(lineBorder); // Установка границ ячейки.
        newCell.setVerticalAlignment(SwingConstants.CENTER); // Установка вертикальной центровки.
        newCell.setHorizontalAlignment(SwingConstants.CENTER); // Установка горизонтальной центровки.
        newCell.setBackground(Color.WHITE); // Установка цвета фона ячейки.
        newCell.setOpaque(true); // Установка свойства "непрозрачности" ячейки.
        gridPanel.add(newCell); // Добавление ячейки на сетку.
        newCell.setState(State.EMPTY); // Установка состояния ячейки.
        grid[--x][--y] = newCell; // Инициализация ячейки.
        Cell.emptyCells.add(newCell); // Добавление ячейки в список пустых ячеек.
    }
}