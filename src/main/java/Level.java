package main.java;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by connorusry on 9/1/15.
 */
public abstract class Level {
    private Stage currentPrimaryStage;
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
    private TranslateTransition jumpTransition;
    private Boolean gameStarted;
    private double timeRemaining;
    private Label timerLabel;
    private ArrayList<Dodgeball> ballsInFlightList;

    abstract Scene init(Stage ps, int w, int h, int l);

    abstract void step(double elapsedTime);

    abstract void handleKeyInput (KeyCode code);

    abstract void handleKeyRelease(KeyCode code);

    abstract void setMyLivesHBox(int l);

    abstract void createHeroDodgeballer();

    abstract void createEnemyDodgeballer();

    abstract VBox getTimerButtonVBox();

    public void exitLevel(String s) {
        setGameStarted(false);
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        Button continueButton = new Button("Continue");
        dialogVbox.getChildren().addAll(new Text(s), continueButton);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);

        Main newMain = new Main();
        continueButton.setOnAction(e->{
            try {
                newMain.init();
                newMain.start(getCurrentPrimaryStage());
                dialog.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(e ->{
            try {
                newMain.init();
                newMain.start(getCurrentPrimaryStage());
                dialog.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
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

    public TranslateTransition getJumpTransition() {
        return jumpTransition;
    }

    public void setJumpTransition(TranslateTransition jumpTransition) {
        this.jumpTransition = jumpTransition;
    }

    public Boolean getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(Boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public ArrayList<Dodgeball> getBallsInFlightList() {
        return ballsInFlightList;
    }

    public void setBallsInFlightList(ArrayList<Dodgeball> ballsInFlightList) {
        this.ballsInFlightList = ballsInFlightList;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public Stage getCurrentPrimaryStage() {
        return currentPrimaryStage;
    }

    public void setCurrentPrimaryStage(Stage currentPrimaryStage) {
        this.currentPrimaryStage = currentPrimaryStage;
    }
}
