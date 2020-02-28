package com.game.lines.gui;

import javax.swing.*;

/**
 * @author Eugene Ivanov on 18.06.18
 */

final class OptionModal {
    /**
     * Настройка модального окна, возницкающего при попытке закрыть главное окно игры.
     */
    private OptionModal() {
        UIManager.put("OptionPane.yesButtonText", "Продолжить игру");
        UIManager.put("OptionPane.noButtonText", "Завершить игру");

        int res = JOptionPane.showConfirmDialog(
                GameInitializer.getFrames()[0],
                "Вы уверены, что хотите выйти из игры?",
                "",
                JOptionPane.YES_NO_OPTION);

        if ( res == JOptionPane.NO_OPTION ) {
            System.exit(0);
        }
    }

    static void getOptionPane() {
        new OptionModal();
    }
}