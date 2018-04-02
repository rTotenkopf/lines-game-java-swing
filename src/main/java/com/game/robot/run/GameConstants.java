package com.game.robot.run;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class GameConstants {

    public static final String MAIN_FRAME_TITLE = "Lines";

    public static final Image MAIN_FRAME_ICON = new ImageIcon(
            MainFrame.checkingUrl("/food/bananas")).getImage();

    public static final Border DEFAULT_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1);
    public static final Border RED_BORDER = BorderFactory.createLineBorder(Color.RED, 5);

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

    public static final List<Cell> FREE_CELLS = new LinkedList<>();

    public static final List<Cell> CLICKED_CELLS = new ArrayList<>(2);
}