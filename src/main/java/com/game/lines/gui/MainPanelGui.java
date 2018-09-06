package com.game.lines.gui;

import com.game.lines.logic.Play;

import javax.swing.*;
import java.awt.*;

class MainPanelGui {

    static MainPanelGui getInstance() {
        return new MainPanelGui();
    }

    /**
     * Создание gui с добавлением на него сетки из ячеек.
     * @param panel панель игры.
     * @param width ширина фрейма.
     * @param height высота фрейма.
     * @param grid панель, содержащая сетку из ячеек.
     */
    void createGui(MainPanel panel, int width, int height, JLabel infoLabel, JLabel pointsLabel, JLabel ballsLabel, JPanel grid) {
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
        testButton.addActionListener( e -> {
            EndingModal.init();

        });
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

        // После построение GUI игры, происходит инициализация игрового процесса.
        Play.initGameProcess();
    }
}