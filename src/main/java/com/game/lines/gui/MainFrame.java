package com.game.lines.gui;

import com.game.lines.*;
import com.game.lines.entity.Cell;
import com.game.lines.common.Common;
import com.game.lines.logic.ClickListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class MainFrame extends JFrame {

    private static JLabel[][] grid;

    public MainFrame(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        setTitle(Common.MAIN_FRAME_TITLE);
        setIconImage(Common.MAIN_FRAME_ICON);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel northPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(gridWidth,gridHeight) ); // set layout of frame
        grid = new Cell[gridWidth][gridHeight]; // create a grid from many cells (size in choose)
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        JButton startButton = new JButton("Следующий ход");

        startButton.addActionListener(e -> {
            if (Common.FREE_CELLS.size() < 3) {
                System.out.println("End of game!");
            } else {
               RunLines.go();
            }
        });

        JButton button2 = new JButton("BUTTON_2");

        northPanel.setBackground(Color.YELLOW);
        northPanel.add(startButton);
        southPanel.setBackground(Color.GREEN);
        southPanel.add(button2);

        ClickListener clickListener = new ClickListener();

        for (int y = gridHeight-1; y >= 0; y--) {
            Cell createdCell;
            for (int x = 0; x < gridWidth; x++) {
                grid[x][y] = new Cell(); // create a new cell on grid
                createdCell = (Cell) grid[x][y];
                createdCell.setXx(x + 1);
                createdCell.setYy(y + 1);
//                createdCell.setText("(" + (x + 1) + "," + (y + 1) + ")");
                createdCell.setBorder(lineBorder);
                createdCell.setVerticalAlignment(SwingConstants.CENTER);
                createdCell.setHorizontalAlignment(SwingConstants.CENTER);
                createdCell.addMouseListener(clickListener);
                createdCell.setBackground(Color.WHITE);
                createdCell.setOpaque(true);
                centerPanel.add(createdCell); // add element at grid
                Common.FREE_CELLS.add(createdCell); // add element on List<Cell> FREE_CELLS
            }
        }
        pack(); // set up of appropriate frame size
        setLocation(500, 100); // location of frame at the user screen
        setSize(frameWidth, frameHeight); // set up of frame size
        getContentPane().add(BorderLayout.SOUTH, southPanel);
        getContentPane().add(BorderLayout.CENTER, centerPanel);
        getContentPane().add(BorderLayout.NORTH, northPanel);
        setVisible(true); // set option "visible" of frame
    }
}