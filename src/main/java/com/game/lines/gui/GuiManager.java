package com.game.lines.gui;

import com.game.lines.logic.GameHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Класс GuiManager отвечает за создание графического интерфейса приложения.
 *
 * @author Eugene Ivanov on 09.09.18
 */

public class GuiManager {

    private static JLabel infoLabel;   // Информация о состоянии игры.
    private static JLabel pointsLabel; // Информация об очках.
    private static JLabel ballsLabel;  // Информация о количестве удаленных шаров.

    private static final String DEFAULT_POINTS_VALUE;
    private static final String DEFAULT_BALLS_VALUE;

    static {
        DEFAULT_POINTS_VALUE = "Очки: 0";
        DEFAULT_BALLS_VALUE = "0 : Шары";
    }

    public static JLabel getInfoLabel() {
        return infoLabel;
    }

    public static JLabel getPointsLabel() {
        return pointsLabel;
    }

    public static JLabel getBallsLabel() {
        return ballsLabel;
    }

    static GuiManager getInstance() {
        return new GuiManager();
    }

    public static void setDefaultLabelsInfo() {
        pointsLabel.setText(DEFAULT_POINTS_VALUE);
        ballsLabel.setText(DEFAULT_BALLS_VALUE);
    }

    /**
     * Создание gui с добавлением на него сетки из ячеек.
     * @param panel панель игры.
     * @param width ширина фрейма.
     * @param height высота фрейма.
     * @param grid панель, содержащая сетку из ячеек.
     */
    void createGui(GameInitializer panel, int width, int height, JPanel grid) {
        infoLabel = new JLabel("Начата новая игра."); // Виджет состояния игры.
        pointsLabel = new JLabel(DEFAULT_POINTS_VALUE);          // Виджет очков во время игры.
        ballsLabel = new JLabel(DEFAULT_BALLS_VALUE);          // Виджет количества удаленных шаров.
        Font labelFont = new Font("", Font.BOLD, 16);
        infoLabel.setFont(labelFont);
        pointsLabel.setFont(labelFont);
        ballsLabel.setFont(labelFont);

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
        testButton.addActionListener( e -> EndingModal.init());
        northPanel.add(testButton, BorderLayout.CENTER);
        // =====================TEST=======

        panel.pack(); // Установка соответствующего размера окна программы.
        panel.setLocation(500, 100); // Установка положения окна на экране пользователя.
        panel.setSize(width, height); // Установка размера.

        // Добавление панелей на главный фрейм с заданием необходимого расположения.
        panel.getContentPane().add(BorderLayout.SOUTH, southPanel);
        panel.getContentPane().add(BorderLayout.CENTER, grid);
        panel.getContentPane().add(BorderLayout.NORTH, northPanel);
        panel.setResizable(false);
        panel.setVisible(true);

        // TODO: написать отдельный класс, в котором будет инициализация игры.
        // После построение GUI игры, происходит инициализация игрового процесса.
        GameHelper.initGameProcess();
    }
}