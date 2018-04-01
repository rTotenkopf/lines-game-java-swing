package com.game.robot.run;

import java.util.List;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class RunLines {

    public void go() {
        for (int i = 0; i < 3; i++) {
            Cell cell = getRandomCell(GameConstants.FREE_CELLS);
            int index = (int) (Math.random() * GameConstants.BALLS.length);
            cell.setIcon(GameConstants.BALLS[index] );
            GameConstants.FREE_CELLS.remove(cell);
        }
    }

    private Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }

    public static void main(String[] args) {
        new MainFrame(540,570, 9, 9);
    }
}
