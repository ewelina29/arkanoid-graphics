package view;

import com.almasb.fxgl.app.*;
import com.almasb.fxgl.asset.FXGLAssets;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.ScrollingBackgroundView;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import controller.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import model.modelEnum.BrickDifficulty;
import model.modelEnum.Direction;
import model.modelEnum.EntityType;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.Color.red;

public class GameView extends GameApplication {

    private GameApplication app = this;
    private static GameEntity platformEntity;
    private static GameEntity brickEntity;
    private static GameEntity ballEntity;
    private static GameEntity bonusEntity;
    private static HashMap<Brick, GameEntity> bricksEntities;
    private static HashMap<Bonus, GameEntity> bonusesEntities;

    private static Text scoreText;
    private static Text labelScoreText;
    private static Text livesText;
    private static Text labelLivesText;
    private static Text levelText;
    private static Text labelLevelText;
    private static SimpleIntegerProperty livesProperty;
    private static SimpleIntegerProperty levelProperty;
    private static SimpleIntegerProperty scoreProperty;

    public static Timer timer;
    public static ArrayList<Brick> bricks;
    public static BrickController brickController;
    public static BonusController bonusController;
    private static PlatformController platformController;
    public static BallController ballController;
    public static GameController gameController;
    public static Platform platform;
    public static Ball ball;
    public static ArrayList<Bonus> bonusesList;
    public static boolean startGame = true;
    private static boolean pause = false;
    public static int score = 0;
    public static int level = 14;
    private boolean started = false;
    public static Music music;
    public static Sound gameOverSound;
    public static Sound nextLevelSound;


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(Dimensions.WIDTH);
        settings.setHeight(Dimensions.HEIGHT);
        settings.setTitle("Arkanoid Graphics");
        settings.setVersion("0.1");
        settings.setIntroEnabled(false);
        settings.setMenuEnabled(true);
        settings.setProfilingEnabled(false);

    }

    @Override
    protected void initGame() {

        brickController = new BrickController(level);
        platformController = new PlatformController();
        ballController = new BallController();
        bonusController = new BonusController();
        bricks = brickController.getBricks();
        bricksEntities = new HashMap<>();
        bonusesEntities = new HashMap<>();
        platform = platformController.getPlatform();
        ball = ballController.getBall();
        bonusesList = new ArrayList<>();
        drawBackground();
        drawBricks();
        drawPlatform();
        drawBall();
        drawScore();

        gameController = new GameController(ball);

        music = FXGL.getAssetLoader().loadMusic("music.mp3");
        music.setCycleCount(Integer.MAX_VALUE);
        FXGL.getAudioPlayer().playMusic(music);

        gameOverSound = FXGL.getAssetLoader().loadSound("game_over.mp3");
        nextLevelSound = FXGL.getAssetLoader().loadSound("next_level.mp3");

        startTimer();
    }

    private void startTimer() {
        Timer timer = new Timer();
        int period = ball.getSpeed() / 10 - 1;
        System.out.println("PERIOd:" + period);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    try {
                        if (!startGame && !pause) {
                            gameController.startMoving(platform);
                            if (period != ball.getSpeed() / 10 - 1) {
                                timer.cancel();
                                startTimer();
                            }

                        }
                        else if (pause){
                            timer.cancel();

                        }
                         if(gameController.gameOver){
                            timer.cancel();
                            getAudioPlayer().stopMusic(music);
                            getAudioPlayer().playSound(gameOverSound);

                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 0, period);
    }

    public static void generateNewLevel(int lives) {

        brickController = new BrickController(level);
        ballController = new BallController();
        platformController = new PlatformController();
        bonusController = new BonusController();

        bricks = brickController.getBricks();
        platform = platformController.getPlatform();
        ball = ballController.getBall();

        platform.setLives(lives);

        gameController = new GameController(ball);
        startGame = true;
        bonusesList = new ArrayList<>();
        drawBricks();
        redrawOnStart();

    }

    @Override
    protected void initInput() {
        super.initInput();
        Input input = getInput();

        input.addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                if (!pause) {
                    platformController.move(Direction.RIGHT);
                    redrawPlatform();
                }

            }
        }, KeyCode.RIGHT);


        input.addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                if (!pause) {
                    platformController.move(Direction.LEFT);
                    redrawPlatform();
                }

            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                if (!pause) {
                    platformController.move(Direction.UP);
                    redrawPlatform();
                }
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                if (!pause) {
                    platformController.move(Direction.DOWN);
                    redrawPlatform();
                }
            }
        }, KeyCode.DOWN);

        input.addAction(new UserAction("Start") {
            @Override
            protected void onAction() {
                if (!pause) {
                    startGame = false;
                }
                if (gameController.gameOver){
                    startGame = true;
                    getAudioPlayer().stopMusic(music);

                    startNewGame();

                }


            }
        }, KeyCode.SPACE);

    }


    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        livesProperty.set(platform.getLives());
        scoreProperty.set(score);
        levelProperty.set(level);
    }


    private void drawBall() {
        ballEntity = Entities.builder()
                .type(EntityType.BALL)
                .at(ball.getX(), ball.getY())
                .viewFromTexture("ball.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
    }

    public static void redrawBall() {
        ballEntity.getPositionComponent().setX(ball.getX());
        ballEntity.getPositionComponent().setY(ball.getY());

    }

    private static void drawPlatform() {
        platformEntity = Entities.builder()
                .type(EntityType.PLATFORM)
                .at(platform.getX(), platform.getY())
                .viewFromTexture("platform/platform_normal.png")
                .buildAndAttach();
    }

    private static void redrawPlatform() {
        platformEntity.getPositionComponent().setX(platform.getX());
        platformEntity.getPositionComponent().setY(platform.getY());
    }

    public static void redrawPlatform(boolean changeLength) {
        int platformLength = platform.getLength();
        String texture;
        if (platformLength == Dimensions.PLATFORM_LENGTH_NORMAL) {
            texture = "platform/platform_normal.png";
        } else if (platformLength == Dimensions.PLATFORM_LENGTH_SHORT) {
            texture = "platform/platform_short.png";
        } else {
            texture = "platform/platform_long.png";
        }
        platformEntity.setViewFromTexture(texture);

        platformEntity.getPositionComponent().setX(platform.getX());
        platformEntity.getPositionComponent().setY(platform.getY());
    }

    private static void drawBricks() {
        for (Brick b : bricks) {
            BrickDifficulty difficulty = b.getDifficulty();
            drawBrick(b, difficulty, false);

        }
    }

    public static void drawBrick(Brick b, BrickDifficulty difficulty, boolean redraw) {

        String texture = null;
        switch (difficulty) {

            case GLASS:
                texture = "brick/glass.png";
                break;
            case WOOD:
                texture = "brick/wood.png";
                break;
            case ROCK:
                texture = "brick/rock.png";
                break;
            case METAL:
                texture = "brick/metal.png";
                break;
            case DIAMOND:
                texture = "brick/diamond.png";
               break;
        }
        if (redraw) {
            GameEntity brickEntity = bricksEntities.get(b);
            if (texture == null) {
                brickEntity.removeFromWorld();
                return;
            }
            brickEntity.setViewFromTexture(texture);

        } else if (texture != null) {
            brickEntity = Entities.builder()
                    .type(EntityType.BRICK)
                    .at(b.getX(), b.getY())
                    .viewFromTexture(texture)
                    .buildAndAttach();

            bricksEntities.put(b, brickEntity);
        }
    }

    public static void redrawBrick(int brickIndex) {
        if (brickIndex > -1) {
            Brick brick = bricks.get(brickIndex);
            BrickDifficulty difficulty = brick.getDifficulty();
            drawBrick(brick, difficulty, true);

        }
    }

    private static void drawBonus(Bonus bonus) {
        if (bonusesEntities.get(bonus) != null) {
            bonusesEntities.get(bonus).setY(bonus.getY());
        } else {
            switch (bonus.getBonusType()) {
                case ALL_DIAMOND:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("brick/diamond.png")
                            .buildAndAttach();
                    break;
                case ALL_GLASS:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("brick/glass.png")
                            .buildAndAttach();
                    break;
                case FASTER_BALL:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("bonus/fast_ball.png")
                            .buildAndAttach();

                    break;
                case SLOWER_BALL:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("bonus/slow_ball.png")
                            .buildAndAttach();
                    break;
                case LONGER_PLATFORM:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("bonus/longer_platform.png")
                            .buildAndAttach();
                    break;
                case SHORTER_PLATFORM:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("bonus/shorter_platform.png")
                            .buildAndAttach();
                    break;
                case EXTRA_LIFE:
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("bonus/heart.png")
                            .buildAndAttach();
                    break;
                case EXTRA_POINTS:
                    Text bonusText = new Text("+ 1000");
                    bonusText.setFill(Color.WHITE);
                    bonusEntity = Entities.builder()
                            .type(EntityType.BONUS)
                            .at(bonus.getX(), bonus.getY())
                            .viewFromTexture("bonus/extra_points.png")
                            .buildAndAttach();
                    break;
            }

            bonusesEntities.put(bonus, bonusEntity);
        }
    }

    public static void clearUsedBonus(Bonus bonus) {
        bonusesEntities.get(bonus).removeFromWorld();
        bonusesEntities.remove(bonus);
    }

    public static void redrawBonus(Bonus bonus) {
        drawBonus(bonus);
    }

    public static void printGameOver() {
        removeLabels();
        GameEntity gameOver = Entities.builder()
                .at(0, 0)
                .viewFromTexture("gameover.jpg")
                .with(new CollidableComponent(true))
                .buildAndAttach();

    }

    private static void removeLabels() {
        labelLevelText.setVisible(false);
        labelLivesText.setVisible(false);
        labelScoreText.setVisible(false);
        levelText.setVisible(false);
        livesText.setVisible(false);
        scoreText.setVisible(false);
    }

    private void drawBackground() {
        Texture texture = FXGL.getAssetLoader().loadTexture("background.jpg");
        texture.setFitHeight(Dimensions.HEIGHT);
        texture.setFitWidth(Dimensions.WIDTH);
        ScrollingBackgroundView bg = new ScrollingBackgroundView(texture, Orientation.HORIZONTAL);
        getGameScene().addGameView(bg);
    }

    public static void redrawOnStart() {
        redrawPlatform(true);
        redrawBall();
    }

    private void drawScore() {

        labelScoreText = new Text(10, 20, "Score:");
        labelScoreText.setFont(Font.font("Verdana", 20));
        labelScoreText.setFill(Color.WHITE);

        scoreProperty = new SimpleIntegerProperty(score);
        scoreText = new Text(75, 20, "0");
        scoreText.setFont(Font.font("Verdana", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.textProperty().bind(scoreProperty.asString());

        labelLivesText = new Text(815, 20, "Lives:");
        labelLivesText.setFont(Font.font("Verdana", 20));
        labelLivesText.setFill(Color.WHITE);

        livesProperty = new SimpleIntegerProperty(platform.getLives());
        livesText = new Text(880, 20, "3");
        livesText.setFont(Font.font("Verdana", 20));
        livesText.setFill(Color.WHITE);
        livesText.textProperty().bind(livesProperty.asString());

        labelLevelText = new Text(420, 20, "Level:");
        labelLevelText.setFont(Font.font("Verdana", 20));
        labelLevelText.setFill(Color.WHITE);

        levelProperty = new SimpleIntegerProperty(platform.getLives());
        levelText = new Text(485, 20, "1");
        levelText.setFont(Font.font("Verdana", 20));
        levelText.setFill(Color.WHITE);
        levelText.textProperty().bind(levelProperty.asString());

        getGameScene().addUINode(scoreText);
        getGameScene().addUINode(labelScoreText);
        getGameScene().addUINode(livesText);
        getGameScene().addUINode(labelLivesText);
        getGameScene().addUINode(levelText);
        getGameScene().addUINode(labelLevelText);
    }

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
}
