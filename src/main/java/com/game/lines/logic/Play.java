package com.game.lines.logic;

import com.game.lines.common.ResourceManger;
import com.game.lines.entity.Cell;
import javafx.util.Pair;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.logging.Logger;

/**
 * Класс Play отвечает за игровую логику игры Lines: перемещение шара из ячейки в ячейку (проверка возможности
 * перемещения); генерацию новых шаров на игровом поле, а также удаление с поля линии из 5-ти шаров
 * одинакового цвета.
 *
 * @author Eugene Ivanov on 24.04.18
 */

public class Play {

    // Логгер игрового процесса.
    private static Logger playLogger;
    // Длина стороны сетки игрового поля.
    private static int sideLength;
    // Переменная принимает значение true, если ход (перемещение) возможен.
    private static boolean moveAbility;
    // Ячейка, в которую перемещаем изображение.
    private Cell targetCell;
    // Связанный список ячеек для реализации проверки возможности хода.
    private List<Cell> visited;
    // Очередь, основанная на связанном списке, необходима для реализации проверки возможности хода.
    private Queue<Cell> queue;
    // Переменная принимает значение true, если строка была удалена.
    private boolean lineState;
    // Сеттер установки значения для переменной lineState.
    private void setLineState(boolean lineState) {
        this.lineState = lineState;
    }
    // Геттер переменной экземпляра - lineState.
    private boolean getLineState() {
        return lineState;
    }

    /**
     * Конструктор класса Play, отвечающего за игровой процесс, принимает в качестве аргументов 2 ячейки:
     * @param filledCell ячейка, из которой необходимо переместить изображение.
     * @param emptyCell пустая ячейка, в которую необходимо переместить изображение.
     */
    private Play(Cell filledCell, Cell emptyCell) {
        playLogger = Logger.getLogger(Play.class.getName());
        sideLength = Cell.getGridLength();  // Длина (в ячейках) стороны квадрата игрового поля.
        targetCell = emptyCell;             // "Целевая ячейка", она же ячейка, в которую нужно ходить.
        visited = new LinkedList<>();  // Инициализация списка, используемого для проверки возможности хода в ячейку.
        queue = new LinkedList<>();    // Инициализация очереди, используемой для проверки возможности хода в ячейку.
        setLineState(false);         // Установка значения переменной экземпляра lineState.
        moveAbility = traverse(filledCell); // Получение результата выполнения метода traverse.
        initializeGame(filledCell, emptyCell); // Инициализация игрового процесса.
    }

