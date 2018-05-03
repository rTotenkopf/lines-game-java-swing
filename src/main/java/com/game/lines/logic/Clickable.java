package com.game.lines.logic;

import java.awt.event.MouseListener;

/**
 * Интерфейс Clickable наследует MouseListener (слушатель мыши) и делает объекты класса, реализующего интерфейс,
 * "кликабельными", т.е. с объектами можно будет выполнять действия с помощью кликов мыши.
 * Кроме наследуемых методов, интерфейс предоставляет 2 своих собственных метода.
 *
 * @author Eugene Ivanov on 11.04.18
 */

public interface Clickable extends MouseListener {
    void select();  // Выбрать (выделить) объект.
    void release(); // Освободить (снять выделение) объект.
}