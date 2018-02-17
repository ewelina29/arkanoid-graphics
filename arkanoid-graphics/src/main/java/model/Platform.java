package model;

public class Platform {
    private int length;
    private int oldLength;
    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private int lives;

    public static final int movement = 1;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Platform() {
        this.length = Dimensions.PLATFORM_LENGTH_NORMAL;
        this.oldLength = Dimensions.PLATFORM_LENGTH_NORMAL;
        this.lives = 3;
        this.x = Dimensions.PLATFORM_INITIAL_POSITION_HORIZONTAL;
        this.y = Dimensions.PLATFORM_INITIAL_POSITION_VERTICAL;
        this.oldX = Dimensions.PLATFORM_INITIAL_POSITION_HORIZONTAL;
        this.oldY = Dimensions.PLATFORM_INITIAL_POSITION_VERTICAL;

    }

    public int getOldLength() {
        return oldLength;
    }

    public void setNewPosition(int newX, int newY, int newLength) {
        this.oldX = this.x;
        this.oldY = this.y;
        this.oldLength = this.length;
        this.x = newX;
        this.y = newY;
        this.length = newLength;


    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getX() {
        return x;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getY() {
        return y;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }


}
