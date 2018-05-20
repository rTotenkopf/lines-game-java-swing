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

    // Длина стороны сетки игрового поля.
    private static int sideLength;
    // Ячейка, в которую перемещаем изображение.
    private static Cell target;
    // Связанный список ячеек для реализации проверки возможности хода.
    private static List<Cell> visited;
    // Очередь, основанная на связанном списке, необходима для реализации проверки возможности хода.
    private static Queue<Cell> queue;
    // В эту переменную устанавливается значение true, если ход возможен, иначе - значение false.
    private static boolean moveAbility;
    // Переменная принимает значени true, если строка была удалена.
    private static boolean lineDeleted;
    // Множество ячеек, представляющее линию из шаров одинакового цвета.
    // Когда линия достигает определенной (в зависимости от настроек игры) длины, то она удалется и ячейки
    // снова становятся пустыми.
    private Set<Cell> line;

    /**
     * Метод отвечает за один игровой ход (перемещение изображения в пустую ячейку).
     * @param filledCell ячейка, из которой необходимо переместить изображение.
     * @param emptyCell пустая ячейка, в которую необходимо переместить изображение.
     * @return значение boolean-типа означающее возможность или невозможность хода в выбранную ячейку.
     */
    public static boolean getMove(Cell filledCell, Cell emptyCell) {
        new Play( filledCell, emptyCell );
        return moveAbility;
    }

    /**
     * Конструктор класса Play, отвечающего за игровой процесс, принимает в качестве аргументов 2 ячейки:
     * @param filledCell ячейка, из которой необходимо переместить изображение.
     * @param emptyCell пустая ячейка, в которую необходимо переместить изображение.
     */
    private Play(Cell filledCell, Cell emptyCell) {
        sideLength = Cell.getGridLength();
        target = emptyCell;
        visited = new LinkedList<>();
        queue = new LinkedList<>();
        moveAbility = traverse(filledCell); // Получение результата выполнения метода traverse.
        lineDeleted = false;
        Logger playLogger = Logger.getLogger(Play.class.getName()); // Логирование игрового процесса.

        // Если ход возможен, то перемещаем изображения (выполняем ход), генерируем новые изображения
        // и выводим сообщение - "ход выполнен успешно". Иначе, выводим сообщение - "ход невозможен".
        if ( moveAbility ) {
            moveImageCell(filledCell, emptyCell);
            playLogger.info("Move complete!");
            linesSearch(); // Вызов метода для поиска всех сформированных линий.
            if (!lineDeleted) {
                generateRandomImages(3);
//                linesSearch(); // Вызов метода для поиска всех сформированных линий.
            }
        } else {
            playLogger.info("Move impossible..");
        }
    }

    private void linesSearch() {
        Predicate<Collection> linePredicate = collection ->
                line.size() >= 5 && line.size() <= Cell.getGridLength();

        straightLines(1, 2, linePredicate); // Поиск линий по вертикали.
        straightLines(2, 1, linePredicate); // Поиск линий по горизонтали.
        // Поиск линий по диагонали справа налево и снизу вверх с ++ сдвигом по оси Y и -- сдвигом по оси X.
        for (int x = 5; x <= sideLength ; x++) {
            diagonallyLines_1( x, false, linePredicate );
            diagonallyLines_1( x, true, linePredicate );
        }
        // Поиск линий по диагонали слева направо и сверху вниз с -- сдвигом по оси Y и ++ сдвигом по оси X.
        for (int x = sideLength - 4; x >= 2; x--) {
            diagonallyLines_2( x, false, linePredicate );
            diagonallyLines_2( x, true, linePredicate );
        }
    }

    private void straightLines(int x,
                               int y,
                               Predicate<Collection> linePredicate) {

        boolean vertical = x < y;
        x = x == 2 ? 1 : 1;
        y = y == 2 ? 1 : 1;
        Cell prevCell = Cell.cellMap.get( new Pair<>(x, y) );
        Cell nextCell;

        for (x = 1; x <= sideLength; x++) {
            line = new HashSet<>();

            for (y = 1; y <= sideLength; y++) {
                nextCell = vertical ? Cell.cellMap.get(new Pair<>(x, y)) : Cell.cellMap.get(new Pair<>(y, x));
                checkCellSequence(prevCell, nextCell, line);
                prevCell = nextCell;
            }
//                System.out.println("line.size() = " + line.size());
            if ( linePredicate.test(line) ) {
                deleteImagesFromCells(line);
            }
        }
    }

    private void diagonallyLines_1(int start_X,
                                  boolean isOpposite,
                                  Predicate<Collection> linePredicate) {

        Function<Integer, Integer> linearFunction = number -> -1 * number + start_X + 1;
        Function<Integer, Integer> oppositeLinearFunction = number -> sideLength + (number - start_X);
        int y = isOpposite ? oppositeLinearFunction.apply(start_X) : linearFunction.apply(start_X);
        Cell prevCell = Cell.cellMap.get( new Pair<>( start_X, y ));
        Cell nextCell;
        line = new HashSet<>();

        for (int x = start_X; x >= 1; x--) {
            y = isOpposite ? oppositeLinearFunction.apply(x) : linearFunction.apply(x);
            nextCell = Cell.cellMap.get(new Pair<>(x, y));
//                nextCell.setBackground(Color.YELLOW); // visualize algorithm
            checkCellSequence(prevCell, nextCell, line);
            prevCell = nextCell;
        }
        if ( linePredicate.test(line) ) {
            deleteImagesFromCells(line);
        }

    }

    private void diagonallyLines_2(int start_X,
                                   boolean isOpposite,
                                   Predicate<Collection> linePredicate) {

        Function<Integer, Integer> function = number -> Math.abs(number - (start_X - 1) - (sideLength + 1));
        Function<Integer, Integer> oppositeFunction = number -> number - start_X + 1;
        int y = isOpposite ? oppositeFunction.apply(start_X) : function.apply(start_X);
        Cell prevCell = Cell.cellMap.get( new Pair<>(start_X, y));
        Cell nextCell;
        line = new HashSet<>();

        for (int x = start_X; x <= sideLength; x++) {
            y = isOpposite ? oppositeFunction.apply(x) : function.apply(x);
            nextCell = Cell.cellMap.get(new Pair<>(x, y));
//                nextCell.setBackground(Color.YELLOW); // visualize algorithm
            checkCellSequence(prevCell, nextCell, line);
            prevCell = nextCell;
        }
        if ( linePredicate.test(line) ) {
            deleteImagesFromCells(line);
        }
    }

    private void checkCellSequence(Cell prevCell, Cell nextCell, Collection<Cell> line) {
        if ( (nextCell.containsImage() && prevCell.containsImage()) &&
                nextCell.getImageColor().equals(prevCell.getImageColor()) )
        {
            line.add(prevCell);
            line.add(nextCell);
//            System.out.println("line.size() = " + line.size());
        } else if (line.size() < 5) {
            line.clear();
        }
//        if (line.size() == 5) {
//            deleteImagesFromCells(line);
//        }
    }

    private void deleteImagesFromCells(Collection<Cell> line) {
        System.out.println("line.size() = " + line.size());
        lineDeleted = true;
        line.forEach( cell -> {
            cell.setIcon(null);
            cell.setState(State.EMPTY);
            Cell.emptyCells.add(cell);
        });
        line.clear();
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
        return visited.contains(target);
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
        // Добавляем предыдущую ячейку в список свободных ячеек и удаляем из этого списка текущую ячейку.
        Cell.emptyCells.add(previousCell);
        Cell.emptyCells.remove(currentCell);
    }

    // Заполнение изображениями N пустых случайных ячеек.
    public static void generateRandomImages(int cells) {
        for (int i = 0; i < cells; i++) {
            Cell cell = getRandomCell(Cell.emptyCells); // Получаем рандомную ячейку из массива пустых ячеек.
            int index = (int) (Math.random() * ResourceManger.BALLS.length); // Подбираем случайный индекс.
            cell.setIcon( (ImageIcon) ResourceManger.BALLS[index] ); // Устанавливаем случайное изображение в ячейку.
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