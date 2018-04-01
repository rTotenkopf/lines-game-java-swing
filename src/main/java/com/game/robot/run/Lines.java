package com.game.robot.run;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class Lines {

    private JFrame frame;
    private static JLabel[][] grid;
    private Map<Integer, JLabel> cellMap;

    // constructor of class
    public void createGui(int frameWidth, int frameHeight, int gridWidth, int gridHeight) {
        frame = new JFrame("Lines"); // create a frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // default close operation

        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(gridWidth,gridHeight) ); // set layout of frame
        grid = new JLabel[gridWidth][gridHeight]; // create a grid from many cells (size in choose)
        Border borderLine = BorderFactory.createLineBorder(Color.BLACK, 1);

        String iconPath = "food/bananas";
        frame.setIconImage(new ImageIcon(
                checkingUrl("/"+iconPath )).getImage());

        JButton button1 = new JButton("BUTTON_1");
        JButton button2 = new JButton("BUTTON_2");

        northPanel.setBackground(Color.ORANGE);
        northPanel.add(button1);
        southPanel.setBackground(Color.GREEN);
        southPanel.add(button2);

        cellMap = new HashMap<>();
        int key;

        for (int y = gridHeight-1; y >= 0; y--) {
            for (int x = 0; x < gridWidth; x++) {
//                grid[x][y] = new JLabel("(" + (x + 1) + "," + (y + 1) + ")");
                grid[x][y] = new JLabel(); // create a new cell on grid
                grid[x][y].setBorder(borderLine);
                grid[x][y].setVerticalAlignment(SwingConstants.CENTER);
                grid[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                grid[x][y].addMouseListener(new Clicker());
                grid[x][y].setBackground(Color.WHITE);
                grid[x][y].setOpaque(true);
                key = getCoordinates(x + 1, y + 1);
                cellMap.put(key, grid[x][y]);
                centerPanel.add(grid[x][y]); // add element at grid
            }
        }
        frame.pack(); // set appropriate frame size
        frame.setLocation(500, 100); // location of fram at user screen
        frame.setSize(frameWidth, frameHeight); // standard size of frame
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.setVisible(true); // set option "visible" of frame
    }

    private static int getCoordinates(int x, int y) {
        return Integer.parseInt(String.valueOf(x) + String.valueOf(y));
    }

    public static URL checkingUrl(String resourcePath) {
        resourcePath = resourcePath + ".png";
        URL resourceUrl = Lines.class.getResource(resourcePath);
        if (resourceUrl == null) {
            System.out.println("Could not find resource!");
            resourceUrl = Lines.class.getResource("/");
        }
        return resourceUrl;
    }

    public static void main(String[] args) {
        Lines app = new Lines();

        // 7 X 7
        app.createGui(   395,470, 7, 7);

        // 8 X 8
//        app.createGui(   480,510, 8, 8);

        // 9 X 9
//        app.createGui(   540,570, 9, 9);

        // 10 X 10
//        app.createGui(   600,630, 10, 10);

        // 11 X 11
//        app.createGui(   660,690, 11, 11);

        // 12 X 12
//        app.createGui(   690,770, 12, 12);

        // coordinates of cherries
//        int cherriCoordinates = getCoordinates(5, 3);
//        app.cellMap.get(cherriCoordinates).setBackground(Color.YELLOW);
//        app.cellMap.get(cherriCoordinates).setOpaque(true);
//        app.cellMap.get(cherriCoordinates).setIcon(
//                new ImageIcon(checkingUrl("/cherries.png"))
//        );
//        grid[0][0].setBackground(Color.YELLOW);
        grid[0][0].setIcon(new ImageIcon(
                checkingUrl("/balls/sapphire-ball")));

        grid[1][0].setIcon(new ImageIcon(
                checkingUrl("/balls/black-ball")));

        grid[2][0].setIcon(new ImageIcon(
                checkingUrl("/balls/blue-ball")));

        grid[3][0].setIcon(new ImageIcon(
                checkingUrl("/balls/gray-ball")));

        grid[4][0].setIcon(new ImageIcon(
                checkingUrl("/balls/yellow-ball")));

        grid[5][0].setIcon(new ImageIcon(
                checkingUrl("/balls/purple-ball")));

        grid[6][0].setIcon(new ImageIcon(
                checkingUrl("/balls/red-ball")));

        grid[0][1].setIcon(new ImageIcon(
                checkingUrl("/balls/green-ball")));

        grid[1][1].setIcon(new ImageIcon(
                checkingUrl("/balls/pink-ball")));


        app.frame.addMouseListener(new Clicker());

        // coordinates of color cell
//        int targetCoordinates = getCoordinates(5, 4);
//        app.cellMap.get(targetCoordinates).setBackground(Color.GREEN);
//        System.out.println(app.cellMap.get(targetCoordinates).getX());
//        System.out.println(app.cellMap.get(targetCoordinates).getY());
    }

    static class Clicker implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
//            System.out.println("Я кнопка, меня нажали");
//            e.getComponent().setBackground(Color.ORANGE);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("Я кнопка, меня нажали");
            e.getComponent().setBackground(Color.ORANGE);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("Я кнопка, меня отпустили");
//            e.getComponent().;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