    private void initializeGame(Cell filledCell, Cell emptyCell) {
        if ( moveAbility ) {
            // Если ход возможен, то запускаем новый поток.
            new Thread( () -> {
                moveImageCell(filledCell, emptyCell); // Ход (перемещение).
                try {
                    Thread.sleep(500);  // Приотановка потока на 0,5 секунды.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Поиск всех возможных линий на поле.
                linesSearch();
                // Генерируем новые изображения в случайном порядке.
                generateRandomImages( getLineState(), 3 );
                try {
                    Thread.sleep(500);  // Приотановка потока на 0,5 секунды.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Повторно запускаем linesSearch() для поиска и удаления линий, сформированных случайно.
                linesSearch();
                // Добавляем ячейку (из которой изображение было перемещено) в список пустых ячеек.
                Cell.emptyCells.add(filledCell);
            })
                    .start(); // Запускаем поток.

        } else {
            // Если ход невозможен, то логируем сообщение о невозможности хода.
            playLogger.info("Move impossible..");
        }
    }

    /**
     * Метод отвечает за один игровой ход (перемещение изображения в пустую ячейку) и инициализирует игру
     * вызовом конструктора класса.
     * Возвращаемое значение используется для определения ситуации: был выполнен ход или нет.
     * @param filledCell ячейка, из которой необходимо переместить изображение.
     * @param emptyCell пустая ячейка, в которую необходимо переместить изображение.
     * @return значение boolean-типа означающее возможность или невозможность хода в выбранную ячейку.
     */
    public static boolean getMove(Cell filledCell, Cell emptyCell) {
        int emptyCells = Cell.emptyCells.size();
        if ( emptyCells > 3 ) {
            new Play(filledCell, emptyCell);
        } else {
            playLogger.warning("End of the game!");
        }
        return moveAbility;
    }

    /**
     * Метод который осуществляет поиск всех возможных линий на игровом поле.
     */
    private void linesSearch() {
        perpendicularSearch(false); // Поиск линий по вертикали.
        perpendicularSearch(true); // Поиск линий по горизонтали.

        // Поиск линий по диагонали справа налево и снизу вверх с ++ сдвигом по оси Y и -- сдвигом по оси X.
        for (int x = 5; x <= sideLength ; x++) {
            diagonallyLines_1( x, false);
            diagonallyLines_1( x, true);
        }

        // Поиск линий по диагонали слева направо и сверху вниз с -- сдвигом по оси Y и ++ сдвигом по оси X.
        for (int x = sideLength - 4; x >= 2; x--) {
            diagonallyLines_2( x, false);
            diagonallyLines_2( x, true);
        }
    }

    /**
     * Метод поиска перпендикулярных (горизонтальных и вертикальных) линий.
     * @param vertical параметр, в зависисмости от значения которого будет выполяняться поиск линий:
     *                 по горизонтали или вертикали.
     */
    private void perpendicularSearch(boolean vertical) {
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) ->
                vertical ? (curr.getYy() + 1 == next.getYy()) : (curr.getXx() + 1 == next.getXx());

        for (int x = 1; x <= sideLength; x++) {
            Map<String, List<Cell>>  colorSequenceMap = new HashMap<>();

            for (int y = 1; y <= sideLength; y++) {
                Cell nextCell = vertical ? Cell.cellMap.get(new Pair<>(x, y)) : Cell.cellMap.get(new Pair<>(y, x));
                String color = nextCell.containsImage() ? nextCell.getImageColor() : "";

                List<Cell> images = color.isEmpty() ? null : colorSequenceMap.get(color);
                images = Objects.isNull(images) ? new ArrayList<>() : colorSequenceMap.get(color);
                images.add(nextCell);
                if ( !color.isEmpty() ) {
                    colorSequenceMap.put(color, images);
                }
//                playLogger.warning("total keys in map: " + colorSequenceMap.values().size());
            }
            colorSequenceMap.values()
                    .stream().filter( element -> element.size() >= 5)
                    .forEach( collection -> prepareLineSequence(collection, searchPredicate));
        }
    }

    private void diagonallyLines_1(int start_X, boolean isOpposite) {
        Function<Integer, Integer> linearFunction = number -> -1 * number + start_X + 1;
        Function<Integer, Integer> oppositeLinearFunction = number -> sideLength + (number - start_X);
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) -> curr.getXx() - 1 == next.getXx();
        Map<String, List<Cell>> colorSequenceMap = new HashMap<>();

        for (int x = start_X; x >= 1; x--) {
            lineSequence(x, isOpposite, linearFunction, oppositeLinearFunction, colorSequenceMap);
        }
        colorSequenceMap.values()
                .stream().filter( element -> element.size() >= 5)
                .forEach( collection -> prepareLineSequence(collection, searchPredicate));
    }

    private void diagonallyLines_2(int start_X, boolean isOpposite) {
        Function<Integer, Integer> function = number -> Math.abs(number - (start_X - 1) - (sideLength + 1));
        Function<Integer, Integer> oppositeFunction = number -> number - start_X + 1;
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) -> curr.getXx() + 1 == next.getXx();
        Map<String, List<Cell>> colorSequenceMap = new HashMap<>();

