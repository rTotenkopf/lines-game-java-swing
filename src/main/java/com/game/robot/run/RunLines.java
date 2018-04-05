package com.game.robot.run;

import javax.swing.*;
import java.util.List;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class RunLines {

    public static final ImageIcon[] BALLS = new ImageIcon[] {
            new ImageIcon(MainFrame.checkingUrl("/balls/black-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/blue-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/gray-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/green-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/pink-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/purple-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/red-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/sapphire-ball")),
            new ImageIcon(MainFrame.checkingUrl("/balls/yellow-ball")),
    };

    public static void go() {
        for (int i = 0; i < 3; i++) {
            Cell cell = getRandomCell(GameConstants.FREE_CELLS);
            int index = (int) (Math.random() * BALLS.length);
            cell.setIcon(BALLS[index] );
            GameConstants.FREE_CELLS.remove(cell);
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