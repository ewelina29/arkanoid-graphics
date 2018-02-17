package controller;

import model.Bonus;
import model.Brick;
import model.Dimensions;
import model.Level;
import model.modelEnum.BonusType;
import model.modelEnum.BrickDifficulty;
import model.modelEnum.LevelDifficulty;
import view.GameView;

import java.util.ArrayList;
import java.util.Random;

public final class BrickController {

    private ArrayList<Brick> bricks;
    private static int[][] bricksIds;
    private Random random;
    private int counterX;
    private int counterY;
    int brickCounter;

    public BrickController(int level) {

        bricks = new ArrayList<>();
        random = new Random();
        counterX = Dimensions.BRICKS_IN_COLUMN;
        counterY = Dimensions.BRICKS_IN_ROW;
        bricksIds = new int[Dimensions.HEIGHT + 1][Dimensions.WIDTH + 1];
        fillBricksIds();
        setBricksPositions();
        createBricks(new Level(level));
        updateBrickIds();
    }

    void updateBrick(int id) {
        if (id > -1) {
            Brick brick = bricks.get(id);
            checkBonus(brick);
            updateDifficulty(brick);
        }
    }

    private static void checkBonus(Brick brick) {
        if (brick.getBonus() != null) {
            Bonus b = new Bonus(brick.getX(), brick.getY(), brick.getBonus());
            GameView.bonusesList.add(b);
        }

        brick.setBonus(null);
    }


    private void fillBricksIds() {
        for (int i = 0; i < Dimensions.HEIGHT + 1; i++) {
            for (int j = 0; j < Dimensions.WIDTH + 1; j++) {
                bricksIds[i][j] = -1;
            }
        }
    }

    private void findBrickArea(Brick b, int index) {
        int brickX = b.getX();
        int brickY = b.getY();

        for (int i = brickY; i < (Dimensions.BRICK_HEIGHT + brickY); i++) {
            for (int j = brickX; j < (Dimensions.BRICK_WIDTH + brickX); j++) {
                bricksIds[i][j] = index;
            }
        }
    }

    private void setBricksPositions() {
        int x = 3*Dimensions.SCALE_X, y = 4*Dimensions.SCALE_Y;
        for (int i = 0; i < counterX; i++) {
            for (int j = 0; j < counterY; j++) {
                Brick b = new Brick(x, y);
                int index = bricks.indexOf(b);
                bricks.add(b);
                findBrickArea(b, index);
                x += Dimensions.BRICK_WIDTH + Dimensions.MARGIN;
            }
            x = 3*Dimensions.SCALE_X;
            y += 3*Dimensions.SCALE_Y;
        }

    }

    private void updateBrickIds() {
        for (Brick b : bricks) {
            int index = bricks.indexOf(b);
            if (b.isVisible()) {
                findBrickArea(b, index);
                brickCounter++;
            } else {
                findBrickArea(b, -1);

            }
        }
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    private Brick findRandomBrick() {
        int rand = random.nextInt(Dimensions.MAX_BRICKS - 1) + 1;
        int x = random.nextInt(rand);
        int y = random.nextInt(rand);
        return bricks.get(rand);
    }

    private int assignBonus(Brick brick, int bonuses) {

        if (bonuses > 0) {
            if (random.nextBoolean()) {
                BonusType bonus = generateBonus();
                brick.setBonus(bonus);
                bonuses--;
            }
        }
        return bonuses;
    }

    private void createBricks(Level level) {
        LevelDifficulty levelDifficulty = level.getLevelDifficulty();

        int visibleBricks = level.getVisibleBricks();

        int bonuses = level.getBonusCounter();

        if (Dimensions.MAX_BRICKS == visibleBricks) {

            for (Brick brick : bricks) {
                BrickDifficulty brickDifficulty = generateBrickDifficulty(levelDifficulty);
                brick.setDifficulty(brickDifficulty);
                brick.setVisible(true);
                bonuses = assignBonus(brick, bonuses);

            }
        } else {
            while (visibleBricks > 0) {

                Brick brick = findRandomBrick();
                while (brick.getDifficulty() != BrickDifficulty.NONE) {
                    brick = findRandomBrick();
                }

                BrickDifficulty brickDifficulty = generateBrickDifficulty(levelDifficulty);
                brick.setVisible(true);
                brick.setDifficulty(brickDifficulty);
                bonuses = assignBonus(brick, bonuses);
                visibleBricks--;
            }
        }
    }

    private BonusType generateBonus() {
        int value = random.nextInt(9);
        for (BonusType bonus : BonusType.values()) {
            if (value == bonus.value) {
                return bonus;
            }
        }
        return null;

    }

    private BrickDifficulty generateBrickDifficulty(LevelDifficulty levelDifficulty) {
        int brickHits;
        switch (levelDifficulty) {
            case EASY:
                brickHits = random.nextInt(3) + 1;
                break;
            case MEDIUM:
                brickHits = random.nextInt(4) + 2;
                break;
            case HARD:
                brickHits = random.nextInt(5) + 3;
                break;
            default:
                brickHits = random.nextInt(5) + 1;

        }
        for (BrickDifficulty b : BrickDifficulty.values()) {
            if (b.hits == brickHits) {

                return b;
            }

        }
        return BrickDifficulty.GLASS;
    }

    private void updateDifficulty(Brick brick) {
        BrickDifficulty difficulty = brick.getDifficulty();

        switch (difficulty) {
            case GLASS:
                brick.setDifficulty(BrickDifficulty.NONE);
                GameView.redrawBrick(bricks.indexOf(brick));
                brick.setVisible(false);
                findBrickArea(brick, -1);
                GameView.score += 50;
                brickCounter--;
                break;
            case WOOD:
                brick.setDifficulty(BrickDifficulty.GLASS);
                GameView.redrawBrick(bricks.indexOf(brick));
                GameView.score += 100;
                break;
            case ROCK:
                brick.setDifficulty(BrickDifficulty.WOOD);
                GameView.redrawBrick(bricks.indexOf(brick));
                GameView.score += 150;
                break;
            case METAL:
                brick.setDifficulty(BrickDifficulty.ROCK);
                GameView.redrawBrick(bricks.indexOf(brick));
                GameView.score += 200;
                break;
            case DIAMOND:
                brick.setDifficulty(BrickDifficulty.METAL);
                GameView.redrawBrick(bricks.indexOf(brick));
                GameView.score += 250;
                break;
        }

    }

    int[][] getBricksIds() {
        return bricksIds;
    }

}
