package com.game.lines;

import com.game.lines.entity.Cell;
import com.game.lines.common.Common;
import com.game.lines.gui.MainFrame;

import java.util.List;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class RunLines {

    public static void go() {
        for (int i = 0; i < 3; i++) {
            Cell cell = getRandomCell(Common.FREE_CELLS);
            int index = (int) (Math.random() * Common.PICTURES.length);
            cell.setIcon(Common.PICTURES[index] );
            Common.FREE_CELLS.remove(cell);
        }
    }

    private static Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }

    public static void main(String[] args) {
        // 7 X 7
//        new MainFrame(   395,470, 7, 7);

        // 8 X 8
//        new MainFrame(   480,555, 8, 8);

        // 9 X 9
        new MainFrame(   565,640, 9, 9);

        // 10 X 10
//        new MainFrame(   600,630, 10, 10);

        // 11 X 11
//        new MainFrame(   660,690, 11, 11);

        // 12 X 12
//        new MainFrame(   690,770, 12, 12);
    }
}