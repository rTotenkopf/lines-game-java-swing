package com.game.robot.run;

import javax.swing.*;
import java.util.List;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class RunLines {

    public void go() {
        for (int i = 0; i < 3; i++) {
            Cell cell = getRandomCell(GameConstants.FREE_CELLS);
            cell.setIcon(new ImageIcon(MainFrame
                    .checkingUrl("/balls/" + getRandomColor() + "-ball") ));
            GameConstants.FREE_CELLS.remove(cell);
        }
    }

    private Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }

    private String getRandomColor () {
        return GameConstants.COLORS[(int) (Math.random() * GameConstants.COLORS.length)];
    }

    public static void main(String[] args) {
        new MainFrame(540,570, 9, 9);
    }
}
