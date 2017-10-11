package model;

public class Ball {
    private BallSpeed speed;
    private BallMode mode;

    public BallSpeed getSpeed() {
        return speed;
    }

    public void setSpeed(BallSpeed speed) {
        this.speed = speed;
    }

    public BallMode getMode() {
        return mode;
    }

    public void setMode(BallMode mode) {
        this.mode = mode;
    }
}
