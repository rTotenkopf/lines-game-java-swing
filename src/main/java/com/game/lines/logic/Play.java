package com.game.lines.logic;

import com.game.lines.gui.GuiManager;
import com.game.lines.model.Cell;
import com.game.lines.gui.Grid;
import javafx.util.Pair;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static com.game.lines.util.ResourceManager.*;
import static com.game.lines.logic.State.*;
import static com.game.lines.model.Cell.*;

/**
 * Игровая логика игры Lines.
 * Перемещение шара из ячейки в ячейку (проверка возможности перемещения), генерация новых шаров на игровом поле,
 * а также удаление с поля линии из 5-ти и более шаров одинакового цвета.
 */
public class Play {
    // логгер игрового процесса
    private Logger playLogger;
    // счетчик очков в игре
    private static int pointsCounter;
    // счетчик удаленных шаров
    private static int ballsCounter;
    // длина стороны сетки игрового поля
    private static int sideLength;
    // переменная принимает значение true, если ход (перемещение) возможен
    private static boolean moveAbility;
    // ячейка, в которую перемещаем изображение
    private Cell targetCell;
    // связанный список ячеек для реализации проверки возможности хода
    private List<Cell> visited;
    // очередь, необходимая для реализации проверки возможности хода
    private Queue<Cell> queue;
    // переменная принимает значение true, если строка была удалена
    private boolean lineState;

    static void setPointsCounter(int pointsCounter) {
        Play.pointsCounter = pointsCounter;
    }

    public static int getPointsCounter() {
        return pointsCounter;
    }

    private void setLineState(boolean lineState) {
        this.lineState = lineState;
    }

    static void setBallsCounter(int ballsCounter) {
        Play.ballsCounter = ballsCounter;
    }

    static int getBallsCounter() {
        return ballsCounter;
    }

    private boolean getLineState() {
        return lineState;
    }

    /**
     * Конструктор класса Play, отвечающего за игровой процесс, принимает в качестве параметров 2 ячейки:
     * @param filledCell ячейка, из которой необходимо переместить изображение.
     * @param emptyCell пустая ячейка, в которую необходимо переместить изображение.
     */
    private Play(Cell filledCell, Cell emptyCell) {
        playLogger = Logger.getLogger(getClass().getName());
        sideLength = Grid.getGridLength();  // длина (в ячейках) стороны квадрата игрового поля
        targetCell = emptyCell;             // "целевая ячейка", она же ячейка, в которую нужно ходить
        visited = new ArrayList<>();        // инициализация списка, используемого для проверки возможности хода в ячейку
        queue = new LinkedList<>();         // инициализация очереди, используемой для проверки возможности хода в ячейку
        setLineState(false);                // установка значения переменной экземпляра lineState
        moveAbility = traverse(filledCell); // получение результата выполнения метода traverse
        makeMove(filledCell, emptyCell);    // вызов метода для исполнения одного игорового хода
    }

