package com.game.lines.logic;

import com.game.lines.common.Common;
import com.game.lines.entity.Cell;
import javafx.util.Pair;

import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

/**
 * Класс Play отвечает за игровую логику игры Lines: перемещение шара из ячейки в ячейку (проверка возможности
 * перемещения); генерация новых шаров на игровом поле, а также удаление с поля линии из 5-ти шаров
 * одинакового цвета.
 *
 * @author Eugene Ivanov on 24.04.18
 */

public class Play {

    // Добавляем логгер игрового процесса.
    private static Logger playLogger = Logger.getLogger(Play.class.getName());
    // Ячейка, в которую перемещаем изображение.
    private static Cell target;
    // Связанный список ячеек для реализации проверки возможности хода.
    private static List<Cell> visited = new LinkedList<>();
    // Очередь, основанная на связанном списке, необходима для реализации проверки возможности хода.
    private static Queue<Cell> queue = new LinkedList<>();
    // В эту переменную устанавливается значение true, если ход возможен, иначе - значение false.
    private static boolean moveAbility;

    public static boolean getMove(Cell previousCell, Cell currentCell) {
        new Play( previousCell, currentCell );
        return moveAbility;
    }

    /**
     * Конструктор класса Play, отвечающий за игровой процесс, принимает в качестве аргументов 2 ячейки:
     * @param filledCell ячейка, из которой необходимо переместить изображение.
     * @param emptyCell пустая ячейка, в которую необходимо переместить изображение.
     */
    private Play(Cell filledCell, Cell emptyCell) {
        target = emptyCell;
        visited = new LinkedList<>();
        queue = new LinkedList<>();
        // Получение результата выполнения рекурсивного метода traverse.
        moveAbility = traverse(filledCell);
        // Если ход возможен, то перемещаем изображения (выполняем ход), генерируем новые изображения
        // и выводим сообщение - "ход выполнен успешно". Иначе, выводим сообщение - "ход невозможен".
        if ( moveAbility ) {
            moveImageCell(filledCell, emptyCell);
            generateRandomImages(3);
            playLogger.info("Move complete!");
            findLines(); // Вызов метода для поиска всех сформированных линий.
        } else {
            playLogger.info("Move impossible..");
        }
    }

    private void findLines() {
        int sideLength = Cell.getGridLength(); // Получаем длину стороны сетки игрового поля.

        BiConsumer<Integer, Integer> straight = ( x, y ) -> {
            boolean vertical = x < y;
            x = x == 2 ? 1 : 1;
            y = y == 2 ? 1 : 1;

            Cell prevCell = Cell.cellMap.get( new Pair<>(x, y) );
            Cell nextCell;

            for (x = 1; x <= sideLength; x++) {
                Set<Cell> line = new HashSet<>();

                for (y = 1; y <= sideLength; y++) {
                    nextCell = vertical ? Cell.cellMap.get(new Pair<>(x, y)) : Cell.cellMap.get(new Pair<>(y, x));

                    if ((nextCell.containsImage() && prevCell.containsImage()) &&
                            nextCell.getImageColor().equals(prevCell.getImageColor())) {

                        line.add(prevCell);
                        line.add(nextCell);
                    } else if (line.size() < 5) {
                        line.clear();
                    }
                    if (line.size() == 5) {
                        deleteImagesFromCells(line);
                    }
                    prevCell = nextCell;
                }
//            System.out.println("line.size() = " + line.size());
            }
        };

        straight.accept(1, 2); // Поиск линий по-вертикали.
        straight.accept(2, 1); // Поиск линий по-горизонтали.
    }

    private void deleteImagesFromCells(Collection<Cell> line) {
        line.forEach( cell -> {
            cell.setIcon(null);
            cell.setState(State.EMPTY);
            Cell.emptyCells.add(cell);
        });
        line.clear();
    }

    /**
     * Рекурсивный обход графа пустых ячеек (массив или область пустых ячеек на игровом поле, в которой находится
     * ячейка, ИЗ КОТОРОЙ планируется переместить изображение), с целью "посетить" все пустые ячейки в заданной
     * области. Если среди "посещенных" ячеек будет находиться пустая ячейка В КОТОРУЮ планируется переместить
     * изображение, то ход (перемещение) возможен, иначе - ход невозможен.
     * @param node вершина графа, она же - ячейка из которой перемещается изображение.
     * @return true - ход (перемещение) в выбранную ячейку возможен или false - ход невозможен.
     */
    private boolean traverse(Cell node) {
        // Получаем потомков, находящихся по соседству от ячейки-родителя.
        List<Cell> children;
        if ( !Objects.isNull(node) ) {
            children = node.getNeighbors();
            // Если статус потомка "пустой", то добавляем его в список посещенных ячеек и в конец очереди.
            for (Cell child : children) {
                if (child.getState() == State.EMPTY && !visited.contains(child)) {
                    visited.add(child);
                    queue.offer(child);
                }
            }
//            System.out.println(queue.size());
            traverse( queue.poll() );
        }
        return visited.contains(target) && queue.size() == 0;
    }

    /**
     * Перемещение изображения из одной ячейки в другую.
     * @param previousCell предыдущая ячейка (с изображением)
     * @param currentCell текущая (пустая) ячейка.
     */
    private void moveImageCell(Cell previousCell, Cell currentCell) {
        // Получаем изображение из предыдущей ячейки.
        String pictureColor = previousCell.getImageColor();
        // Устанавливаем изображение в пустую ячейку.
        currentCell.setIcon(Common.picturesMap().get(pictureColor) );
        // Удаляем изображение из предыдущей ячейки.
        previousCell.setIcon(null);
        // Меняем состояния предыдущей и текущей ячеек.
        previousCell.setState(State.EMPTY);
        currentCell.setState(State.RELEASED);
        // Добавляем предыдущую ячейку в список свободных ячеек и удаляем из этого списка текущую ячейку.
        Cell.emptyCells.add(previousCell);
        Cell.emptyCells.remove(currentCell);
    }

    // Заполнение изображениями N пустых случайных ячеек.
    public static void generateRandomImages(int cells) {
        for (int i = 0; i < cells; i++) {
            Cell cell = getRandomCell(Cell.emptyCells); // Получаем рандомную ячейку из массива пустых ячеек.
            int index = (int) (Math.random() * Common.PICTURES.length); // Подбираем случайный индекс.
            cell.setIcon(Common.PICTURES[index]); // Устанавливаем случайное изображение в ячейку.
            cell.setState(State.RELEASED); // Устанавливаем состояние "ячейка освобождена".
            Cell.emptyCells.remove(cell); // Удаляем ячейку из списка пустых ячеек.
        }
    }

    /**
     * Получение случайной пустой ячейки.
     * @param freeCells список пустых ячеек.
     * @return случайная пустая ячейка.
     */
    private static Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }
}