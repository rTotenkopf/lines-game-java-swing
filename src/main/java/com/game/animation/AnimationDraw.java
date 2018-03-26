package com.game.animation;

import com.game.robot.connections.RobotConnection;
import com.game.robot.connections.RobotConnectionManager;
import com.game.robot.entities.Robot;
import com.game.robot.exceptions.RobotConnectionException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class AnimationDraw extends JPanel {

    private DrawPanel contentPane;

    public void display() {
        JFrame frame = new JFrame("Robot");
        JButton[][] grid;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = new DrawPanel();

        frame.setContentPane(contentPane);
        frame.setLayout(new GridLayout(10, 10));
        grid = new JButton[10][10];

        frame.add(grid[1][1]);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocation(500, 100);
        frame.setSize(500, 500);
        frame.setVisible(true);



        URL url = getClass().getResource("/bananas.png");
        if (url == null)
            System.out.println( "Could not find image!" );
        else
            frame.setIconImage(new ImageIcon(url).getImage());
    }

    public class DrawPanel extends JPanel {

        private BufferedImage image;

        public DrawPanel() {
            try {
                image = ImageIO.read(DrawPanel.class.getResource("/cherries.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 200, 200, this);
        }
    }

    private static void moveRobot(RobotConnectionManager manager, int toX, int toY) throws InterruptedException {
        int count = 0;
        int maxAttempts = 3;
        boolean completed = false;

        while (!completed) {
            try (RobotConnection rc = manager.getConnection()) {
                rc.moveRobotTo(toX, toY);
                completed = true;
            } catch (RobotConnectionException r) {
                if (++count == maxAttempts) {
                    throw new RobotConnectionException("Connection failed");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Runnable runnable = new AnimationDraw()::display;
        EventQueue.invokeLater(runnable);

        RobotConnectionManager manager = new Robot();

        moveRobot(manager, 4, 5);
    }
}
