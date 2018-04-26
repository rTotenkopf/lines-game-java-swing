package com.game.lines.logic;

import com.game.lines.common.Common;
import com.game.lines.entity.Cell;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Eugene Ivanov on 24.04.18
 */

public class Play {

    // Добавляем логгер игрового процесса.
    private Logger playLogger = Logger.getLogger(Play.class.getName());
    // Ячейка, изображение которой необходимо переместить в пустую ячейку, также является первой вершиной
    // или узлом графа ячеек.
    private Cell node;
    // Ячейка, в которую перемещаем изображение.
    private Cell target;
    // Список или массив соседних ячеек, для какой-либо ячейки.
    private List<Cell> neighbors;
    // Связанный список ячеек для реализации проверки возможности хода.
    private List<Cell> visited = new LinkedList<>();
    // Очередь, основанная на связанном списке, необходима для реализации проверки возможности хода.
    private Queue<Cell> queue = new LinkedList<>();

    // Конструктор класса по умолчанию.
    private Play() {
        generateRandomImages();
    }


    private Play(Cell previousCell, Cell currentCell) {
        this.node = previousCell;
        this.target = currentCell;
        this.neighbors = previousCell.getNeighbors();

        int i = (int) neighbors.stream().filter(e -> e.getState() == State.EMPTY).count();

        if (i != 0) {
            moveImageCell(previousCell, currentCell);
            generateRandomImages();
            playLogger.severe("Move complete.");
        } else {
            playLogger.severe("Move was blocked, because empty children = { " + i + " }");
        }

        visited.add( node );
        traverse( node );
    }

    public static void getBalls() {
        new Play();
    }

    public static void getMove(Cell previousCell, Cell currentCell) {
        new Play( previousCell, currentCell );
    }

    private void traverse(Cell node) {
        // Добавляем ячейку-родителя в конец очереди.
        queue.offer( node );
        // Получаем потомков, находящихся по соседству от ячейки-родителя.
        List<Cell> children = node.getNeighbors();

        // Если статус потомка "пустой", то добавляем её в список посещенных ячеек и в конец очереди.
        for (Cell child : children) {
            if (child.getState() == State.EMPTY && !visited.contains( child )) {
                visited.add( child );
                queue.offer( child );
            }
        }

        if ( !visited.contains( target ) && !queue.isEmpty() ) {
            queue.poll();
            traverse( queue.peek() );
        }
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

    // Заполнение изображениями 3-х пустых случайных ячеек.
    private void generateRandomImages() {
        for (int i = 0; i < 3; i++) {
            Cell cell = getRandomCell(Cell.emptyCells); // Получаем рандомную ячейку.
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
    private Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }
}