package com.game.lines.gui;

import com.game.lines.common.ResourceManger;
import com.game.lines.entity.Cell;
import com.game.lines.logic.Play;
import com.game.lines.logic.State;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Класс MainPanel нужен для создания главного окна программы с отрисовкой всех необходимых виджетов и
 * игрового поля.
 *
 * @author Eugene Ivanov on 31.03.18
 */

public class MainPanel extends JFrame {

    public static JLabel[][] grid; // Сетка/игровое поле
    public static JLabel infoLabel; // Лэйбл с информированием пользователя о состоянии игры.
    public static JLabel pointsLabel;
    public static JLabel ballsLabel;

    // Конструктор класса, отвечающего за создание графического интерфейса.
    public MainPanel(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        // Настройка главного окна игры.
        setTitle("Lines Game"); // Устанавливаем заголовок окна игры.
        setIconImage( new ResourceManger().getImage() ); // Устанавливаем изображение/иконку окна игры.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Устанавливаем закрытие окна нажатием на "крестик".
        String startPhrase = "Начата новая игра."; // Фраза, которая выводится на экран в начале игры.

        // Создание элементов gui.
        infoLabel = new JLabel(startPhrase);
        infoLabel.setFont(new Font("MyfFont", Font.BOLD, 16));

        pointsLabel = new JLabel("Очки: 0");
        pointsLabel.setFont(new Font("MyfFont", Font.BOLD, 16));

        ballsLabel = new JLabel("0 : Шары");
        ballsLabel.setFont(new Font("MyfFont", Font.BOLD, 16));

        JPanel pointsPanel = new JPanel();
        JPanel ballsPanel = new JPanel();
        JPanel gridPanel = new JPanel();    // Панель, на которой располагается игровое поле.
        JPanel southPanel = new JPanel();   // Доп. панель, расположенная снизу экрана.
        JPanel northPanel = new JPanel();   // Доп. панель, расположенная сверху экрана..

        // Создание сетки из ячеек для игры.
        gridPanel.setLayout(new GridLayout(gridWidth, gridHeight) ); // Установка сетки на панель.
        grid = new Cell[gridWidth][gridHeight]; // Инициализация сетки.
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1); // Установка границ ячеек сетки.

        // Установка параметров панелей и добавление на них элементов.
        pointsPanel.setBackground(Color.YELLOW);
        pointsPanel.add(pointsLabel);
        ballsPanel.setBackground(Color.YELLOW);
        ballsPanel.add(ballsLabel);
        northPanel.setBackground(Color.YELLOW);
        northPanel.setLayout(new BorderLayout());
        northPanel.add(pointsPanel, BorderLayout.WEST);
        northPanel.add(ballsPanel, BorderLayout.EAST);
        southPanel.setBackground(Color.YELLOW);
        southPanel.add(infoLabel);

        // Инициализации ячеек сетки.
        for (int y = gridHeight; y >= 1; y--) {
            for (int x = 1; x <= gridWidth; x++) {
                Cell createdCell = new Cell(x, y); // Создание нового объекта ячейки на сетке.
                initializeCell( createdCell, gridPanel, lineBorder ); // Вызов метода инициализации ячейки.
            }
        }
        pack(); // Установка соответствующего размера окна программы.
        setLocation(500, 100); // Установка положения окна на экране пользователя.
        setSize(frameWidth, frameHeight); // Установка размера.

        // Добавление панелей на главный фрейм с заданием необходимого расположения.
        getContentPane().add(BorderLayout.SOUTH, southPanel);
        getContentPane().add(BorderLayout.CENTER, gridPanel);
        getContentPane().add(BorderLayout.NORTH, northPanel);
        setVisible(true); // Установка свойства "видимый".

        // Инициализация игры, генерации первых N изображений в ячейки.
        // Задержка в 1с необходима, чтобы все элементы gui успели отрисоваться перед началом новой игры.
        new Thread( () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Рандом изображений.
            Play.generateRandomImages(startPhrase, false, 5);
        }).start();
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