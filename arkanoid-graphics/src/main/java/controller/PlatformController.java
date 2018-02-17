package controller;

import com.almasb.fxgl.app.GameApplication;
import model.Dimensions;
import model.Platform;
import model.modelEnum.Direction;
import view.GameView;

public class PlatformController {

    private Platform platform;

    public PlatformController() {
        this.platform = new Platform();

    }

    public Platform getPlatform() {
        return platform;
    }

    public void move(Direction direction){
        switch (direction){

            case UP:
                if(platform.getY() > 440){
                    platform.setY(platform.getY() - Platform.movement * 5);
                    platform.setY(platform.getY());
                    if (!GameView.ball.isInMove()) {
                        GameView.ball.setNewPosition(GameView.ball.getX(), GameView.ball.getY()-5);
                        GameView.redrawBall();
                    }
                }else {
                    platform.setY(440);
                    platform.setY(platform.getY());


                }

                break;
            case DOWN:
                if(platform.getY() < 600 - Dimensions.SCALE_Y){
                    platform.setY(platform.getY() + Platform.movement * 5);
                    platform.setY(platform.getY());
                    if (!GameView.ball.isInMove()) {
                        GameView.ball.setNewPosition(GameView.ball.getX(), GameView.ball.getY()+5);
                        GameView.redrawBall();
                    }
                }else {
                    platform.setY(600 - Dimensions.SCALE_Y);
                    platform.setY(platform.getY());

                }
                break;
            case LEFT:
                if(platform.getX()>0){
                    platform.setX(platform.getX() - Platform.movement * 5);
                    platform.setX(platform.getX());
                    if (!GameView.ball.isInMove()) {
                        GameView.ball.setNewPosition((GameView.ball.getX() - 5), GameView.ball.getY());
                        GameView.redrawBall();
                    }
                }
                else {
                    platform.setX(0);
                    platform.setX(0);


                }
                break;
            case RIGHT:
                if(platform.getX() + platform.getLength()  < Dimensions.WIDTH ){
                    platform.setX(platform.getX() + Platform.movement * 5);
                    platform.setX(platform.getX());
                    if (!GameView.ball.isInMove()) {
                            GameView.ball.setNewPosition((GameView.ball.getX() + 5), GameView.ball.getY());
                            GameView.redrawBall();
                    }


                }
                else {
                }
                break;
        }
    }


}