    /**
     * Выполнение хода.
     * Если значение переменной {@link #moveAbility} == true, то выполняется ход.
     * Создается новый поток, в котором выполняется перемещение ячеек, затем происходит остановка потока
     * на 0,5 секунды, для того чтобы, втечение этих 0,5 секунд, была видна вся удаляемая линия.
     * Удалением всех сформированных линий из 5 и более шаров, занимается метод {@link #linesSearch}.
     * Затем вызывается метод {@link GameHelper#checkGameEndingCondition()}, который выполняет проверку условия:
     * "Должна ли завершиться игра, при N свободных ячейках, оставшихся на игровом поле?"
     * Далее поток вновь приостанавливается на 0,5 секунды, для того чтобы дать игроку увидеть, какие линии
     * будут удалены повторным вызовом {@link #linesSearch}.
     * (метод вызывается повторно, потому что необходимо удалить также линии, которые были сфомированы рандомно,
     * т.е. случайным образом, когда сгенерированные методом {@link GameHelper#generateRandomImages(String, boolean, int)}
     * изображения, выстраиваются в линии без прямого воздействия игрока.
     *
     * @param filledCell ячейка с изображением.
     * @param emptyCell пустая ячейка.
     */
    private void makeMove(Cell filledCell, Cell emptyCell) {
        if ( moveAbility ) {
            GuiManager.getInfoLabel().setText("Ход выполняется...");
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
                GameHelper.generateRandomImages("Ход успешно выполнен.", getLineState(), 3);
                GameHelper.checkGameEndingCondition();
                try {
                    Thread.sleep(500);  // Приотановка потока на 0,5 секунды.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Повторно запускаем linesSearch() для поиска и удаления линий, сформированных случайно.
                linesSearch();
                // Добавляем ячейку (из которой изображение было перемещено) в список пустых ячеек.
//                Cell.emptyCells.add(filledCell);
//                filledCell.setState(State.EMPTY);
            })
                    .start(); // Запускаем поток.

        } else {
            // Если ход невозможен, то логируем сообщение о невозможности хода.
            playLogger.info("Move impossible..");
            GuiManager.getInfoLabel().setText("Ход в выбранную ячейку невозможен..");
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
    public static boolean moveInit(Cell filledCell, Cell emptyCell) {
        new Play(filledCell, emptyCell);

        return moveAbility;
    }

    /**
     * Поиск всех возможных линий на игровом поле.
     */
    private void linesSearch() {
        perpendicularSearch(false); // поиск линий по вертикали
        perpendicularSearch(true); // поиск линий по горизонтали

        // поиск линий по диагонали справа налево и снизу вверх с ++ сдвигом по оси Y и -- сдвигом по оси X
        for (int x = 5; x <= sideLength ; x++) {
            diagonallyLines_1( x, false);
            diagonallyLines_1( x, true);
        }
        // поиск линий по диагонали слева направо и сверху вниз с -- сдвигом по оси Y и ++ сдвигом по оси X
        for (int x = sideLength - 4; x >= 2; x--) {
            diagonallyLines_2( x, false);
            diagonallyLines_2( x, true);
        }
    }

    /**
     * Поиск перпендикулярных (горизонтальных и вертикальных) линий.
     *
     * @param isVertical параметр, в зависисмости от значения которого будет выполяняться поиск линий
     * (по горизонтали или вертикали)
     */
    private void perpendicularSearch(boolean isVertical) {
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) ->
                isVertical ? (curr.getYy() + 1 == next.getYy()) : (curr.getXx() + 1 == next.getXx());

        for (int x = 1; x <= sideLength; x++) {
            Map<String, List<Cell>>  colorSequenceMap = new HashMap<>();

            for (int y = 1; y <= sideLength; y++) {
                Cell nextCell = isVertical ? getCellMap().get(new Pair<>(x, y)) : getCellMap().get(new Pair<>(y, x));
                String color = nextCell.containsImage() ? nextCell.getImageColor() : "";

                List<Cell> images = color.isEmpty() ? null : colorSequenceMap.get(color);
                images = Objects.isNull(images) ? new ArrayList<>() : colorSequenceMap.get(color);
                images.add(nextCell);
                if ( !color.isEmpty() ) {
                    colorSequenceMap.put(color, images);
                }
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
     *
     * @param x координата X ячейки
     * @param isOpposite boolean-значение, указывающее на конфигурацию поиска линий
     * @param function функция, которая выражает Y через Х (линейная функция)
     * @param oppositeFunction функция, которая выражает Y через X, но с помощью другой формулы, нежели function
     * @param sequenceMap карта, где Key = цвет, Value = последовательность (список) ячеек одного цвета
     */
    private void lineSequence(int x,
                              boolean isOpposite,
                              Function<Integer, Integer> function,
                              Function<Integer, Integer> oppositeFunction,
                              Map<String, List<Cell>> sequenceMap) {

        int y = isOpposite ? oppositeFunction.apply(x) : function.apply(x);
        Cell nextCell = getCellMap().get(new Pair<>(x, y));
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
     *
     * @param sequence последовательность изображений, в порядке их добавления
     * @param predicate условие, по которому будут сравниваться ячейки из строки
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
     *
     * @param line коллекция, содержащая ячейки, изображения из которых необходимо удалить.
     */
    private void deleteImagesFromCells(Collection<Cell> line) {
        playLogger.info("Line of " + line.size() + " balls was deleted!");
        GuiManager.getInfoLabel().setText("Линия из " + line.size() + " шаров удалена!");
        setLineState(true); // значение true означает, что срока удалена
        line.forEach( cell -> { // последовательное удаление изображений из ячеек
            cell.setIcon(null);
            cell.setState(EMPTY);
            getEmptyCells().add(cell);
        });
        GameHelper.accuralPoints(line.size()); // начисление очков
        line.clear(); // очистка коллекции
    }

    /**
     * Обход графа пустых ячеек (массив или область пустых ячеек на игровом поле, в которой находится
     * ячейка, из которой планируется переместить изображение), с целью "посетить" все пустые ячейки в заданной
     * области. Если среди "посещенных" ячеек будет находиться пустая ячейка в которую планируется переместить
     * изображение, то ход (перемещение) возможен, иначе - ход невозможен.
     * @param node вершина графа, она же - ячейка из которой перемещается изображение.
     * @return true - ход в выбранную ячейку возможен или false - ход невозможен.
     */
    private boolean traverse(Cell node) {
        Function<Cell, List<Cell>> findNeighbors = (cell) ->
                Objects.isNull(cell) ? Collections.emptyList() : cell.getNeighbors();
        Predicate<Cell> predicate = child -> child.getState() == EMPTY && !visited.contains(child);
        findNeighbors.apply(node).stream().filter(predicate).forEach( child -> {
            visited.add(child);
            queue.offer(child);
            traverse( queue.poll() );
        });
        return visited.contains(targetCell);
    }

    /**
     * Перемещение изображения из одной ячейки в другую.
     *
     * @param previousCell предыдущая ячейка (с изображением)
     * @param currentCell текущая (пустая) ячейка.
     */
    private void moveImageCell(Cell previousCell, Cell currentCell) {
        // получаем изображение из предыдущей ячейки
        String pictureColor = previousCell.getImageColor();
        // устанавливаем изображение в пустую ячейку
        currentCell.setIcon(ballsMap().get(pictureColor));
        // удаляем изображение из предыдущей ячейки
        previousCell.setIcon(null);
        // меняем состояния предыдущей и текущей ячеек
        previousCell.setState(EMPTY);
        currentCell.setState(RELEASED);
        // добавляем предыдущую ячейку в список свободных ячеек и удаляем текущую ячейку
        getEmptyCells().add(previousCell);
        getEmptyCells().remove(currentCell);
    }
}