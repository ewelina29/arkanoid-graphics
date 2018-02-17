package controller;

import com.almasb.fxgl.app.FXGL;
import model.Ball;
import model.Bonus;
import model.Dimensions;
import model.Platform;
import model.modelEnum.DirectionBall;
import view.GameView;

import java.util.Random;

public class GameController {

    private Ball ball;
    boolean lostLife = false;
    public boolean gameOver = false;

    public GameController(Ball ball) {
        this.ball = ball;

    }

    private void gameOver() {
        gameOver = true;
        GameView.printGameOver();
        ball.setInMove(false);
        GameView.score = 0;

    }

    public void startMoving(Platform platform) throws InterruptedException {

        if (!ball.isInMove()) {
            if (lostLife) {
                platform.setLives(platform.getLives() - 1);
                if (platform.getLives() > 0) {
                    ball.setNewPosition(Dimensions.BALL_INITIAL_POSITION_HORIZONTAL, Dimensions.BALL_INITIAL_POSITION_VERTICAL);
                    platform.setNewPosition(Dimensions.PLATFORM_INITIAL_POSITION_HORIZONTAL, Dimensions.PLATFORM_INITIAL_POSITION_VERTICAL, Dimensions.PLATFORM_LENGTH_NORMAL);
                    GameView.redrawOnStart();
                    lostLife = false;
                    GameView.startGame = true;
                } else {
                    gameOver();

                }
            } else {
                Random random = new Random();
                int randomDir = random.nextInt(10);
                if (randomDir < 5) {//piłka startuje w prawo
                    ball.setDirection(DirectionBall.UPRIGHT);
                    this.ball.setInMove(true);
                } else {//piłka startuje w lewo
                    ball.setDirection(DirectionBall.UPLEFT);
                    this.ball.setInMove(true);
                }
            }
        } else {
            if (GameView.brickController.brickCounter==0) {
                GameView.level++;

                GameView.generateNewLevel(platform.getLives());

            } else if(!GameView.bonusesList.isEmpty()){
                for (Bonus b: GameView.bonusesList){
                    if (!b.isUsed()){
                        GameView.bonusController.moveBonus(b);
                    }
                }
            }
            GameView.ballController.moveBall();
            GameView.redrawBall();
        }
    }

    }


