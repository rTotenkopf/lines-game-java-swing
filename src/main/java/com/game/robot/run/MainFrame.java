package com.game.robot.run;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class MainFrame extends JFrame {

    protected static JLabel[][] grid;

    public MainFrame(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        setTitle(GameConstants.MAIN_FRAME_TITLE);
        setIconImage(GameConstants.MAIN_FRAME_ICON);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel northPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(gridWidth,gridHeight) ); // set layout of frame
        grid = new Cell[gridWidth][gridHeight]; // create a grid from many cells (size in choose)
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        JButton startButton = new JButton("Следующий ход");

        startButton.addActionListener(e -> {
            if (GameConstants.FREE_CELLS.size() < 3) {
                System.out.println("End of game!");
            } else {
                new RunLines().go();
            }
        });

        JButton button2 = new JButton("BUTTON_2");

        northPanel.setBackground(Color.YELLOW);
        northPanel.add(startButton);
        southPanel.setBackground(Color.GREEN);
        southPanel.add(button2);

        ClickListener clickListener = new ClickListener();

        for (int y = gridHeight-1; y >= 0; y--) {
            for (int x = 0; x < gridWidth; x++) {
//                grid[x][y] = new JLabel("(" + (x + 1) + "," + (y + 1) + ")");
                grid[x][y] = new Cell(); // create a new cell on grid
                grid[x][y].setBorder(lineBorder);
                grid[x][y].setVerticalAlignment(SwingConstants.CENTER);
                grid[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                grid[x][y].addMouseListener(clickListener);
                grid[x][y].setBackground(Color.WHITE);
                grid[x][y].setOpaque(true);
                centerPanel.add(grid[x][y]); // add element at grid
                GameConstants.FREE_CELLS.add((Cell) grid[x][y]); // add element on List<Cell> FREE_CELLS
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

    protected static URL checkingUrl(String resourcePath) {
        resourcePath = resourcePath + ".png";
        URL resourceUrl = Lines.class.getResource(resourcePath);
        if (resourceUrl == null) {
            System.out.println("Could not find resource!");
            resourceUrl = Lines.class.getResource("/");
        }
        return resourceUrl;
    }
}
