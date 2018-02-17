package model;

import model.modelEnum.BonusType;

public class Bonus {
    private int x;
    private int y;
    private int previousY;
    private boolean used;
    private BonusType bonusType;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Bonus(int xBrick, int yBrick, BonusType bonusType) {
        this.x = xBrick;
        this.y = yBrick;
        this.bonusType = bonusType;
        this.used = false;

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

    public void setNewY(int newY) {
        this.previousY = this.y;
        this.y = newY;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public int getPreviousY() {
        return previousY;
    }
}
