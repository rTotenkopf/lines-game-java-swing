package com.game.robot.run;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Eugene Ivanov on 31.03.18
 */

class ClickListener implements MouseListener {

    public Cell clickedCell;
    public int clickCount;
    private Border defBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    private Border redBorder = BorderFactory.createLineBorder(Color.RED, 5);

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Я кнопка, меня нажали");

        clickedCell = (Cell) e.getComponent();
        System.out.println(clickedCell.getIcon());

        if (clickedCell.getIcon() != null) {
            clickedCell.setBorder(redBorder);
            clickCount++;
        }

        System.out.println("clickCount = " + clickCount);

        if (clickCount > 1) {
            clickedCell.setBorder(defBorder);
            clickCount = 0;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("Я кнопка, меня отпустили");
//            e.getComponent().;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}