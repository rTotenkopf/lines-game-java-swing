package com.game.lines.gui;

import com.game.lines.common.ResourceManger;
import com.game.lines.entity.Cell;
import com.game.lines.logic.Play;
import com.game.lines.logic.State;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Класс MainFrame нужен для создания главного окна программы с отрисовкой всех необходимых виджетов и
 * игрового поля.
 *
 * @author Eugene Ivanov on 31.03.18
 */

public class MainFrame extends JFrame {

    // Сетка/игровое поле
    public static JLabel[][] grid;

    // Конструктор класса, отвечающего за создание графического интерфейса.
    public MainFrame(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        // Устанавливаем заголовок окна игры.
        setTitle("Lines");
        // Устанавливаем изображение/иконку окна игры.
        setIconImage( ResourceManger.getImage() );
        // Устанавливаем закрытие окна нажатием на "крестик".
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel();    // Панель, на которой будет располагаться игровое поле.
        JPanel southPanel = new JPanel();   // Доп. панель.
        JPanel northPanel = new JPanel();   // Доп. панель.

        gridPanel.setLayout(new GridLayout(gridWidth, gridHeight) ); // Установка сетки на панель.
        grid = new Cell[gridWidth][gridHeight]; // Инициализация сетки.
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // Установка границ ячеек сетки.

        JButton startButton = new JButton("Следующий ход"); // Кнопка нового хода.
        // Слушатель кнопки.
        startButton.addActionListener( e -> {
            if (Cell.emptyCells.size() < 5) {
                System.out.println("End of game!");
            } else {
                Play.generateRandomImages(5);
            }
        });
        // Пока просто кнопка..
        JButton button2 = new JButton("BUTTON_2");
        // Установка параметров панелей и добавление на них элементов.
        northPanel.setBackground(Color.YELLOW);
        northPanel.add(startButton);
        southPanel.setBackground(Color.GREEN);
        southPanel.add(button2);

        for (int y = gridHeight; y >= 1; y--) {
            for (int x = 1; x <= gridWidth; x++) {
                Cell createdCell = new Cell(x, y); // Создание нового объекта ячейки на сетке.
                initializeCell( createdCell, gridPanel, lineBorder ); // Вызов метода инициализации ячейки.
            }
        }
        pack(); // Установка соответствующего размер фрейма.
        setLocation(500, 100); // Установка положения фрейма на экране пользователя.
        setSize(frameWidth, frameHeight); // Установка размера фрейма.
        // Добавление панелей на главный фрейм с заданием необходимого расположения.
        getContentPane().add(BorderLayout.SOUTH, southPanel);
        getContentPane().add(BorderLayout.CENTER, gridPanel);
        getContentPane().add(BorderLayout.NORTH, northPanel);
        setVisible(true); // Установка свойства "видимый".
    }

    /**
     * Метод инициализации ячейки.
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