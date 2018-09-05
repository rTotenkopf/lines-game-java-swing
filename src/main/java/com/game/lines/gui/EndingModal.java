package com.game.lines.gui;

import com.game.lines.logic.Play;

import javax.swing.*;
import java.awt.*;

/**
 * @author Eugene Ivanov on 17.06.18
 */

public class EndingModal extends JDialog {

    private static Color paneColor;
    private static int points = Play.getPointsCounter();
    private static EndingModal modal;

    /**
     * Конструктор класса EndingModal.
     */
    private EndingModal() {
        super(MainPanel.getFrames()[0], "Игра окончена", true);
        modal = this;
        paneColor = new Color(0, 230, 150);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(paneColor);
        addComponentsToPane(getContentPane());
        pack();
        setSize(350, 280);
        setLocationRelativeTo(MainPanel.getFrames()[0]);
        setResizable(false);
        setVisible(true);
    }

    private static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        JPanel messagePane = new JPanel();
        messagePane.setBackground(paneColor);
        messagePane.add(addLabel(pane));
        pane.add(messagePane);
        addRigidArea(pane);
        addAButton(String.format("   %s    ",  "Новая игра")   , pane).addActionListener( event -> {
            Play.startNewGame();
            modal.dispose();
        });

        addRigidArea(pane);
        addAButton(String.format(" %s "      , "Выход из игры"), pane)
                .addActionListener( event -> OptionModal.getOptionPane());
        addRigidArea(pane);
    }

    private static JLabel addLabel(Container container) {
        String labelText =
                "<html>" +
                "<h1 align=\"center\" color=\"purple\">" + "Поздравляем!</h1>" +
                "<h2 align=\"center\" color=\"#A0522D\">" + "Вы набрали " + points + " очков.</h2>" +
                "<font face=\"verdana\" size=4 color=\"navy\">" +
                "<b>На поле не осталось свободных <br> ячеек.</b> <br>" +
                "<b>Выберите дальнейшее действие:</b>" +
                "</html>";
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("", Font.PLAIN, 17));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
        return label;
    }

    private static JButton addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        return button;
    }


    private static void addRigidArea(Container container) {
        Component rigidArea = Box.createRigidArea(new Dimension(15, 15));
        container.add(rigidArea);
    }

    public static void init() {
        new EndingModal();
    }
}