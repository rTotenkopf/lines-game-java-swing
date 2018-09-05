package com.game.lines.gui;

import com.game.lines.common.ResourceManager;
import com.game.lines.entity.Cell;
import com.game.lines.logic.Play;
import com.game.lines.logic.State;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс MainPanel отвечает за создание главного окна программы и отрисовку всех необходимых виджетов.
 * @author Eugene Ivanov on 31.03.18
 */

public class MainPanel extends JFrame {

    public static JLabel[][] grid;    // Сетка/игровое поле
    public static JLabel infoLabel;   // Информация о состоянии игры.
    public static JLabel pointsLabel; // Информация об очках.
    public static JLabel ballsLabel;  // Информация о количестве удаленных шаров.

    /**
     * Конструктор класса MainPanel, который отвечает за создание и настройку GUI главного окна игры.
     * @param frameWidth ширина фрейма.
     * @param frameHeight высота фрейма.
     * @param gridWidth ширина сетки.
     * @param gridHeight высота сетки.
     */
    public MainPanel(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        super("Lines"); // Устанавливаем заголовок окна - название игры.
        setIconImage( ResourceManager.getImageIcon() ); // Устанавливаем изображение/иконку окна игры.
        windowClosingSetUp(); // Настраиваем закрытие окна игры.

        infoLabel = new JLabel("Начата новая игра."); // Инициализация информационного виджета.
        JPanel gridPanel = new JPanel();    // Инициализация панели/сетки, на которой располагается игровое поле.

        createGrid(gridWidth, gridHeight, gridPanel);
        MainPanelGui.getInstance().create(this, frameWidth, frameHeight, infoLabel, gridPanel);
    }

    /**
     * Предварительная обработка действия закрытия окна (добавление модального окна, появляющегося
     * при попытке закрытия приложения и спрашивающего подтверждение для этого действия).
     */
    private void windowClosingSetUp() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                OptionModal.getOptionPane();
            }
        });
    }

    /**
     * Создание сетки.
     * @param gridWidth ширина сетки.
     * @param gridHeight высота сетки.
     * @param gridPanel панель, содержащая сетку из ячеек.
     */
    private void createGrid(int gridWidth, int gridHeight, JPanel gridPanel) {
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