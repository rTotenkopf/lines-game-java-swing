package com.game.lines.logic;

import com.game.lines.common.Common;
import com.game.lines.entity.Cell;

import java.util.List;

/**
 * @author Eugene Ivanov on 22.04.18
 */

public interface Playable {
    /**
     * Перемещение изображения из одной ячейки в другую.
     * @param currentCell предыдущая ячейка (с изображением) и текущая (пустая) ячейка.
     */
    static void moveImageCell(Cell previousCell, Cell currentCell) {
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

        generateRandomImages();
    }

    // Заполнение изображениями 3-х пустых случайных ячеек.
    static void generateRandomImages() {
        for (int i = 0; i < 3; i++) {
            Cell cell = getRandomCell(Cell.emptyCells);
            int index = (int) (Math.random() * Common.PICTURES.length);
            cell.setIcon(Common.PICTURES[index] );
            cell.setState(State.RELEASED);
            Cell.emptyCells.remove(cell);
        }
    }

    // Получение случайной свободной ячейки.
    static Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }

    // Проверка возможности хода (перемещения изображения из непустой ячейки в пустую).
    // Если пустая ячейка, в которую игрок хочет переместить изображение, "заблокирована" другими ячейками
    // содержащими изображения, то ход невозможен.
    static boolean checkAbilityToMove() {

        return true;
    }
}