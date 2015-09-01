package main.java;

import javafx.animation.*;
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

public class TrainingLevel {
    private int width;
    private int height;
    private Scene myScene;
    private ArrayList<Circle> myLives;
    private Dodgeballer myPlayer;
    private ImageView myPlayerIV;

    private Dodgeballer villainPlayer;
    private ImageView villainPlayerIV;

    private Timeline clockTimeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(START_TIME);

    private static final Integer START_TIME = 120;
    private static final Integer DEFAULT_SPEED = 15;

    public Scene init(int w, int h, int l){
        //initialize globals
        width = w;
        height = h;
        setMyLives(l);
        createHeroDodgeballer();
        createVillianDodgeballer();

        //Create scene graph to organize scene
        Group root = new Group();
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
        root.getChildren().addAll(baseline, midline, midCirc, timerVbox, myPlayerIV, villainPlayerIV, getLivesHbox());

        //respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return myScene;
    }

    private void createHeroDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
        myPlayer = new MyDodgeballer(myLives.size() ,DEFAULT_SPEED, new ImageView(stand));
        myPlayer.getImageView().setX((.2 * width)  -myPlayer.getImageView().getBoundsInLocal().getWidth() / 2);
        myPlayer.getImageView().setY((.8 * height) - myPlayer.getImageView().getBoundsInLocal().getHeight() / 2);
        myPlayerIV= myPlayer.getImageView();

    }

    private void createVillianDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/patchesStand.png"));
        villainPlayer = new VillainDodgeballer(myLives.size() ,DEFAULT_SPEED, new ImageView(stand));
        villainPlayer.getImageView().setX((.8 * width) - villainPlayer.getImageView().getBoundsInLocal().getWidth() / 2);
        villainPlayer.getImageView().setY((.8 * height) - villainPlayer.getImageView().getBoundsInLocal().getHeight() / 2);
        villainPlayerIV= villainPlayer.getImageView();
    }


    public void step (double elapsedTime) {
        // update attributes
        // ImageView myPlayerIV= myPlayer.getImageView();
        // myPlayerIV.setX(myPlayerIV.getX() + 1000);
        // myPlayer.setImageView(myPlayerIV);
        // check for collisions
        // with shapes, can check precisely
    }

    private void handleKeyInput (KeyCode code) {
        double xLoc = myPlayerIV.getX();
        double yLoc = myPlayerIV.getY();
        double speed = myPlayer.getSpeed();
        switch (code) {
            case RIGHT:
                if(xLoc + myPlayerIV.getBoundsInLocal().getWidth() < width / 2){
                    myPlayerIV.setX(xLoc + 10);
                }
                //Move Joe Right
                //Check to see if crossed half court
                break;
            case LEFT:
                myPlayerIV.setX(xLoc - 10);
                //Move Joe Left
                //Make sure not too far out of window
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
    private void handleKeyRelease(KeyCode code) {
//        double xLoc = myPlayerIV.getX();
//        double yLoc = myPlayerIV.getY();
//        myPlayerIV.setImage(stand);
//        myPlayerIV.setX(xLoc);
//        myPlayerIV.setY(yLoc);
    }

    private void setMyLives(int l) {
        // TODO: Figure out why standard iter isn't working for circle
        myLives = new ArrayList<>();
        for(int i = 0; i < l; i++){
            Circle life = new Circle(20, Color.RED);
            myLives.add(life);
        }
    }

    private HBox getLivesHbox(){
        HBox livesBox = new HBox();
        livesBox.getChildren().addAll(myLives);
        return livesBox;

    }
    //Add the countdown timer
    private VBox getTimerVbox() {
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
