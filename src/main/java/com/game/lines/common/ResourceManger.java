package com.game.lines.common;

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

public class ResourceManger {

    // Папка с изображениями.
    private String BALLS_FOLDER;
    // Тип файла изображения.
    private String FILE_TYPE;
    // URL иконки окна игры.
    private final URL IMAGE_ICON_URL;
    // Получаем изображение иконки окна игры, используя URL.
    public Image getImageIcon() {
        return IMAGE_ICON_URL != null ? new ImageIcon(IMAGE_ICON_URL).getImage() : null;
    }
    // Массив изображений используемых в игре.
    public final Object[] BALLS;

    /**
     * Конструктор класса ResourceManager.
     */
    public ResourceManger() {
        String iconFolder = "/icons/";
        String iconName = "bananas";
        BALLS_FOLDER = "/balls/";
        FILE_TYPE = ".png";
        IMAGE_ICON_URL = getClass().getResource( iconFolder + iconName + FILE_TYPE);
        BALLS = ballsMap().values().toArray();
    }

    /**
     * Key = название цвета;
     * Value = изображение;
     * @return карта изображений по цветам.
     */
    public Map<String, ImageIcon> ballsMap() {
        List<String> colors = Arrays.asList("black", "blue", "green", "purple", "red", "sapphire", "yellow");
        Map<String, ImageIcon> imageMap = new HashMap<>();

        for (String color : colors) {
            ImageIcon image = getImageByColor(color);
            imageMap.put(color, image);
        }
        return imageMap;
    }

    private ImageIcon getImageByColor(String color) {
        return new ImageIcon(getClass().getResource(BALLS_FOLDER + color + "-ball" + FILE_TYPE));
    }
}