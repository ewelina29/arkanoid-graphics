package controller;

import com.almasb.fxgl.app.GameApplication;
import model.Ball;
import model.Dimensions;
import model.Platform;
import model.modelEnum.DirectionBall;
import view.GameView;

public class BallController {

    private Ball ball;

    public Ball getBall() {
        return ball;
    }

    public BallController() {
        this.ball = new Ball();
    }
    public void moveBall() {
        int[][] brickList = GameView.brickController.getBricksIds();
        int ballX = ball.getX();
        int ballY = ball.getY();
        if (ballY == Dimensions.HEIGHT-Dimensions.SCALE_X) {
            System.out.println(ballY);
            ball.setInMove(false);
            GameView.gameController.lostLife=true;
            return;
        }
        if (ball.getDirection() == DirectionBall.UPLEFT) {
            if (ballX == 1) {//kolizja ze ścianą
                setDirectionAndMove(DirectionBall.UPRIGHT);
            } else if (ballY == 40) {//kolizja z sufitem
                setDirectionAndMove(DirectionBall.DOWNLEFT);
            } else {
                if (brickList[ballY][ballX] >= 1) {//sprawdzenie czy kolizja nad
                    setDirectionAndMove(DirectionBall.DOWNLEFT);
                    GameView.brickController.updateBrick(brickList[ballY][(ballX)]);
                } else if (brickList[ballY][(ballX -1)] >= 0) {//kolizja obok
                    setDirectionAndMove(DirectionBall.UPRIGHT);
                    GameView.brickController.updateBrick(brickList[ballY][(ballX - 1)]);
                } else {
                    setDirectionAndMove(DirectionBall.UPLEFT);
                }
            }
        } else if (ball.getDirection() == DirectionBall.UPRIGHT) {

            if (ballX == Dimensions.WIDTH - Dimensions.SCALE_X) {//kolizja ze ścianą
                setDirectionAndMove(DirectionBall.UPLEFT);
            } else if (ballY == 40) {//kolizja z sufitem
                setDirectionAndMove(DirectionBall.DOWNRIGHT);

            } else {
                if (brickList[ballY][(ballX)] >= 0) {//sprawdzenie czy kolizja nad
                    System.out.println("nad   ballx");
                    setDirectionAndMove(DirectionBall.DOWNRIGHT);
                    GameView.brickController.updateBrick(brickList[ballY][(ballX)]);

                } else if (brickList[ballY][(ballX + Dimensions.SCALE_X)] >= 0) {//kolizja obok
                    setDirectionAndMove(DirectionBall.UPLEFT);
                    GameView.brickController.updateBrick(brickList[ballY][(ballX + Dimensions.SCALE_X)]);

                } else {
                    setDirectionAndMove(DirectionBall.UPRIGHT);
                }
            }
        } else if (ball.getDirection() == DirectionBall.DOWNLEFT) {

            //kolizja ze ścianą lub paletką w przeciwnym wypadku porusz
            if (ballX == 1) {//kolizja ze scianą
                setDirectionAndMove(DirectionBall.DOWNRIGHT);
            } else if (brickList[(ballY + 10)][ballX] >= 0) {//kolizje z cegla pod
                setDirectionAndMove(DirectionBall.UPLEFT);
                GameView.brickController.updateBrick(brickList[(ballY + Dimensions.SCALE_Y)][(ballX)]);

            } else if (brickList[(ballY)][(ballX-1)] >= 0) {//kolizje z cegłą po lewej
                setDirectionAndMove(DirectionBall.DOWNRIGHT);
                GameView.brickController.updateBrick(brickList[ballY][(ballX - 1)]);

            } else if ((ballY + 10) == GameView.platform.getY()) {//kolizja z paletką na poziomie y
                int platformStart = GameView.platform.getX();
                int platformEnd = GameView.platform.getX() + GameView.platform.getLength();
                if (ballX >= platformStart && ballX <= platformEnd) {//na poziomie x (czy piłka między x start, xend)
                    setDirectionAndMove(DirectionBall.UPLEFT);
                } else {
                    setDirectionAndMove(DirectionBall.DOWNLEFT);
                }
            } else {
                setDirectionAndMove(DirectionBall.DOWNLEFT);
            }

        } else if (ball.getDirection() == DirectionBall.DOWNRIGHT) {

            //kolizja ze ścianą lub paletką w przeciwnym wypadku porusz
            if (ballX == Dimensions.WIDTH - Dimensions.SCALE_X) {
                setDirectionAndMove(DirectionBall.DOWNLEFT);

            } else if (brickList[(ballY + 10)][ballX] >= 0) {//kolizje z cegla pod
                setDirectionAndMove(DirectionBall.UPRIGHT);
                GameView.brickController.updateBrick(brickList[(ballY + Dimensions.SCALE_Y)][(ballX)]);

            } else if (brickList[(ballY)][ballX + Dimensions.SCALE_X] >= 0) {//kolizje z cegłą po lewej
                setDirectionAndMove(DirectionBall.DOWNLEFT);
                GameView.brickController.updateBrick(brickList[(ballY)][ballX + Dimensions.SCALE_X]);
            } else if ((ballY + 10) == GameView.platform.getY()) {//kolizja z paletką na poziomie y
                int platformStart = GameView.platform.getX();
                int platformEnd = GameView.platform.getX() + GameView.platform.getLength();
                if (ballX >= platformStart && ballX <= platformEnd) {//na poziomie x (czy piłka między x start, xend)
                    setDirectionAndMove(DirectionBall.UPRIGHT);
                } else {
                    setDirectionAndMove(DirectionBall.DOWNRIGHT);
                }
            } else {
                setDirectionAndMove(DirectionBall.DOWNRIGHT);
            }
        }

    }

    private void setDirectionAndMove(DirectionBall direction) {
        switch (direction) {
            case UPLEFT:
                ball.setDirection(DirectionBall.UPLEFT);
                ball.setNewPosition((ball.getX()-1), (ball.getY()-1));
                break;
            case UPRIGHT:
                ball.setDirection(DirectionBall.UPRIGHT);
                ball.setNewPosition((ball.getX()+1), (ball.getY()-1));
                break;
            case DOWNLEFT:
                ball.setDirection(DirectionBall.DOWNLEFT);
                ball.setNewPosition((ball.getX()-1), (ball.getY()+1));
                break;
            case DOWNRIGHT:
                ball.setDirection(DirectionBall.DOWNRIGHT);
                ball.setNewPosition((ball.getX()+1), (ball.getY()+1));
                break;
        }
    }

}