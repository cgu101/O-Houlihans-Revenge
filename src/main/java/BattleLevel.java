package main.java;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Created by connorusry on 8/30/15.
 */
public class BattleLevel extends Level{

    @Override
    Scene init(int w, int h, int l) {
        return null;
    }

    @Override
    void step(double elapsedTime) {

    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        double xLoc = myPlayerIV.getX();
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
                //Joe Jumps
                TranslateTransition translation = new TranslateTransition(Duration.millis(500), myPlayerIV);
                translation.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                translation.setByY(-50);
                translation.setAutoReverse(true);
                translation.setCycleCount(2);
                translation.play();
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

    }

    @Override
    protected void setMyLivesHBox(int l) {

    }

    @Override
    protected void createHeroDodgeballer() {

    }

    @Override
    protected void createPatchesDodgeballer() {

    }

    @Override
    protected VBox getTimerVbox() {
        return null;
    }
}
