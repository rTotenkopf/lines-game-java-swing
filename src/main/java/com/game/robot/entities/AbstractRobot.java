package com.game.robot.entities;

import com.game.robot.enums.Direction;
import com.game.robot.logic.Movable;

public class AbstractRobot implements Movable
{
    private Direction direction;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public AbstractRobot(String dir, int valueX, int valueY) {
        setDirection(dir);
        this.x = valueX;
        this.y = valueY;
        System.out.print("Direction: " + getDirection() + "\nstart_X:   " + this.x + "\nstart_Y:   " + this.y);
    }

    @Override
    public void setDirection(String direction) {
        // Установка позиции робота в соответствии с "заданием"
        switch (direction.toLowerCase()) {
            case "n" : this.direction = Direction.N;
                break;
            case "s" : this.direction = Direction.S;
                break;
            case "w" : this.direction = Direction.W;
                break;
            case "e" : this.direction = Direction.E;
                break;
            case "nw" : this.direction = Direction.NW;
                break;
            case "ne" : this.direction = Direction.NE;
                break;
            case "sw" : this.direction = Direction.SW;
                break;
            case "se" : this.direction = Direction.SE;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void turnLeft() {
        this.turnLeft(1);
    }

    @Override
    public void turnLeft(int amount) {
        // Поворот робота влево в соответствии с текущим направлением взгляда
        for (int i = 0; i < amount; i++) {
            switch (getDirection()) {
                case N:
                    setDirection("NW");
                    break;
                case S:
                    setDirection("SE");
                    break;
                case W:
                    setDirection("SW");
                    break;
                case E:
                    setDirection("NE");
                    break;
                case NW:
                    setDirection("W");
                    break;
                case NE:
                    setDirection("N");
                    break;
                case SW:
                    setDirection("S");
                    break;
                case SE:
                    setDirection("E");
            }
            System.out.print("\nNextDir: " + getDirection());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void turnRight() {
        this.turnRight(1);
    }

    @Override
    public void turnRight(int amount) {
        // Поворот робота вправо в соответствии с текущим направлением взгляда
        for (int i = 0; i < amount; i++) {
            switch (getDirection()) {
                case N:
                    setDirection("NE");
                    break;
                case S:
                    setDirection("SW");
                    break;
                case W:
                    setDirection("NW");
                    break;
                case E:
                    setDirection("SE");
                    break;
                case NW:
                    setDirection("N");
                    break;
                case NE:
                    setDirection("E");
                    break;
                case SW:
                    setDirection("W");
                    break;
                case SE:
                    setDirection("S");
            }
            System.out.print("\nNextDir: " + getDirection());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stepForward() {
        // Один шаг робота вперед в соответствии с текущим направлением взгляда
        switch (getDirection()) {
            case N :
                this.y++;
                break;
            case S :
                this.y--;
                break;
            case W :
                this.x--;
                break;
            case E :
                this.x++;
                break;
            case NW :
                this.x--;
                this.y++;
                break;
            case NE :
                this.x++;
                this.y++;
                break;
            case SW :
                this.x--;
                this.y--;
                break;
            case SE :
                this.x++;
                this.y--;
        }
        System.out.print("\nNextPos: X= " + getX() + "; Y= " + getY());
    }
}
