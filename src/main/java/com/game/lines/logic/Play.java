package com.game.lines.logic;

import com.game.lines.common.Common;
import com.game.lines.entity.Cell;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * @author Eugene Ivanov on 24.04.18
 */

public class Play {

    // Добавляем логгер игрового процесса.
    private Logger playLogger = Logger.getLogger(Play.class.getName());

    private Cell previousCell;
    private Cell currentCell;
    private List<Cell> neighborsOfCell;
    private Set<Cell> allEmptyNeighborsCells = new LinkedHashSet<>();

    private Play() {
        generateRandomImages();
    }

    private Play(Cell previousCell, Cell currentCell) {
        this.previousCell = previousCell;
        this.currentCell = currentCell;
        this.neighborsOfCell = previousCell.getNeighbors();

        int i = (int) neighborsOfCell.stream().filter(e -> e.getState() == State.EMPTY).count();

        Predicate<Integer> cellUnblocked = e -> e != 0;

        if (cellUnblocked.test(i)) {
            moveImageCell(previousCell, currentCell);
            generateRandomImages();
            playLogger.severe("Move complete.");
        } else {
            playLogger.severe("Move was blocked, because empty neighbors = { " + i + " }");
        }

//        checkAbilityToMove( neighborsOfCell );

        Common.emptyCells.clear();
    }

    public static void getBalls() {
        new Play();
    }

    public static void getMove(Cell previousCell, Cell currentCell) {
        new Play( previousCell, currentCell );
    }

    /**
     * Проверка возможности хода (перемещения изображения из непустой ячейки в пустую).
     * Если пустая ячейка, в которую игрок хочет переместить изображение, "заблокирована" другими ячейками
     * содержащими изображения, то ход невозможен.
     * @param setOfCells множество ячеек.
     */
    private void checkAbilityToMove(Collection<Cell> setOfCells) {
        Set<Cell> emptyCellSet = new LinkedHashSet<>();

        setOfCells.forEach( e -> {
            if (e.getState() == State.EMPTY) {
                allEmptyNeighborsCells.add(e);

                emptyCellSet.add(e);
                if (allEmptyNeighborsCells.size() < Cell.emptyCells.size()) {
                    decomposition(e);
                }
            }
        });
    }

    private void decomposition(Cell emptyCell) {
        int emptyNeighbors = (int) neighborsOfCell.stream().filter(e -> e.getState() == State.EMPTY).count();

        if (emptyNeighbors != 0) {
            checkAbilityToMove( emptyCell.getNeighbors() );
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