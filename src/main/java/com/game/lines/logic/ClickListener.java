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

    private Cell previousCell;
    private boolean cellWasMoved;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("======================");
        System.out.println("Я кнопка, меня нажали");

        Cell choosedCell = (Cell) e.getComponent();
        choosedCell.setClickCount(1);
        cellWasMoved = false;

        if (previousCell != null && previousCell.containsImage() && !choosedCell.containsImage() &&
                previousCell.getClickCount() == 1) {
            moveImageCell(choosedCell);
        }
        if (previousCell != null && !(previousCell.equals(choosedCell) )) {
            previousCell.setDefaultBorder();
            previousCell.setClickCount(1);
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
        if (cellWasMoved) {
            choosedCell.setDefaultBorder();
            choosedCell.setClickCount(2);
            previousCell = null;
        } else {
            previousCell = choosedCell;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void moveImageCell(Cell choosed) {
        System.out.println("equals: " + (previousCell.equals(choosed)) );
        System.out.println("icon  : " + previousCell.getIcon());

        String pictureColor = previousCell.getPictureColor();
        choosed.setIcon(Common.imageIconMap().get(pictureColor) );
        previousCell.setIcon(null);

        Common.FREE_CELLS.add(previousCell);
        Common.FREE_CELLS.remove(choosed);
        cellWasMoved = true;
        RunLines.go();
    }
}