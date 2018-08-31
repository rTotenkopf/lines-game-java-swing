package com.game.lines.logic;

import java.awt.event.MouseListener;

/**
 * Интерфейс Clickable наследует класс MouseListener и позволяет сделать ячейки "кликабельными".
 * Кроме наследуемых, интерфейс предоставляет 2 своих собственных метода.
 *
 * @author Eugene Ivanov on 11.04.18
 */

public interface Clickable extends MouseListener {
    void select();  // Выбрать (выделить) объект.
    void release(); // Освободить (снять выделение) объект.
}