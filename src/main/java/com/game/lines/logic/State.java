package com.game.lines.logic;

/**
 * Перечисление State содержит все возможные игровые состояние ячейки.
 *
 * @author Eugene Ivanov on 13.04.18
 */

public enum State {
    SELECTED,   // Ячейка выбрана.
    RELEASED,   // Ячейка освобождена.
    EMPTY       // Ячейка пуста.
}