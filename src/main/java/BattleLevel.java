package main.java;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
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

/**
 * Created by connorusry on 8/30/15.
 */
public class BattleLevel extends Level{

    private boolean ducking;

    @Override
    Scene init(int w, int h, int l) {
        setMyWidth(w);
        setMyHeight(h);
        setMyStartLives(l);
        setMyStartTime(120);
        setMyMoveSpeed(20);
        setMyTossSpeed(23);

        createHeroDodgeballer();
        createEnemyDodgeballer();
        setMyLivesHBox(getMyStartLives());

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

        getMyRoot().getChildren().addAll(baseline, midline, midCirc, getMyPlayerIV(), getMyLivesHBox());

        //respond to input
        getMyScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        getMyScene().setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return getMyScene();
    }

    @Override
    void step(double elapsedTime) {

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
                    getJumpTransition().setByY(-getMyPlayerIV().getY()/2);
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
            default:
        }
    }

    @Override
    protected void handleKeyRelease(KeyCode code) {
        double xLoc = getMyPlayerIV().getX();
        double yLoc = getMyPlayerIV().getY();
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
}
