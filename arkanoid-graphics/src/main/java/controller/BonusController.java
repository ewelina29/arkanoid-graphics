package controller;

import model.Bonus;
import model.Brick;
import model.Dimensions;
import model.modelEnum.BallSpeed;
import model.modelEnum.BonusType;
import model.modelEnum.BrickDifficulty;
import view.GameView;

public class BonusController {

    public void moveBonus(Bonus bonus) {
        int[][] brickList = GameView.brickController.getBricksIds();
        int platformStart = GameView.platform.getX();
        int platformEnd = GameView.platform.getX() + GameView.platform.getLength();
        int xBonus = bonus.getX();
        int yBonus = bonus.getY();
        int length;
        if (bonus.getBonusType() == BonusType.EXTRA_LIFE) {
            length = Dimensions.SCALE_Y;
        } else if (bonus.getBonusType() == BonusType.EXTRA_POINTS) {
            length = 5*Dimensions.SCALE_Y;
        } else length = 3*Dimensions.SCALE_Y;


        if (yBonus+Dimensions.SCALE_Y != Dimensions.HEIGHT) {
            if (yBonus + 10 == GameView.platform.getY()) {
                if ((xBonus >= platformStart && xBonus <= platformEnd) || ((xBonus+length) >= platformStart && (xBonus+length) <= platformEnd )) {
                    startBonus(bonus);
                    GameView.clearUsedBonus(bonus);
                    bonus.setUsed(true);
                } else {
                    bonus.setNewY((yBonus + 1));
                    GameView.redrawBonus(bonus);
                }
            } else if (brickList[yBonus + 10][xBonus] == -1) {
                bonus.setNewY((yBonus + 1));
                GameView.redrawBonus(bonus);
            } else {
                if (brickList[yBonus + 10][xBonus] >= 0) {
                    bonus.setNewY((yBonus + 3*Dimensions.SCALE_Y));
                    GameView.redrawBonus(bonus);
                }
            }
        } else {
            bonus.setUsed(true);
            GameView.clearUsedBonus(bonus);
        }
    }

    private static void startBonus(Bonus bonus) {
        switch (bonus.getBonusType()) {

            case FASTER_BALL:
                if(GameView.ball.getSpeed()>=BallSpeed.FAST.speed) {
                    GameView.ball.setSpeed(GameView.ball.getSpeed() - 10);
                }
                break;
            case SLOWER_BALL:
                if(GameView.ball.getSpeed()<=BallSpeed.SLOW.speed) {
                    GameView.ball.setSpeed(GameView.ball.getSpeed() + 10);
                }
                break;
            case LONGER_PLATFORM:
                GameView.platform.setLength(Dimensions.PLATFORM_LENGTH_LONG);
                GameView.redrawPlatform(true);
                break;
            case SHORTER_PLATFORM:
                GameView.platform.setLength(Dimensions.PLATFORM_LENGTH_SHORT);
                GameView.redrawPlatform(true);
                break;
            case EXTRA_LIFE:
                GameView.platform.setLives(GameView.platform.getLives() + 1);
                break;
            case EXTRA_POINTS:
                view.GameView.score += 1000;
                break;
            case ALL_GLASS:
                changeBricksDifficulty(BrickDifficulty.GLASS);
                break;
            case ALL_DIAMOND:
                changeBricksDifficulty(BrickDifficulty.DIAMOND);
                break;
        }
    }

    private static void changeBricksDifficulty(BrickDifficulty difficulty) {
        for (Brick b : GameView.bricks) {
            if (b.isVisible()) {
                b.setDifficulty(difficulty);
                GameView.drawBrick(b, difficulty,true);
            }
        }

    }
}
