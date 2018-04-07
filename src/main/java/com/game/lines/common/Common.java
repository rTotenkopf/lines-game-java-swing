package com.game.lines.common;

import com.game.lines.entity.Cell;
import com.game.lines.entity.Picture;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eugene Ivanov on 01.04.18
 */

public class Common {

    private static URL checkUrl(String resourcePath) {
        resourcePath = resourcePath + ".png";
        URL resourceUrl = Common.class.getResource(resourcePath);
        if (resourceUrl == null) {
            System.out.println("Could not find resource!");
            resourceUrl = Common.class.getResource("/");
        }
        return resourceUrl;
    }

    public static List<Cell> FREE_CELLS = new LinkedList<>();

    public static final String MAIN_FRAME_TITLE = "Lines";

    public static final Image MAIN_FRAME_ICON = new ImageIcon(
            checkUrl("/food/bananas")).getImage();

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
}