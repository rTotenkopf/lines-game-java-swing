package com.game.robot.connections;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public interface RobotConnection extends AutoCloseable {

    void moveRobotTo(int x, int y) throws InterruptedException;

    @Override
    void close();
}
