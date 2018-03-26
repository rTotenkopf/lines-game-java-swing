package com.game.robot.run;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class ButtonGrid {

    private JFrame frame;
    private JLabel[][] grid;
    private Border border;
    private Map<Integer, JLabel> cellMap;

    public void createGui(int width, int height){ // constructor of class
        frame = new JFrame("Simple Animation"); // create a frame
        frame.setLayout(new GridLayout(width,height)); // set layout of frame
        grid = new JLabel[width][height]; // create a grid from many cells (size in choose)
        border = BorderFactory.createLineBorder(Color.BLACK, 1);

        frame.setIconImage(new ImageIcon(
                checkingUrl("/bananas.png")).getImage());

        cellMap = new HashMap<>();
        int key;

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                // create a new cell on grid
                grid[x][y] = new JLabel("(" + (x + 1) + "," + (y + 1) + ")");
                grid[x][y].setBorder(border);
                key = getCoordinates(x + 1, y + 1);
                cellMap.put(key, grid[x][y]);
                frame.add(grid[x][y]); // add element at grid
            }
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // default close operation
        frame.pack(); // set appropriate frame size
        frame.setLocation(500, 100); // location of fram at user screen
        frame.setSize(700, 700); // standard size of frame
        frame.setVisible(true); // set option "visible" of frame
    }

    private static int getCoordinates(int x, int y) {
        return Integer.parseInt(String.valueOf(x) + String.valueOf(y));
    }

    private static URL checkingUrl(String resourceName) {
        URL resourceUrl = ButtonGrid.class.getResource(resourceName);
        if (resourceUrl == null) {
            System.out.println("Could not find resource!");
            resourceUrl = ButtonGrid.class.getResource("/");
        }
        return resourceUrl;
    }

    public static void main(String[] args) {
        ButtonGrid buttonGrid = new ButtonGrid();

        buttonGrid.createGui(   10,10);

        // coordinates of cherries
        int cherriCoordinates = getCoordinates(5, 3);
        buttonGrid.cellMap.get(cherriCoordinates).setBackground(Color.YELLOW);
        buttonGrid.cellMap.get(cherriCoordinates).setOpaque(true);
        buttonGrid.cellMap.get(cherriCoordinates).setIcon(
                new ImageIcon(checkingUrl("/cherries.png"))
        );

        // coordinates of color cell
        int targetCoordinates = getCoordinates(5, 4);
        buttonGrid.cellMap.get(targetCoordinates).setBackground(Color.GREEN);
        buttonGrid.cellMap.get(targetCoordinates).setOpaque(true);
    }
}
