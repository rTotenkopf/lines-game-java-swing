package com.game.lines.logic;

import com.game.lines.RunLines;
import com.game.lines.common.Common;
import com.game.lines.entity.Cell;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class ClickListener implements MouseListener {

    private Cell anyCell;
    private boolean flag;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("======================");
        System.out.println("Я кнопка, меня нажали");

        Cell choosedCell = (Cell) e.getComponent();
        choosedCell.setClickCount(1);
        flag = false;

        if (anyCell != null && anyCell.containsImage() && !choosedCell.containsImage() &&
                anyCell.getClickCount() == 1) {
            moveImageCell(choosedCell);
        }
        if (anyCell != null && !(anyCell.equals(choosedCell) )) {
            anyCell.setDefaultBorder();
            anyCell.setClickCount(1);
        }
        if (choosedCell.containsImage()) {
            if (choosedCell.getClickCount() == 0) {
                choosedCell.setClickCount(1);
            }
            if (choosedCell.getClickCount() == 1) {
                choosedCell.setRedBorder();
            } else {
                choosedCell.setDefaultBorder();
            }
        }
        System.out.println("choosedCell.getClickCount() = " + choosedCell.getClickCount());
        if (flag) {
            choosedCell.setDefaultBorder();
            choosedCell.setClickCount(2);
            anyCell = null;
        } else {
            anyCell = choosedCell;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void moveImageCell(Cell choosed) {
        System.out.println("equals: " + (anyCell.equals(choosed)) );
        System.out.println("icon  : " + anyCell.getIcon());

        String pictureColor = anyCell.getPictureColor();
        choosed.setIcon(Common.imageIconMap().get(pictureColor) );
        anyCell.setIcon(null);

        Common.FREE_CELLS.add(anyCell);
        Common.FREE_CELLS.remove(choosed);
        flag = true;
        RunLines.go();
    }
}