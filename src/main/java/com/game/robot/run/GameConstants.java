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

    public static final List<Cell> FREE_CELLS = new LinkedList<>();
}