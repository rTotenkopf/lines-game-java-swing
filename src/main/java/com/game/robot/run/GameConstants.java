package com.game.robot.run;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class GameConstants {

    public static final String MAIN_FRAME_TITLE = "Lines";

    public static final Image MAIN_FRAME_ICON = new ImageIcon(
            MainFrame.checkingUrl("/food/bananas")).getImage();

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

    public static final String[] COLORS = new String[]
            {"black", "blue", "gray", "green", "pink", "purple", "red", "sapphire", "yellow"};

    public static final List<Cell> FREE_CELLS = new LinkedList<>();
}
