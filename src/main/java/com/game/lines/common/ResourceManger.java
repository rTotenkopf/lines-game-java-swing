package com.game.lines.common;

import com.game.lines.run.RunLines;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Класс ResourceManager управляет доступом к ресурсам проекта.
 *
 * @author Eugene Ivanov on 01.04.18
 */

public class ResourceManger {

    // Логгер процесса работы с ресурсами.
    private static Logger resourceLogger = Logger.getLogger(ResourceManger.class.getName());
    // URL иконки окна игры.
    private static final URL IMAGE_ICON_URL ;
    // Инициализация константы.
    static {
        IMAGE_ICON_URL = ResourceManger.class.getResource("/food/bananas.png");
    }
    // Получаем изображение иконки окна игры, используя URL.
    public static Image getImage() {
        return IMAGE_ICON_URL != null ? new ImageIcon(IMAGE_ICON_URL).getImage() : null;
    }
    // Массив изображений используемых в игре.
    public static final Object[] BALLS = ballsMap().values().toArray();

    /**
     * Key = название цвета;
     * Value = изображение;
     * @return карта изображений по цветам.
     */
    public static Map<String, ImageIcon> ballsMap() {
        Map<String, ImageIcon> iconMap = new HashMap<>();
        // Ссылка на ресурс (папку с изображениями).
        URL resourceUrl = RunLines.class.getResource("/balls/");
        // Обход файлов, находящихся в папке ресурса.
        if (resourceUrl != null) {
            iconMap = getImagesFromResource( resourceUrl );
        }
        return iconMap;
    }

    private static Map<String, ImageIcon> getImagesFromResource(URL resourceUrl) {
        Map<String, ImageIcon> map = new HashMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(resourceUrl.toURI()))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        // Получаем название цвета изображения, содержащееся в имени файла.
                        String color = file.getFileName().toString().split("[.-]")[0];
//                            resourceLogger.info(color);
//                            resourceLogger.info(url.getFile());
                        String image = "/" + file.getParent().getFileName() + "/" + file.getFileName();
                        // Добавляем ImageIcon в карту, где Key = цвет, Value = ImageIcon.
                        map.put(color, new ImageIcon( RunLines.class.getResource(image) ));
                    });
        } catch (URISyntaxException | IOException e) {
            resourceLogger.warning(e.getMessage());
        }
        return map;
    }
}