package com.game.lines.common;

import com.game.lines.run.RunLines;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Класс Resource Manager управляет доступом к ресурсам проекта.
 *
 * @author Eugene Ivanov on 01.04.18
 */

public class ResourceManger {

    // Массив изображений используемых в игре.
    public static Object[] balls = ballsMap().values().toArray();

    /**
     * Key = название цвета; Value = изображение;
     * @return карта изображений по цветам.
     */
    public static Map<String, ImageIcon> ballsMap() {
        Map<String, ImageIcon> iconMap = new HashMap<>();
        // Ссылка на ресурс (папку с изображениями).

        URL resourceUrl = RunLines.class.getResource("/balls/");
        // Обход файлов, находящихся в папке ресурса.

        if (resourceUrl != null) {
            try (Stream<Path> paths = Files.walk(Paths.get(resourceUrl.toURI()))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            URL url = RunLines.class.getResource(
                                    "/" + file.getParent().getFileName() + "/" + file.getFileName());

                            String[] fileName = file.getFileName().toString().split("[\\.-]");
//                            System.out.println(fileName[0]);
                            ImageIcon imageIcon = new ImageIcon(url);
                            iconMap.put(fileName[0], imageIcon);
//                            System.out.println(url.getFile());
                        });
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
        return iconMap;
    }
}