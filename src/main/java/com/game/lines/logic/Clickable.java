package com.game.lines.logic;

import java.awt.event.MouseListener;

/**
 * Интерфейс Clickable наследует MouseListener и позволяет сделать ячейки "кликабельными".
 * Кроме наследуемых, интерфейс предоставляет 2 своих собственных метода.
 */
public interface Clickable extends MouseListener {
    void select();  // выбрать (выделить) объект
    void release(); // освободить (снять выделение) объект
}