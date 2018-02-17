package model;

import model.modelEnum.BonusType;
import model.modelEnum.BrickDifficulty;

public class Brick {
    private int x;
    private int y;
    private BonusType bonus;
    private boolean isVisible;
    private BrickDifficulty difficulty;

    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
        this.difficulty = BrickDifficulty.NONE;
        this.isVisible = false;
        this.bonus = null;
    }
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public BonusType getBonus() {
        return this.bonus;
    }

    public void setBonus(BonusType bonus) {
        this.bonus = bonus;
    }


}
