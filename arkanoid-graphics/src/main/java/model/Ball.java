package model;

import model.modelEnum.BallSpeed;
import model.modelEnum.DirectionBall;

public class Ball {
    private int speed;
    private int x;
    private int y;
    private int previousX;
    private int previousY;
    private boolean inMove;
    private DirectionBall direction;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }



    public Ball() {
        this.x = Dimensions.BALL_INITIAL_POSITION_HORIZONTAL;
        this.previousX = Dimensions.BALL_INITIAL_POSITION_HORIZONTAL;
        this.y = Dimensions.BALL_INITIAL_POSITION_VERTICAL;
        this.previousY = Dimensions.BALL_INITIAL_POSITION_VERTICAL;
        this.speed = BallSpeed.NORMAL.speed;
        this.inMove = false;

    }

    public void setNewPosition(int newX, int newY) {
        this.previousX = this.x;
        this.previousY = this.y ;
        this.x = newX;
        this.y = newY ;

    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isInMove() {
        return inMove;
    }

    public void setInMove(boolean inMove) {
        this.inMove = inMove;
    }

    public DirectionBall getDirection() {
        return direction;
    }

    public void setDirection(DirectionBall direction) {
        this.direction = direction;
    }
}
