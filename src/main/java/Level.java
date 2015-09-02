package main.java;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by connorusry on 9/1/15.
 */
public abstract class Level {
    private Integer myStartTime;
    private Integer myMoveSpeed;
    private Integer myTossSpeed;

    private int myWidth;
    private int myHeight;
    private int myStartLives;
    private Group myRoot;
    private Scene myScene;
    private HBox myLivesHBox;
    private Dodgeballer myPlayer;
    private ImageView myPlayerIV;
    private Timeline myClockTimeline;
    private IntegerProperty myTimeSeconds;

    abstract Scene init(int w, int h, int l);

    abstract void step(double elapsedTime);

    abstract void handleKeyInput (KeyCode code);

    abstract void handleKeyRelease(KeyCode code);

    abstract void setMyLivesHBox(int l);

    abstract void createHeroDodgeballer();

    abstract void createEnemyDodgeballer();

    abstract VBox getTimerVbox();

    public Integer getMyMoveSpeed() {
        return myMoveSpeed;
    }

    public void setMyMoveSpeed(Integer myMoveSpeed) {
        this.myMoveSpeed = myMoveSpeed;
    }

    public Integer getMyTossSpeed() {
        return myTossSpeed;
    }

    public void setMyTossSpeed(Integer myTossSpeed) {
        this.myTossSpeed = myTossSpeed;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public void setMyWidth(int myWidth) {
        this.myWidth = myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public void setMyHeight(int myHeight) {
        this.myHeight = myHeight;
    }

    public int getMyStartLives() {
        return myStartLives;
    }

    public void setMyStartLives(int myStartLives) {
        this.myStartLives = myStartLives;
    }

    public Group getMyRoot() {
        return myRoot;
    }

    public void setMyRoot(Group myRoot) {
        this.myRoot = myRoot;
    }

    public Scene getMyScene() {
        return myScene;
    }

    public void setMyScene(Scene myScene) {
        this.myScene = myScene;
    }


    public HBox getMyLivesHBox() {
        return myLivesHBox;
    }

    public void setMyLivesHBox(HBox myLivesHBox){
        this.myLivesHBox = myLivesHBox;
    }

    public Dodgeballer getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Dodgeballer myPlayer) {
        this.myPlayer = myPlayer;
    }

    public ImageView getMyPlayerIV() {
        return myPlayerIV;
    }

    public void setMyPlayerIV(ImageView myPlayerIV) {
        this.myPlayerIV = myPlayerIV;
    }

    public Timeline getMyClockTimeline() {
        return myClockTimeline;
    }

    public void setMyClockTimeline(Timeline myClockTimeline) {
        this.myClockTimeline = myClockTimeline;
    }

    public Integer getMyStartTime() {
        return myStartTime;
    }

    public void setMyStartTime(Integer myStartTime) {
        this.myStartTime = myStartTime;
    }

    public void setMyTimeSeconds(IntegerProperty myTimeSeconds) {
        this.myTimeSeconds = myTimeSeconds;
    }


    public IntegerProperty getMyTimeSeconds() {
        return myTimeSeconds;
    }
}