        for (int x = start_X; x <= sideLength; x++) {
            lineSequence(x, isOpposite, function, oppositeFunction, colorSequenceMap);
        }
        colorSequenceMap.values()
                .stream().filter( element -> element.size() >= 5)
                .forEach( collection -> prepareLineSequence(collection, searchPredicate));
    }

    /**
     * Построение последовательности изображений одного цвета, найденных в строке.
     * @param x координата X ячейки.
     * @param isOpposite boolean-значение, указывающее на конфигурацию поиска линий.
     * @param function функция, которая выражает Y через Х (линейная функция).
     * @param oppositeFunction функция, которая выражает Y через X, но с помощью другой формулы, нежели function.
     * @param sequenceMap карта, где Key = цвет, Value = последовательность (список) ячеек одного цвета.
     */
    private void lineSequence(int x,
                              boolean isOpposite,
                              Function<Integer, Integer> function,
                              Function<Integer, Integer> oppositeFunction,
                              Map<String, List<Cell>> sequenceMap) {

        int y = isOpposite ? oppositeFunction.apply(x) : function.apply(x);
        Cell nextCell = Cell.cellMap.get(new Pair<>(x, y));
        String color = nextCell.containsImage() ? nextCell.getImageColor() : "";

        List<Cell> images = color.isEmpty() ? null : sequenceMap.get(color);
        images = Objects.isNull(images) ? new ArrayList<>() : sequenceMap.get(color);
        images.add(nextCell);
        if ( !color.isEmpty() ) {
            sequenceMap.put(color, images);
        }
    }

    /**
     * Подготовка последовательности изображений одного цвета, найденных в строке.
     * @param sequence последовательность изображений, в порядке их добавления .
     * @param predicate условие, по которому будут сравниваться ячейки из строки.
     */
    private void prepareLineSequence(List<Cell> sequence, BiPredicate<Cell, Cell> predicate) {
        Map<Integer, Set<Cell>> map = new HashMap<>();
        Set<Cell> cellSet = new HashSet<>();
        Cell current = sequence.get(0);
        cellSet.add(current);
        int key = 1;

        for (int i = 0; i < sequence.size() - 1; i++) {
            Cell next = sequence.get(i + 1);
            if ( predicate.test(current, next) ) {
                cellSet.add(next);
            } else {
                cellSet = new HashSet<>();
                cellSet.add(next);
                key++;
            }
            if ( !cellSet.isEmpty() ) {
                map.put(key, cellSet);
            }
            current = next;
        }
        map.values()
                .stream()
                .filter( element -> element.size() >= 5)
                .forEach(this::deleteImagesFromCells);
    }

    /**
     * Удаление изображений из ячеек.
     * @param line коллекция, содержащая ячейки, изображения из которых необходимо удалить.
     */
    private void deleteImagesFromCells(Collection<Cell> line) {
        playLogger.info("Line of " + line.size() + " balls deleted!");
        setLineState(true); // Значение true означает, что срока удалена.
        line.forEach( cell -> { // Последовательное удаление изображений из ячеек.
            cell.setIcon(null);
            cell.setState(State.EMPTY);
            Cell.emptyCells.add(cell);
        });
        line.clear(); // Очистка коллекции.
    }

    /**
     * Обход графа пустых ячеек (массив или область пустых ячеек на игровом поле, в которой находится
     * ячейка, ИЗ КОТОРОЙ планируется переместить изображение), с целью "посетить" все пустые ячейки в заданной
     * области. Если среди "посещенных" ячеек будет находиться пустая ячейка В КОТОРУЮ планируется переместить
     * изображение, то ход (перемещение) возможен, иначе - ход невозможен.
     * @param node вершина графа, она же - ячейка из которой перемещается изображение.
     * @return true - ход в выбранную ячейку возможен или false - ход невозможен.
     */
    private boolean traverse(Cell node) {
        Function<Cell, List<Cell>> findNeighbors = (cell) ->
                Objects.isNull(cell) ? Collections.emptyList() : cell.getNeighbors();
        Predicate<Cell> predicate = child -> child.getState() == State.EMPTY && !visited.contains(child);
        findNeighbors.apply(node).stream().filter(predicate).forEach( child -> {
            visited.add(child);
            queue.offer(child);
            traverse( queue.poll() );
        });
        return visited.contains(targetCell);
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
        currentCell.setIcon(ResourceManger.ballsMap().get(pictureColor) );
        // Удаляем изображение из предыдущей ячейки.
        previousCell.setIcon(null);
        // Меняем состояния предыдущей и текущей ячеек.
        previousCell.setState(State.EMPTY);
        currentCell.setState(State.RELEASED);
        // Удаляем из списка свободных ячеек текущую ячейку.
        Cell.emptyCells.remove(currentCell);
    }

    /**
     * Заполнение изображениями N пустых случайных ячеек происходит только в том случае, если линия была удалена.
     * @param lineWasDeleted флаг события удаления линии.
     * @param amount количество ячеек для рандомного заполнения изображениями (зависит от настроек игры).
     */
    public static void generateRandomImages(boolean lineWasDeleted, int amount) {
        if ( !lineWasDeleted ) {
            for (int i = 0; i < amount; i++) {
                Cell cell = getRandomCell(Cell.emptyCells); // Получаем рандомную ячейку из массива пустых ячеек.
                int index = (int) (Math.random() * ResourceManger.BALLS.length); // Подбираем случайный индекс.
                cell.setIcon((ImageIcon) ResourceManger.BALLS[index]); // Устанавливаем случайное изображение в ячейку.
                cell.setState(State.RELEASED); // Устанавливаем состояние "ячейка освобождена".
                Cell.emptyCells.remove(cell); // Удаляем ячейку из списка пустых ячеек.
            }
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