package com.game.lines.gui;

import com.game.lines.common.ResourceManger;
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
        super("Lines Game"); // Устанавливаем заголовок окна игры.
        setIconImage( new ResourceManger().getImage() ); // Устанавливаем изображение/иконку окна игры.
        windowClosingSetUp(); // Настраиваем закрытие окна игры.

        String startPhrase = "Начата новая игра."; // Фраза, которая выводится на экран в начале игры.
        infoLabel = new JLabel(startPhrase); // Инициализация информационного виджета.
        JPanel gridPanel = new JPanel();    // Инициализация панели/сетки, на которой располагается игровое поле.

        createGrid(gridWidth, gridHeight, gridPanel);
        createGui(frameWidth, frameHeight, infoLabel, gridPanel);
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
                UIManager.put("OptionPane.yesButtonText", "Продолжить игру");
                UIManager.put("OptionPane.noButtonText", "Завершить игру");

                int res = JOptionPane.showConfirmDialog(
                        MainPanel.this,
                        "Вы уверены, что хотите выйти из игры?",
                        "",
                        JOptionPane.YES_NO_OPTION);

                if ( res == JOptionPane.NO_OPTION ) {
                    System.exit(0);
                }
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
     * Создание gui с добавлением на него сетки из ячеек.
     * @param frameWidth ширина фрейма.
     * @param frameHeight высота фрейма.
     * @param infoLabel элемент, который содержит информацию о ходе игры.
     * @param gridPanel панель, содержащая сетку из ячеек.
     */
    private void createGui(int frameWidth, int frameHeight, JLabel infoLabel, JPanel gridPanel) {
        infoLabel.setFont(new Font("MyfFont", Font.BOLD, 16));
        pointsLabel = new JLabel("Очки: 0");
        pointsLabel.setFont(new Font("MyfFont", Font.BOLD, 16));
        ballsLabel = new JLabel("0 : Шары");
        ballsLabel.setFont(new Font("MyfFont", Font.BOLD, 16));

        JPanel pointsPanel = new JPanel();
        JPanel ballsPanel = new JPanel();
        JPanel southPanel = new JPanel();   // Доп. панель, расположенная снизу экрана.
        JPanel northPanel = new JPanel();   // Доп. панель, расположенная сверху экрана..

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

        // =====================TEST=======
        JButton testButton = new JButton("Тест завершения игры");
        testButton.addActionListener( e -> EndGameDialog.init());
        northPanel.add(testButton, BorderLayout.CENTER);
        // =====================TEST=======

        pack(); // Установка соответствующего размера окна программы.
        setLocation(500, 100); // Установка положения окна на экране пользователя.
        setSize(frameWidth, frameHeight); // Установка размера.

        // Добавление панелей на главный фрейм с заданием необходимого расположения.
        getContentPane().add(BorderLayout.SOUTH, southPanel);
        getContentPane().add(BorderLayout.CENTER, gridPanel);
        getContentPane().add(BorderLayout.NORTH, northPanel);
        setVisible(true);
        setResizable(false);

        // Инициация игрового процесса.
        new Thread( () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Рандом изображений в сетку.
            Play.generateRandomImages(infoLabel.getText(), false, 5);
        }).start();
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