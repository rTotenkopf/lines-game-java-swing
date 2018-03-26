package com.game.robot.exceptions;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class RobotConnectionException extends RuntimeException {

    public RobotConnectionException(String message) {
        super(message);

    }

    public RobotConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}