package main.java;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * Created by connorusry on 8/29/15.
 */

public class TrainingLevel extends Level {

    private PatchesDodgeballer patchesPlayer;
    private ImageView patchesPlayerIV;
    private Circle patchesBall;

    @Override
    public Scene init(int w, int h, int l){
        //initialize globals
        setMyWidth(w);
        setMyHeight(h);
        setMyStartLives(l);
        setMyStartTime(150);
        setMyMoveSpeed(15);
        setMyTossSpeed(17);

        createHeroDodgeballer();
        createEnemyDodgeballer();
        setMyLivesHBox(getMyStartLives());

        //Create scene
        setMyRoot(new Group());
        setMyScene(new Scene(getMyRoot(), getMyWidth(), getMyHeight(), Color.BISQUE));

        //Make some shapes and set properties
        Line baseline = new Line(0, getMyHeight()*1/3, getMyWidth(), getMyHeight()*1/3);
        baseline.setStrokeWidth(8);
        Line midline = new Line(getMyWidth()/2, getMyHeight()*1/3, getMyWidth()/2, getMyHeight());
        midline.setStrokeWidth(8);
        Circle midCirc = new Circle(getMyWidth()/2, getMyHeight()*2/3, 30);
        midCirc.setStrokeWidth(6);

        baseline.setFill(Color.ROSYBROWN);

        //Order added to group
        getMyRoot().getChildren().addAll(baseline, midline, midCirc, getTimerVbox(), getMyPlayerIV(), patchesPlayerIV, getMyLivesHBox());

        //respond to input
        getMyScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        getMyScene().setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return getMyScene();
    }

    @Override
    public void step (double elapsedTime) {
        // update attributes
        //Chance that patches throws a ball if no ball is in play
        if(patchesBall == null){
                if(patchesPlayer.tossBall()){
                    patchesBall = new Circle(patchesPlayerIV.getX(),
                                                patchesPlayerIV.getY(),
                                                30,
                                                Color.RED);
                    getMyRoot().getChildren().add(patchesBall);
                }
        } else {
            patchesBall.setCenterX(patchesBall.getCenterX() - patchesPlayer.getMyTossSpeed());
            // check for collisions
            if(getMyPlayerIV().getBoundsInParent().intersects(patchesBall.getBoundsInParent())){
                getMyRoot().getChildren().removeAll(patchesBall, getMyLivesHBox());
                patchesBall = null;
                setMyLivesHBox(getMyLivesHBox().getChildren().size() - 1);
                getMyRoot().getChildren().add(getMyLivesHBox());
                if(getMyLivesHBox().getChildren().size() == 0){
                    Platform.exit();
                }
            } else {
                if (patchesBall.getCenterX() + patchesBall.getBoundsInParent().getWidth() / 2 < 0) {
                    patchesBall = null;
                }
            }
        }
    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        double xLoc = getMyPlayerIV().getX();
        double yLoc = getMyPlayerIV().getY();
        double moveSpeed = getMyPlayer().getMyMoveSpeed();
        switch (code) {
            case RIGHT:
                //Move Joe Right
                //Check to see if crossed half court
                if(xLoc + getMyPlayerIV().getBoundsInLocal().getWidth() + moveSpeed < getMyWidth() / 2){
                    getMyPlayerIV().setX(xLoc + moveSpeed);
                }
                break;
            case LEFT:
                //Move Joe Left
                //Make sure not too far out of window
                if(xLoc - moveSpeed > 0){
                    getMyPlayerIV().setX(xLoc - moveSpeed);
                }
                break;
            case UP:
                if(yLoc - moveSpeed > getMyHeight() *1/3){
                    getMyPlayerIV().setY(yLoc - moveSpeed);
                }
                break;
            case DOWN:
                if(yLoc + moveSpeed + getMyPlayerIV().getBoundsInParent().getHeight()< getMyHeight()){
                    getMyPlayerIV().setY(yLoc + moveSpeed);
                }
                break;
            default:
                // do nothing
        }
    }
    @Override
    protected void handleKeyRelease(KeyCode code) {
//        double xLoc = myPlayerIV.getX();
//        double yLoc = myPlayerIV.getY();
//        myPlayerIV.setImage(stand);
//        myPlayerIV.setX(xLoc);
//        myPlayerIV.setY(yLoc);
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
        getMyPlayer().getMyImageView().setX((.2 * getMyWidth())  - getMyPlayer().getMyImageView().getBoundsInLocal().getWidth() / 2);
        getMyPlayer().getMyImageView().setY((.8 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
        setMyPlayerIV(getMyPlayer().getMyImageView());

    }

    @Override
    protected void createEnemyDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/patchesStand.png"));
        patchesPlayer = new PatchesDodgeballer(0, getMyMoveSpeed(), getMyTossSpeed(), new ImageView(stand));
        patchesPlayer.getMyImageView().setX((.8 * getMyWidth()) - patchesPlayer.getMyImageView().getBoundsInLocal().getWidth() / 2);
        patchesPlayer.getMyImageView().setY((.8 * getMyHeight()) - patchesPlayer.getMyImageView().getBoundsInLocal().getHeight() / 2);
        patchesPlayerIV= patchesPlayer.getMyImageView();
    }

    //Add the countdown timer
    @Override
    protected VBox getTimerVbox() {
        Label timerLabel = new Label();
        timerLabel.textProperty().bind(new SimpleIntegerProperty(getMyStartTime()).asString());
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setStyle("-fx-font-size: 4em;");

        Button button = new Button();
        button.setText("Start Game!");
        button.setOnAction(e -> {
                    button.visibleProperty().set(false);
                    setMyTimeSeconds(new SimpleIntegerProperty(getMyStartTime()));
                    setMyClockTimeline(new Timeline());
                    getMyClockTimeline().getKeyFrames().add(
                            new KeyFrame(Duration.seconds(getMyStartTime() + 1),
                                    new KeyValue(new SimpleIntegerProperty(getMyStartTime()), 0)));
                    getMyClockTimeline().playFromStart();
                }
        );

        //Puts timer in Vbox
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPrefWidth(getMyScene().getWidth());
        vb.getChildren().addAll(button, timerLabel);
        vb.setLayoutY(30);

        return vb;
    }
}


//timeseconds =  SimpleIntegerProperty (START_TIME)
//start time = Integer