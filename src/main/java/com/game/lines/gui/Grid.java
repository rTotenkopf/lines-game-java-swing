package com.game.lines.gui;

import com.game.lines.model.Cell;
import com.game.lines.logic.State;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static com.game.lines.model.Cell.*;

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
        gridPanel.setLayout(new GridLayout(gridWidth, gridHeight) ); // установка сетки на панель
        grid = new Cell[gridWidth][gridHeight]; // инициализация сетки
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // установка границ ячеек сетки
        // создание и инициализация ячеек сетки
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
        getCellMap().put(newCell.getCoordinates(), newCell); // добавление ячейки в карту ячеек
        newCell.setBorder(lineBorder); // установка границ ячейки
        newCell.setVerticalAlignment(SwingConstants.CENTER); // установка вертикальной центровки
        newCell.setHorizontalAlignment(SwingConstants.CENTER); // установка горизонтальной центровки
        newCell.setBackground(Color.WHITE); // установка цвета фона ячейки
        newCell.setOpaque(true); // установка свойства "непрозрачности" ячейки
        gridPanel.add(newCell); // добавление ячейки на сетку
        newCell.setState(State.EMPTY); // установка состояния ячейки
        grid[--x][--y] = newCell; // инициализация ячейки
        getEmptyCells().add(newCell); // добавление ячейки в список пустых ячеек
    }
}