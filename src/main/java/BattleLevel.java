package main.java;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by connorusry on 8/30/15.
 */
public class BattleLevel extends Level{

    private VillainDodgeballer villainPlayer;
    private ImageView villainPlayerIV;
    private boolean ducking;
    private int enemyStartLives;
    private HBox enemyLivesHBox;


    @Override
    Scene init(int w, int h, int l) {
        setMyWidth(w);
        setMyHeight(h);
        setMyStartLives(l);
        setEnemyStartLives(3);
        setMyStartTime(120);
        setMyMoveSpeed(20);
        setMyTossSpeed(8);
        setEnemyBallList(new ArrayList<>());

        createHeroDodgeballer();
        createEnemyDodgeballer();
        setMyLivesHBox(getMyStartLives());
        setEnemyLivesHBox(getEnemyStartLives());

        //Create scene graph to organize scene
        setMyRoot(new Group());
        setMyScene(new Scene(getMyRoot(), getMyWidth(), getMyHeight(), Color.BLANCHEDALMOND));
        Line baseline = new Line(0, getMyHeight()*2/3, getMyWidth(), getMyHeight()*2/3);
        baseline.setStrokeWidth(8);
        Line midline = new Line(getMyWidth()*.55, getMyHeight()*2/3, getMyWidth()*.45, getMyHeight());
        midline.setStrokeWidth(8);
        Circle midCirc = new Circle(getMyWidth()/2, getMyHeight()*5/6, 15);
        midCirc.setStrokeWidth(6);

        baseline.setFill(Color.ROSYBROWN);

        getMyRoot().getChildren().addAll(baseline, midline, midCirc, getMyPlayerIV(), villainPlayerIV, getMyLivesHBox(), getEnemyLivesHBox());
        //respond to input
        getMyScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        getMyScene().setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return getMyScene();
    }

    @Override
    void step(double elapsedTime) {
        if (getEnemyBallList().size() < 3) {
            if (tossBall()) {
                Random rand = new Random();
                int highOrLow = rand.nextInt(3)*20;
                Dodgeball newBall = new Dodgeball(villainPlayerIV.getX(),
                        villainPlayerIV.getY(),
                        getMyPlayerIV().getX(),
                        getMyPlayerIV().getY() + highOrLow,
                        20,
                        Color.RED);

                getEnemyBallList().add(newBall);
                getMyRoot().getChildren().add(newBall);
            }
        }
        Iterator<Dodgeball> iter = getEnemyBallList().iterator();
        while (iter.hasNext()) {
            Dodgeball villainBall = iter.next();
            villainBall.setCenterX(villainBall.getCenterX() - villainPlayer.getMyTossSpeed());
            villainBall.setCenterY(villainBall.getCenterX() * villainBall.getTrajectorySlope() + villainBall.getTrajectoryYIntercept());

            // check for collisions
            if (getMyPlayerIV().getBoundsInParent().intersects(villainBall.getBoundsInParent())) {
                getMyRoot().getChildren().removeAll(villainBall, getMyLivesHBox());

                setMyLivesHBox(getMyLivesHBox().getChildren().size() - 1);
                getMyRoot().getChildren().add(getMyLivesHBox());
                if (getMyLivesHBox().getChildren().size() == 0) {
                    Platform.exit();
                }
            } else {
                if (villainBall.getCenterX() + villainBall.getBoundsInParent().getWidth() / 2 < 0) {
                    iter.remove();
                }
            }
        }
    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        double xLoc = getMyPlayerIV().getX();
        double yLoc = getMyPlayerIV().getY();
        double yLocBottom = yLoc + getMyPlayerIV().getBoundsInParent().getHeight()/2;
        double moveSpeed = getMyPlayer().getMyMoveSpeed();
        switch (code) {
            case RIGHT:
                //Check to see if crossed half court or ducking
                if(xLoc + getMyPlayerIV().getBoundsInLocal().getWidth() + moveSpeed < getMyWidth() / 2 && !isDucking()){
                    getMyPlayerIV().setX(xLoc + moveSpeed);
                }
                break;
            case LEFT:
                //Make sure not too far out of window or ducking
                if(xLoc - moveSpeed > 0 && !isDucking()){
                    getMyPlayerIV().setX(xLoc - moveSpeed);
                }
                break;
            case UP:
                //Joe Jumps, prevents jump if already in the middle of one
                if(getJumpTransition() == null || getJumpTransition().getStatus() == Animation.Status.STOPPED) {
                    setJumpTransition(new TranslateTransition(Duration.millis(300), getMyPlayerIV()));
                    getJumpTransition().interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                    getJumpTransition().setByY(-getMyPlayerIV().getY());
                    getJumpTransition().setAutoReverse(true);
                    getJumpTransition().setCycleCount(2);
                    getJumpTransition().play();
                }
                break;
            case DOWN:
                //Prevents "duck" if already ducking
                if(!isDucking()) {
                    getMyRoot().getChildren().removeAll(getMyPlayerIV());
                    Image duck = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/duck.png"));
                    setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(duck)));
                    setMyPlayerIV(getMyPlayer().getMyImageView());
                    getMyPlayer().getMyImageView().setX(xLoc);
                    getMyPlayer().getMyImageView().setY(yLocBottom);
                    getMyRoot().getChildren().add(getMyPlayerIV());
                    setDucking(true);
                }
                break;
            case A:
                if(getMyPlayer().isHoldingBall()){


                }

