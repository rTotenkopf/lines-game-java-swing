package com.game.robot.run;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Objects;

/**
 * @author Eugene Ivanov on 31.03.18
 */

class ClickListener implements MouseListener {

    public Cell cell;
    public int clickCount;

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("======================");
        System.out.println("Я кнопка, меня нажали");

        setCell((Cell) e.getComponent() );

        getCell().containsImage = getCell().getIcon() != null;

        if (getCell().containsImage) {
            clickCount++;
        }

        List<Cell> cells = GameConstants.CLICKED_CELLS;

        System.out.println("cells.isEmpty() = " + cells.isEmpty());
        System.out.println("getCell().containsImage: " + getCell().containsImage);


        // если кликаем 1-й раз на ячейку с изображением
        if (clickCount == 1 && getCell().containsImage) {
            cells.add(cell);
            getCell().isClicked = true;
            getCell().setBorder(GameConstants.RED_BORDER);
        }

        // если кликаем 2-й раз на НЕЁ ЖЕ
        if (clickCount == 2 && getCell().containsImage) {
            cells.add(cell);
            if (Objects.equals(cells.get(0), cells.get(1))) {
                cells.remove(cells.get(1));
                clickCount = 1;
            }
        }

        System.out.println("clickCount = " + clickCount);

        // если кликаем 2-й раз на ДРУГУЮ ячейку с изображенем
        if (clickCount == 2 && getCell().containsImage) {
            cells.add(cell);

            System.out.println("Objects.equals(cells.get(0), cells.get(1)) = "
                    + Objects.equals(cells.get(0), cells.get(1)));

            if (!Objects.equals(cells.get(0), cells.get(1))) {
                cells.get(0).setBorder(GameConstants.DEFAULT_BORDER);
                cells.get(1).setBorder(GameConstants.RED_BORDER);
                cells.clear();
                cells.add(cell);
                clickCount = 1;
            }
        }
        // если нажимаем 2-й раз на ПУСТУЮ ЯЧЕЙКУ
        else if (!getCell().containsImage) {
            Icon icon = cells.get(0).getIcon();
            cells.add(cell);
            cells.get(0).setBorder(GameConstants.DEFAULT_BORDER);
            cells.get(0).setIcon(null);
            cells.get(1).setBorder(GameConstants.RED_BORDER);
            cells.get(1).setIcon(icon);
            cells.clear();
            cells.add(cell);
            clickCount = 1;
            System.out.println("cells.size() = " + cells.size());
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