package com.game.lines.common;

import com.game.lines.Application;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс ResourceManager управляет доступом к ресурсам проекта.
 *
 * @author Eugene Ivanov on 01.04.18
 */

public class ResourceManager {

    // Название папки с изображениями.
    private static final String BALLS_FOLDER;
    // Название папки с иконкой.
    private static final String ICON_FOLDER;
    // Имя изображения иконки.
    private static final String ICON_NAME;
    // Название типа (расширения) файла.
    private static final String FILE_TYPE;
    // Суффикс имени файла.
    private static final String SUFFIX;
    // URL иконки окна игры.
    private static final URL IMAGE_ICON_URL;
    // Массив изображений шаров, используемых в игре.
    public static final Object[] BALLS;

    // Блок статической инициализации констант класса.
    static {
        BALLS_FOLDER = "/images/balls/";
        ICON_FOLDER = "/images/icons/";
        ICON_NAME = "bananas";
        FILE_TYPE = ".png";
        SUFFIX  = "-ball";
        IMAGE_ICON_URL = Application.class.getResource(ICON_FOLDER + ICON_NAME + FILE_TYPE);
        BALLS = ballsMap().values().toArray();
    }

    // Получаем изображение иконки окна игры, используя его URL.
    public Image getImageIcon() {
        return IMAGE_ICON_URL != null ? new ImageIcon(IMAGE_ICON_URL).getImage() : null;
    }

    /**
     * Key - название цвета;
     * Value - изображение шара;
     * @return карта изображений шаров по цветам.
     */
    public static Map<String, ImageIcon> ballsMap() {
        Map<String, ImageIcon> imageMap = new HashMap<>();
        List<String> colors = Arrays.asList("black", "blue", "gray", "green", "pink", "purple", "red", "sapphire", "yellow");
        for (String color : colors) {
            ImageIcon image = getImageByColor(color);
            imageMap.put(color, image);
        }
        return imageMap;
    }

    private static ImageIcon getImageByColor(String color) {
        return new ImageIcon(Application.class.getResource(BALLS_FOLDER + color + SUFFIX + FILE_TYPE));
    }
}