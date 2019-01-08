package com.game.lines.gui;

import com.game.lines.util.ResourceManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс GameInitializer отвечает за создание главного окна программы и размер всех необходимых виджетов.
 * @author Eugene Ivanov on 31.03.18
 */

public class GameInitializer extends JFrame {

    /**
     * Конструктор класса GameInitializer, который отвечает за создание и настройку GUI главного окна игры.
     * @param frameWidth ширина фрейма.
     * @param frameHeight высота фрейма.
     * @param gridWidth ширина сетки.
     * @param gridHeight высота сетки.
     */
    public GameInitializer(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        super("Lines");                               // Устанавливаем заголовок окна - название игры.
        setIconImage( ResourceManager.getImageIcon() );    // Устанавливаем изображение/иконку окна игры.
        windowClosingSetUp();                              // Настраиваем закрытие окна игры.
        JPanel gridPanel = new JPanel();                   // Инициализация сетки (игрового поля).
        Grid.getInstance().createGrid(gridWidth, gridHeight, gridPanel);
        GuiManager.getInstance().createGui(this, frameWidth, frameHeight, gridPanel);
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