package com.game.lines.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Eugene Ivanov on 17.06.18
 */

public class EndGameDialog extends JOptionPane {

    /**
     * Конструктор класса EndGameDialog.
     */
    private EndGameDialog() {

        setBackground(Color.ORANGE);
        UIManager.put("OptionPane.yesButtonText", "Новая игра");
        UIManager.put("OptionPane.noButtonText", "Выйти из игры");

        int res = EndGameDialog.showConfirmDialog(
                MainPanel.getFrames()[0],
                "Хотите начать новую игру или выйти?",
                "Игра закончена! ",
                YES_NO_OPTION );

        if ( res == JOptionPane.NO_OPTION ) {
            System.exit(0);
        }
    }

    public static EndGameDialog init() {
        return new EndGameDialog();
    }
}