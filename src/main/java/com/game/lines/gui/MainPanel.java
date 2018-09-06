package com.game.lines.gui;

import com.game.lines.common.ResourceManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс MainPanel отвечает за создание главного окна программы и отрисовку всех необходимых виджетов.
 * @author Eugene Ivanov on 31.03.18
 */

public class MainPanel extends JFrame {

    private static JLabel infoLabel;   // Информация о состоянии игры.
    private static JLabel pointsLabel; // Информация об очках.
    private static JLabel ballsLabel;  // Информация о количестве удаленных шаров.

    public static JLabel getInfoLabel() {
        return infoLabel;
    }

    public static JLabel getPointsLabel() {
        return pointsLabel;
    }

    public static JLabel getBallsLabel() {
        return ballsLabel;
    }

    /**
     * Конструктор класса MainPanel, который отвечает за создание и настройку GUI главного окна игры.
     * @param frameWidth ширина фрейма.
     * @param frameHeight высота фрейма.
     * @param gridWidth ширина сетки.
     * @param gridHeight высота сетки.
     */
    public MainPanel(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        super("Lines");                               // Устанавливаем заголовок окна - название игры.
        setIconImage( ResourceManager.getImageIcon() );    // Устанавливаем изображение/иконку окна игры.
        windowClosingSetUp();                              // Настраиваем закрытие окна игры.
        infoLabel = new JLabel("Начата новая игра."); // Виджет состояния игры.
        pointsLabel = new JLabel("Очки: 0");          // Виджет очков во время игры.
        ballsLabel = new JLabel("0 : Шары");          // Виджет количества удаленных шаров.
        JPanel gridPanel = new JPanel();                   // Инициализация сетки (игрового поля).
        Grid.getInstance().createGrid(gridWidth, gridHeight, gridPanel);
        MainPanelGui.getInstance().createGui(this, frameWidth, frameHeight, getInfoLabel(), getPointsLabel(), getBallsLabel(), gridPanel);
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
}