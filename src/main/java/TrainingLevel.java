package main.java;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    private ArrayList<Circle> lives;
    private Dodgeballer myPlayer;
    private ImageView myPlayerIV;
    private Image stand;
    private Image duck;

    private static final int DEFAULT_SPEED = 40;

    public Scene init(int w, int h, int l){
        //initialize globals
        width = w;
        height = h;
        setLives(l);
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
        Image duck = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/duck.png"));

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

        myPlayerIV = new ImageView(stand);
        myPlayerIV.setX((.2 * width)/ - myPlayerIV.getBoundsInLocal().getWidth() / 2);
        myPlayerIV.setY((.8 * height) - myPlayerIV.getBoundsInLocal().getHeight() / 2);

        //Order added to group
        root.getChildren().addAll(baseline, midline, midCirc, myPlayerIV, getLivesHbox());

        //respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return myScene;
    }



    public void step (double elapsedTime) {
        // update attributes
//        ImageView myPlayerIV= myPlayer.getImageView();
//        myPlayerIV.setX(myPlayerIV.getX() + 1000);
//        myPlayer.setImageView(myPlayerIV);

        // check for collisions
        // with shapes, can check precisely
    }

    private void handleKeyInput (KeyCode code) {
        double xLoc = myPlayerIV.getX();
        double yLoc = myPlayerIV.getY();
        switch (code) {
            case RIGHT:
                myPlayerIV.setX(xLoc + 10);
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
                myPlayerIV.setImage(duck);
                myPlayerIV.setX(xLoc);
                myPlayerIV.setY(yLoc);

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

    private void setLives(int l) {
        // TODO: Figure out why standard iter isn't working for circle
        lives = new ArrayList<>();
        for(int i = 0; i < l; i++){
            Circle life = new Circle(20, Color.RED);
            lives.add(life);
        }
    }

    private HBox getLivesHbox(){
        HBox livesBox = new HBox();
        livesBox.getChildren().addAll(lives);
        return livesBox;

    }
}
