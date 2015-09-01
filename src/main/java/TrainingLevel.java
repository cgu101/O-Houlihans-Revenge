package main.java;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
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

import java.util.ArrayList;


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
        width = w;
        height = h;
        startLives = l;
        createHeroDodgeballer();
        createPatchesDodgeballer();
        setMyLivesHBox(startLives);

        //Create scene graph to organize scene
        root = new Group();
        //Create a scene to display shapes
        myScene = new Scene(root, width, height, Color.BISQUE);

        //Make some shapes and set properties
        Line baseline = new Line(0, height*1/3,width,height*1/3);
        baseline.setStrokeWidth(8);
        Line midline = new Line(width/2, height*1/3, width/2, height);
        midline.setStrokeWidth(8);
        Circle midCirc = new Circle(width/2, height*2/3, 30);
        midCirc.setStrokeWidth(6);

        baseline.setFill(Color.ROSYBROWN);

        //Order added to group
        VBox timerVbox = getTimerVbox();
        root.getChildren().addAll(baseline, midline, midCirc, timerVbox, myPlayerIV, patchesPlayerIV, myLivesHBox);

        //respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return myScene;
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
                    root.getChildren().add(patchesBall);
                }
        } else {
            patchesBall.setCenterX(patchesBall.getCenterX() - patchesPlayer.getTossSpeed());
            // check for collisions
            if(myPlayerIV.getBoundsInParent().intersects(patchesBall.getBoundsInParent())){
                root.getChildren().removeAll(patchesBall, myLivesHBox);
                patchesBall = null;
                setMyLivesHBox(myLivesHBox.getChildren().size() - 1);
                root.getChildren().add(myLivesHBox);
                if(myLivesHBox.getChildren().size() == 0){
                    Platform.exit();
                }
            } else {
                if (patchesBall.getCenterX() + patchesBall.getBoundsInParent().getWidth() / 2 < 0) {
                    patchesBall = null;
                }
            }
        }


        //If no lives kill program

        // with shapes, can check precisely
    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        double xLoc = myPlayerIV.getX();
        double yLoc = myPlayerIV.getY();
        double moveSpeed = myPlayer.getMoveSpeed();
        switch (code) {
            case RIGHT:
                //Move Joe Right
                //Check to see if crossed half court
                if(xLoc + myPlayerIV.getBoundsInLocal().getWidth() + moveSpeed < width / 2){
                    myPlayerIV.setX(xLoc + moveSpeed);
                }
                break;
            case LEFT:
                //Move Joe Left
                //Make sure not too far out of window
                if(xLoc - moveSpeed > 0){
                    myPlayerIV.setX(xLoc - moveSpeed);
                }
                break;
            case UP:
                TranslateTransition translation = new TranslateTransition(Duration.millis(500), myPlayerIV);
                translation.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                translation.setByY(-50);
                translation.setAutoReverse(true);
                translation.setCycleCount(2);
                translation.play();
                //Joe Jumps
                break;
            case DOWN:
                //Joe Ducks
//                myPlayerIV.setImage(duck);
//                myPlayerIV.setX(xLoc);
//                myPlayerIV.setY(yLoc);

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
        myLivesHBox = new HBox();
        for(int i = 0; i < l; i++){
            Circle life = new Circle(20, Color.RED);
            myLivesHBox.getChildren().add(life);
        }

    }

    @Override
    protected void createHeroDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
        myPlayer = new MyDodgeballer(startLives , DEFAULT_MOVE_SPEED, new ImageView(stand));
        myPlayer.getImageView().setX((.2 * width)  -myPlayer.getImageView().getBoundsInLocal().getWidth() / 2);
        myPlayer.getImageView().setY((.8 * height) - myPlayer.getImageView().getBoundsInLocal().getHeight() / 2);
        myPlayerIV= myPlayer.getImageView();

    }

    @Override
    protected void createPatchesDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/patchesStand.png"));
        patchesPlayer = new PatchesDodgeballer(0, DEFAULT_MOVE_SPEED, DEFAULT_TOSS_SPEED, new ImageView(stand));
        patchesPlayer.getImageView().setX((.8 * width) - patchesPlayer.getImageView().getBoundsInLocal().getWidth() / 2);
        patchesPlayer.getImageView().setY((.8 * height) - patchesPlayer.getImageView().getBoundsInLocal().getHeight() / 2);
        patchesPlayerIV= patchesPlayer.getImageView();
    }

    //Add the countdown timer
    @Override
    protected VBox getTimerVbox() {
        Label timerLabel = new Label();
        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setStyle("-fx-font-size: 4em;");

        Button button = new Button();
        button.setText("Start Game!");
        button.setOnAction(e -> {
                    button.visibleProperty().set(false);
                    timeSeconds.set(START_TIME);
                    clockTimeline = new Timeline();
                    clockTimeline.getKeyFrames().add(
                            new KeyFrame(Duration.seconds(START_TIME + 1),
                                    new KeyValue(timeSeconds, 0)));
                    clockTimeline.playFromStart();
                }
        );

        //Puts timer in Vbox
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPrefWidth(myScene.getWidth());
        vb.getChildren().addAll(button, timerLabel);
        vb.setLayoutY(30);

        return vb;
    }
}
