package com.game.robot.logic;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public interface Movable {

    void setDirection(String direction);

    void turnLeft();

    void turnLeft(int amount);

    void turnRight();

    void turnRight(int amount);

    void stepForward();
}
