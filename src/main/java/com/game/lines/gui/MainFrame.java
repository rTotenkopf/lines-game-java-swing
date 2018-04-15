package com.game.lines.gui;

import com.game.lines.*;
import com.game.lines.entity.Cell;
import com.game.lines.common.Common;
import com.game.lines.entity.State;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class MainFrame extends JFrame {

    // Сетка/игровое поле
    public static JLabel[][] grid;

    // Конструктор класса, отвечающего за создание графического интерфейса.
    public MainFrame(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        setTitle(Common.MAIN_FRAME_TITLE); // Устанавливаем название окна игры.
        setIconImage(Common.MAIN_FRAME_ICON); // Устанавливаем изображение/иконку окна игры.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Закрытие окна нажатием на "крестик".

        JPanel gridPanel = new JPanel();    // Панель, на которой будет располагаться игровое поле.
        JPanel southPanel = new JPanel();   // Доп. панель.
        JPanel northPanel = new JPanel();   // Доп панель.

        gridPanel.setLayout(new GridLayout(gridWidth,gridHeight) ); // Установка сетки на панель.
        grid = new Cell[gridWidth][gridHeight]; // Инициализация сетки.
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // Установка границ ячеек сетки.

        JButton startButton = new JButton("Следующий ход"); // Кнопка нового хода.
        // Слушатель кнопки.
        startButton.addActionListener(e -> {
            if (Cell.emptyCells.size() < 3) {
                System.out.println("End of game!");
            } else {
               RunLines.go();
            }
        });
        // Пока просто кнопка..
        JButton button2 = new JButton("BUTTON_2");
        // Установка параметров панелей и добавление на них элементов.
        northPanel.setBackground(Color.YELLOW);
        northPanel.add(startButton);
        southPanel.setBackground(Color.GREEN);
        southPanel.add(button2);

        for (int y = gridHeight-1; y >= 0; y--) {
            Cell createdCell;
            for (int x = 0; x < gridWidth; x++) {
                grid[x][y] = new Cell(); // Создание нового объекта ячейки на сетке.
                createdCell = (Cell) grid[x][y]; // Инициализация ячейки.
                createdCell.setXx(x + 1); // Установка координаты Х.
                createdCell.setYy(y + 1); // Установка координаты Y.
//                createdCell.setText("(" + (x + 1) + "," + (y + 1) + ")");
                Common.cellMap.put(createdCell.getCoordinates(), createdCell); // Добавление ячейки в карту ячеек.
                createdCell.setBorder(lineBorder); // Установка границ ячейки.
                createdCell.setVerticalAlignment(SwingConstants.CENTER); // Установка вертикальной центровки.
                createdCell.setHorizontalAlignment(SwingConstants.CENTER); // Установка горизонтальной центровки.
                createdCell.setBackground(Color.WHITE); // Установка цвета фона ячейки.
                createdCell.setOpaque(true); // Установка "непрозрачности" ячейки.
                gridPanel.add(createdCell); // Добавление ячейки на сетку.
                createdCell.setState(State.EMPTY); // Установка состояния ячейки.
                Cell.emptyCells.add(createdCell); // Добавление ячейки в список пустых ячеек.
            }
        }
        pack(); // Установка соответствующего размер фрейма.
        setLocation(500, 100); // Установка положения фрейма на экране пользователя.
        setSize(frameWidth, frameHeight); // Установка размера фрейма.
        // Добавление панелей:
        getContentPane().add(BorderLayout.SOUTH, southPanel);
        getContentPane().add(BorderLayout.CENTER, gridPanel);
        getContentPane().add(BorderLayout.NORTH, northPanel);
        setVisible(true); // Установка опции "видимый".
    }
}