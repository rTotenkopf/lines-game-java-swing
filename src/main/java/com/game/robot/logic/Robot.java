package com.game.robot.logic;

import com.game.robot.connections.RobotConnection;
import com.game.robot.connections.RobotConnectionManager;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class Robot extends AbstractRobot implements RobotConnectionManager
{
    private static int totalSteps = 0;

    // Robot start position
    public Robot() {
        this("nw", 1, 1);
    }

    public Robot(String direction, int x, int y) {
        super(direction, x, y);
    }

    @Override
    public RobotConnection getConnection() {
        return new RobotConnection() {
            @Override
            public void moveRobotTo(int targetX, int targetY) throws InterruptedException {
                int stepX = getX();
                int stepY = getY();

                // assign NW (north-west) direction
                if (((stepX > targetX) && (stepY < targetY)) && ((stepX - targetX) == (targetY - stepY))) {
                    switch (getDirection()) {
                        case N :
                            turnLeft();
                            break;
                        case S :
                            turnRight(3);
                            break;
                        case W :
                            turnRight();
                            break;
                        case E :
                            turnLeft(3);
                            break;
                        case NE :
                            turnLeft(2);
                            break;
                        case SW :
                            turnRight(2);
                            break;
                        case SE :
                            turnRight(4);
                            break;
                    }
                }

                // assign NE (north-east) direction
                if ((targetX == targetY && getX() == getY()) && (targetX > getX() && targetY > getY())) {
                    switch (getDirection()) {
                        case N :
                            turnRight();
                            break;
                        case S :
                            turnLeft(3);
                            break;
                        case W :
                            turnRight(3);
                            break;
                        case E :
                            turnLeft();
                            break;
                        case NW :
                            turnRight(2);
                            break;
                        case SW :
                            turnRight(4);
                            break;
                        case SE :
                            turnLeft(2);
                            break;
                    }
                }

                // assign SW (south-west) direction
                if ((targetX == targetY && getX() == getY()) && (targetX < getX() && targetY < getY())) {
                    switch (getDirection()) {
                        case N :
                            turnLeft(3);
                            break;
                        case S :
                            turnRight();
                            break;
                        case W :
                            turnLeft();
                            break;
                        case E :
                            turnRight(3);
                            break;
                        case NW :
                            turnLeft(2);
                            break;
                        case NE :
                            turnRight(4);
                            break;
                        case SE :
                            turnRight(2);
                            break;
                    }
                }

                // assign SE (south-east) direction
                if (((stepX < targetX) && (stepY > targetY)) && ((targetX - stepX) == (stepY - targetY))) {
                    switch (getDirection()) {
                        case N :
                            turnRight(3);
                            break;
                        case S :
                            turnLeft();
                            break;
                        case W :
                            turnLeft(3);
                            break;
                        case E :
                            turnRight();
                            break;
                        case NW :
                            turnRight(4);
                            break;
                        case NE :
                            turnRight(2);
                            break;
                        case SW :
                            turnLeft(2);
                            break;
                    }
                }

                /*for (int i = 0; i < Math.abs(targetX - stepX); i++) {
                    stepForward();
                }*/

                for (; totalSteps < Math.abs(targetY - stepY); totalSteps++) {
                    stepForward();
                    Thread.sleep(1000);

                }

                if (getX() == targetX && getY() == targetY) {
                    System.out.print("\n======================");
                    System.out.print("\nSUCCESS!");
                    System.out.print("\nRobot was make " +  totalSteps + " step/s!");
                }
            }

            @Override
            public void close() {}
        };
    }
}
