package model;

public class Brick {
    private int x;
    private int y;
    private BrickDifficulty difficulty;

    public Brick(int x, int y, BrickDifficulty difficulty) {
        this.x = x;
        this.y = y;
        this.difficulty = difficulty;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDifficulty(BrickDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public BrickDifficulty getDifficulty() {
        return difficulty;
    }
}
