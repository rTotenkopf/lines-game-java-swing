package com.game.robot.run;

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
        clickCount++;

        setCell((Cell) e.getComponent() );

        getCell().containsImage = getCell().getIcon() != null;

        List<Cell> cells = GameConstants.CLICKED_CELLS;

        System.out.println("cells.isEmpty() = " + cells.isEmpty());
        System.out.println("getCell().containsImage: " + getCell().containsImage);



        if (clickCount == 1 && getCell().containsImage) {
            getCell().isClicked = true;
            getCell().setBorder(GameConstants.RED_BORDER);
            cells.add(cell);
        }

        System.out.println("clickCount = " + clickCount);

        if (clickCount == 2 && getCell().containsImage) {
            cells.add(cell);

            System.out.println("Objects.equals(cells.get(0), cells.get(1)) = "
                    + Objects.equals(cells.get(0), cells.get(1)));

            clickCount = 0;
            cells.clear();
        }

//        System.out.println("cell.getBorder() = " + cell.getBorder().toString());
//
//        if (clickCount == 2) {
//            GameConstants.CLICKED_CELLS.add(cell);
//        }
//
//        if (GameConstants.CLICKED_CELLS.size() == 2) {
//            List cells = GameConstants.CLICKED_CELLS;
//            System.out.println("Objects.equals(cells.get(0), cells.get(1)) = "
//                    + Objects.equals(cells.get(0), cells.get(1)));
//
//            if (Objects.equals(cells.get(0), cells.get(1))) {
//                GameConstants.CLICKED_CELLS.get(0).setBorder(GameConstants.DEFAULT_BORDER);
//            } else {
//                GameConstants.CLICKED_CELLS.get(0).setBorder(GameConstants.DEFAULT_BORDER);
//                GameConstants.CLICKED_CELLS.get(1).setBorder(GameConstants.RED_BORDER);
//            }
//
//            cells.clear();
//            clickCount = 0;
//
//        }


//        if (Objects.equals(getCell(), this.cell) &&
//                Objects.equals(getClickCount(), this.clickCount) ) {
//
//            if (this.cell.getIcon() != null) {
//                this.getCell().setBorder(GameConstants.RED_BORDER);
//                this.clickCount++;
//            }
//            if (this.clickCount == 2) {
//                this.cell.setBorder(GameConstants.DEFAULT_BORDER);
//                this.clickCount = 0;
//            }
//        }
//
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