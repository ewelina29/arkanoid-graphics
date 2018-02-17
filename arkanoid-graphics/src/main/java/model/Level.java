package model;


import model.modelEnum.LevelDifficulty;

import java.util.Random;

public class Level {

    private int number;
    private LevelDifficulty levelDifficulty;
    private int visibleBricks;
    private int bonusCounter;

    public Level(int number) {
        this.number = number;
        setLevelDifficulty();
        setVisibleBricks();
        setBonusCounter();
    }

    private void setLevelDifficulty() {
        if (number <= 3) {
            this.levelDifficulty = LevelDifficulty.EASY;
        } else if (number <= 6) {
            this.levelDifficulty = LevelDifficulty.MEDIUM;
        } else {
            this.levelDifficulty = LevelDifficulty.HARD;
        }

    }

    public LevelDifficulty getLevelDifficulty() {
        return levelDifficulty;
    }

    public int getVisibleBricks() {
        return visibleBricks;
    }

    private void setVisibleBricks() {
        switch (this.levelDifficulty) {
            case EASY:
                this.visibleBricks = (int) (Dimensions.MAX_BRICKS * 0.8);
                break;
            case MEDIUM:
                this.visibleBricks = (int) (Dimensions.MAX_BRICKS * 0.9);
                break;
            case HARD:
                this.visibleBricks = Dimensions.MAX_BRICKS;
                break;
            default:
                this.visibleBricks = Dimensions.MAX_BRICKS;
                break;

        }
    }

    public int getBonusCounter() {
        return bonusCounter;
    }

    private void setBonusCounter() {
        Random random = new Random();
        switch (this.levelDifficulty) {
            case EASY:
                this.bonusCounter = 10;
                break;
            case MEDIUM:
                this.bonusCounter = 15;
                break;
            case HARD:
                this.bonusCounter = 18;

        }

    }
}
