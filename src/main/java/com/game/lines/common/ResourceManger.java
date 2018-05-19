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
import java.util.stream.Stream;

/**
 * Класс ResourceManager управляет доступом к ресурсам проекта.
 *
 * @author Eugene Ivanov on 01.04.18
 */

public class ResourceManger {

    // TODO: Обычно константы задаются большими буквами
    // TODO: А инициализацию делать лучше всё таки в конструкторе
    // URL иконки окна игры.
    private static final URL imageIconUrl = ResourceManger.class.getResource("/food/bananas.png");

    // Получаем изображение иконки окна игры, используя URL.
    public static Image getImage() {
        return imageIconUrl != null ? new ImageIcon(imageIconUrl).getImage() : null;
    }
    // Массив изображений используемых в игре.
    public static final Object[] BALLS = ballsMap().values().toArray();

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
            // TODO весь блок try/catch рекомендуется выноосить в отдельный метод, так и читабельнее будет
            try (Stream<Path> paths = Files.walk(Paths.get(resourceUrl.toURI()))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            // TODO: Так же в отдельный метод выносят логику отдельного элемента в цикле
                            // TODO: но тут в принципе 4 строчки, поэтому норм
                            // TODO: System.out.println в качестве тестирования пойдёт, но лучше сразу закладывать использование логгера
                            // TODO: Когда значение, которые ты хочешь передать строится как-то сложно, то лучше задать говорящую переменную:
                            // TODO: *******************************************************************************
                            // TODO: String color = file.getFileName().toString().split("[.-]")[0];
                            // TODO: String image = "/" + file.getParent().getFileName() + "/" + file.getFileName();
                            // TODO: iconMap.put(color, new ImageIcon(RunLines.class.getResource(image)));
                            // TODO: *******************************************************************************

                            URL url = RunLines.class.getResource(
                                    "/" + file.getParent().getFileName() + "/" + file.getFileName());

                            String[] fileName = file.getFileName().toString().split("[.-]");
//                            System.out.println(fileName[0]);
                            ImageIcon imageIcon = new ImageIcon(url);
                            iconMap.put(fileName[0], imageIcon);
//                            System.out.println(url.getFile());
                        });
            } catch (URISyntaxException | IOException e) {
                // TODO: плохой тон стектрейс выводить. нужно пользоваться логгером
                // LOG.warn("Message", e);
                e.printStackTrace();
            }
        }
        return iconMap;
    }
}