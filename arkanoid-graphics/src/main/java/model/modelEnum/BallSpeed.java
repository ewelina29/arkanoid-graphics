package model.modelEnum;

public enum BallSpeed {
    NORMAL (110),
    FAST (60),
    SLOW (180);

    public int speed;

    private BallSpeed(int speed){
        this.speed = speed;
    }



}
