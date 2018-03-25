package com.game.robot.connections;

public interface RobotConnection extends AutoCloseable {

    void moveRobotTo(int x, int y) throws InterruptedException;

    @Override
    void close();
}
