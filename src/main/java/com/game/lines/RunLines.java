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
        new MainFrame(540,570, 9, 9);
    }
}