            default:
        }
    }

    @Override
    protected void handleKeyRelease(KeyCode code) {
        double xLoc = getMyPlayerIV().getX();
        switch (code) {
            case DOWN:
                getMyRoot().getChildren().removeAll(getMyPlayerIV());
                Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
                setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(stand)));
                getMyPlayer().getMyImageView().setX(xLoc);
                getMyPlayer().getMyImageView().setY((.6 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
                setMyPlayerIV(getMyPlayer().getMyImageView());
                getMyRoot().getChildren().add(getMyPlayerIV());
                setDucking(false);
                break;
            default:
        }
    }

    @Override
    protected void setMyLivesHBox(int l) {
        // TODO: Figure out why standard iter isn't working for circle
        setMyLivesHBox(new HBox());
        for(int i = 0; i < l; i++){
            Circle life = new Circle(20, Color.RED);
            getMyLivesHBox().getChildren().add(life);
        }

    }

    @Override
    protected void createHeroDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
        setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(stand)));
        getMyPlayer().getMyImageView().setX(0);
        getMyPlayer().getMyImageView().setY((.6 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
        setMyPlayerIV(getMyPlayer().getMyImageView());

    }

    @Override
    protected void createEnemyDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/villainStand.png"));
        villainPlayer = new VillainDodgeballer(0, getMyMoveSpeed(), getMyTossSpeed(), new ImageView(stand));
        villainPlayer.getMyImageView().setX(getMyWidth() - villainPlayer.getMyImageView().getBoundsInLocal().getWidth());
        villainPlayer.getMyImageView().setY((.6 * getMyHeight()) - villainPlayer.getMyImageView().getBoundsInLocal().getHeight() / 2);
        villainPlayerIV= villainPlayer.getMyImageView();
    }

    @Override
    protected VBox getTimerVbox() {
        return null;
    }

    public boolean isDucking() {
        return ducking;
    }

    public void setDucking(boolean ducking) {
        this.ducking = ducking;
    }

    public int getEnemyStartLives() {
        return enemyStartLives;
    }

    public void setEnemyStartLives(int enemyStartLives) {
        this.enemyStartLives = enemyStartLives;
    }

    public HBox getEnemyLivesHBox() {
        return enemyLivesHBox;
    }

    public void setEnemyLivesHBox(int l) {
        HBox enemyLives = new HBox();
        enemyLives.setAlignment(Pos.TOP_CENTER);
        System.out.println(enemyLives.alignmentProperty());
        for (int i = 0; i < l; i++) {
            Circle life = new Circle(20, Color.PURPLE);
            enemyLives.getChildren().add(life);
        }
        this.enemyLivesHBox = enemyLives;
        enemyLivesHBox.setLayoutX(getMyWidth() -getMyWidth()*.1);
    }
    //Prevents overlapping of dodgeball throws
    private boolean allBallsOverLine() {
        for (Dodgeball dodgeball : getEnemyBallList()) {
            if(dodgeball.getCenterX() > getMyWidth()/2){
                return false;
            }
        }
        return true;
    }
    //Chance that patches throws a ball
    public boolean tossBall(){
        Random rand = new Random();
        if (rand.nextInt(100) == 0) { //tosses a ball 1% of the steps
            return allBallsOverLine();
        }
        return false;
    }
}
