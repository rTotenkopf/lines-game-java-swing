package com.game.lines.logic;

/**
 * Состояния отдельной игровой ячейки
 */
public enum State {
    /**
     * Ячейка выбрана
     */
    SELECTED,
    /**
     * Ячейка освобождена
     */
    RELEASED,
    /**
     * Ячейка пуста
     */
    EMPTY
}