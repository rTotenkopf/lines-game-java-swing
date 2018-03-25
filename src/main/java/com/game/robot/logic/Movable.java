package com.game.robot.logic;

public interface Movable {

    void setDirection(String direction);

    void turnLeft();

    void turnLeft(int amount);

    void turnRight();

    void turnRight(int amount);

    void stepForward();
}
