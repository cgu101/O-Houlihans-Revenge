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

    private static final int DEFAULT_SPEED = 40;

    public Scene init(int w, int h, int l) {
        width = w;
        height = h;
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/throw1.png"));
        myPlayer = new Dodgeballer(l,DEFAULT_SPEED, image);
        myPlayerIV= myPlayer.getImageView();

        myPlayerIV.setX(width / 2 - myPlayerIV.getBoundsInLocal().getWidth() / 2);
        myPlayerIV.setY(height / 2 - myPlayerIV.getBoundsInLocal().getHeight() / 2);
        myPlayer.setImageView(myPlayerIV);
        setLives(l);
        createBackground();
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }


    private void createBackground() {
        //Create scene graph to organize scene
        Group root = new Group();
        //Create a scene to display shapes
        myScene = new Scene(root, width, height, Color.BISQUE);
        //Make some shapes and set properties

        Label trainingLevel = new Label("This is the training level");
        StackPane courtPane = new StackPane();
        courtPane.getChildren().addAll(getLivesHbox(),trainingLevel, myPlayerIV);
        myScene = new Scene(courtPane, width, height);
    }

    public void step (double elapsedTime) {
        // update attributes
        ImageView myPlayerIV= myPlayer.getImageView();
        myPlayerIV.setX(myPlayerIV.getX() + 1000);
        myPlayer.setImageView(myPlayerIV);

        // check for collisions
        // with shapes, can check precisely
    }

    private void handleKeyInput (KeyCode code) {
        double xLoc = myPlayerIV.getX();
        switch (code) {
            case RIGHT:
                myPlayerIV.setX(xLoc + 5);
                //Move Joe Right
                //Check to see if crossed half court
                break;
            case LEFT:
                myPlayerIV.setX(xLoc - 5);
                //Move Joe Left
                //Make sure not too far out of window
                break;
            case UP:
                TranslateTransition translation = new TranslateTransition(Duration.millis(500), myPlayer.getImageView());
                translation.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                translation.setByY(-50);
                translation.setAutoReverse(true);
                translation.setCycleCount(2);
                translation.play();
                //Joe Jumps
                break;
            case DOWN:
                //Joe Ducks
                break;
            default:
                // do nothing
        }
    }

    private void setLives(int l) {
        // TODO: Figure out why standard iter isn't working for circle
        lives = new ArrayList<>();
        for(int i = 0; i < l; i++){
            Circle life = new Circle(10, Color.PINK);
            lives.add(life);
        }
    }

    private HBox getLivesHbox(){
        HBox livesBox = new HBox();
        livesBox.getChildren().addAll(lives);
        return livesBox;

    }
}
