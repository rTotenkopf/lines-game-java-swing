package com.game.lines.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Eugene Ivanov on 16.06.18
 */

public class EndGamePanel extends JFrame {

    private final int WIDTH;
    private final int HEIGHT;
    private JPanel contents;
    private JButton newGameButton;
    private JButton cancelButton;

    public EndGamePanel() {
        super("Игра окончена!");
        WIDTH = 500;
        HEIGHT = 150;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        UIManager.put("OptionPane.yesButtonText"    , "Да"    );
        UIManager.put("OptionPane.noButtonText"     , "Нет"   );
        UIManager.put("OptionPane.cancelButtonText" , "Отмена");

        Frame mainFrame = MainPanel.getFrames()[0];
        int X = mainFrame.getX() + ((mainFrame.getWidth() - WIDTH) / 2);
        int Y = mainFrame.getY() + ((mainFrame.getHeight() - HEIGHT) / 2);

        contents = new JPanel();
        newGameButton = new JButton("Новая игра");
        cancelButton = new JButton("Выйти");
        contents.add(newGameButton, BorderLayout.WEST);
        contents.add(cancelButton, BorderLayout.EAST);
        setContentPane(contents);

        pack();
        setLocation(X, Y);
        setSize(500, 150);
        setVisible(true);
        setResizable(false);
    }
}