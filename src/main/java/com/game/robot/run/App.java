package com.game.robot.run;

import com.game.robot.entities.Robot;
import com.game.robot.exceptions.RobotConnectionException;
import com.game.robot.connections.RobotConnection;
import com.game.robot.connections.RobotConnectionManager;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class App
{
    public static void main(String[] args) throws InterruptedException {
        RobotConnectionManager manager = new Robot();

        // Robot finish poition
        moveRobot(manager, 1,5 );
    }

    private static void moveRobot(RobotConnectionManager manager, int toX, int toY) throws InterruptedException {
        int count = 0;
        int maxAttempts = 3;
        boolean completed = false;

        while (!completed) {
            try (RobotConnection rc = manager.getConnection()) {
                rc.moveRobotTo(toX, toY);
                completed = true;
            } catch (RobotConnectionException r) {
                if (++count == maxAttempts) {
                    throw new RobotConnectionException("Connection failed");
                }
            }
        }
    }
}