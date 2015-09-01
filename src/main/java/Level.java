package main.java;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    static final Integer START_TIME = 120;
    static final Integer DEFAULT_MOVE_SPEED = 15;
    static final Integer DEFAULT_TOSS_SPEED = 17;
    int width;
    int height;
    int startLives;
    Group root;
    Scene myScene;
    HBox myLivesHBox;
    Dodgeballer myPlayer;
    ImageView myPlayerIV;
    Timeline clockTimeline;
    IntegerProperty timeSeconds = new SimpleIntegerProperty(START_TIME);

    abstract Scene init(int w, int h, int l);

    abstract void step(double elapsedTime);

    protected abstract void handleKeyInput (KeyCode code);

    protected abstract void handleKeyRelease(KeyCode code);

    protected abstract void setMyLivesHBox(int l);

    protected abstract void createHeroDodgeballer();

    protected abstract void createPatchesDodgeballer();

    //Add the countdown timer
    protected abstract VBox getTimerVbox();
}
