package com.game.lines.common;

import com.game.lines.entity.Cell;
import com.game.lines.entity.Picture;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class Common {

    public static final String MAIN_FRAME_TITLE = "Lines";

    public static final Image MAIN_FRAME_ICON = new ImageIcon(
            checkUrl("/food/bananas")).getImage();

    public static final JLabel[][] GRID_9X9 = new JLabel[9][9];

    public static final Picture[] PICTURES = new Picture[] {
            new Picture(checkUrl("/balls/black-ball")),
            new Picture(checkUrl("/balls/blue-ball")),
            new Picture(checkUrl("/balls/gray-ball")),
            new Picture(checkUrl("/balls/green-ball")),
            new Picture(checkUrl("/balls/pink-ball")),
            new Picture(checkUrl("/balls/purple-ball")),
            new Picture(checkUrl("/balls/red-ball")),
            new Picture(checkUrl("/balls/sapphire-ball")),
            new Picture(checkUrl("/balls/yellow-ball")),
    };

    public static Map<String, Picture> imageIconMap() {
        Map<String, Picture> map = new HashMap<>();
        map.put("black", PICTURES[0]);
        map.put("blue", PICTURES[1]);
        map.put("gray", PICTURES[2]);
        map.put("green", PICTURES[3]);
        map.put("pink", PICTURES[4]);
        map.put("purple", PICTURES[5]);
        map.put("red", PICTURES[6]);
        map.put("sapphire", PICTURES[7]);
        map.put("yellow", PICTURES[8]);
        return map;
    }

    public static Map<Pair<Integer, Integer>, Cell> cellMap = new HashMap<>();

    private static URL checkUrl(String resourcePath) {
        resourcePath = resourcePath + ".png";
        URL resourceUrl = Common.class.getResource(resourcePath);
        if (resourceUrl == null) {
            System.out.println("Could not find resource!");
            resourceUrl = Common.class.getResource("/");
        }
        return resourceUrl;
    }

    public static List<Cell> freeCells = new LinkedList<>();
}