package com.game.lines.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Eugene Ivanov on 17.06.18
 */

public class EndingModal extends JDialog {

    static JPanel messagePane;
    JButton newGameBtn;
    JButton exitBtn;

    /**
     * Конструктор класса EndingModal.
     */
    private EndingModal() {
        super(MainPanel.getFrames()[0], "Игра окончена!", true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setBackground(Color.YELLOW);
        addComponentsToPane(getContentPane());
        pack();
        setSize(300, 220);
        setLocationRelativeTo(MainPanel.getFrames()[0]);
        setResizable(false);
        setVisible(true);
    }

    private static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addRigidArea(pane);
        messagePane = new JPanel();
        messagePane.add( addLabel("Игра окончена!", pane));
        pane.add(messagePane);
        addRigidArea(pane);
        addAButton(String.format("    %s    ", "Новая игра")   , pane).addActionListener( event -> {
            // do nothing
        });

        addRigidArea(pane);
        addAButton(String.format(" %s "      , "Выход из игры"), pane).addActionListener( event -> {
            OptionModal.getOptionPane();
        } );
        addRigidArea(pane);
    }

    private static JLabel addLabel(String text, Container container) {
        JLabel label = new JLabel(text);
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