package com.game.lines.common;

import com.game.lines.Application;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс ResourceManager управляет доступом к ресурсам проекта.
 *
 * @author Eugene Ivanov on 01.04.18
 */

public class ResourceManger {

    // Папка с изображениями.
    private static final String BALLS_FOLDER;
    // Папка с иконкой.
    private static final String ICON_FOLDER;
    // Имя изображения иконки.
    private static final String ICON_NAME;
    // Тип файла.
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

    // Получаем изображение иконки окна игры, используя URL.
    public Image getImageIcon() {
        return IMAGE_ICON_URL != null ? new ImageIcon(IMAGE_ICON_URL).getImage() : null;
    }

    /**
     * Key - название цвета;
     * Value - изображение;
     * @return карта изображений по цветам.
     */
    public static Map<String, ImageIcon> ballsMap() {
        List<String> colors = new ArrayList<>();
        Map<String, ImageIcon> imageMap = new HashMap<>();
        URL resourceUrl = Application.class.getResource( BALLS_FOLDER );

        try (Stream<Path> paths = Files.walk(Paths.get( resourceUrl.toURI() ))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        // Получаем название цвета изображения, содержащееся в имени файла.
                        String color = file.getFileName().toString().split("[.-]")[0];
                        colors.add(color);
                    });
        } catch (URISyntaxException | IOException e) {
            e.getMessage();
        }

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