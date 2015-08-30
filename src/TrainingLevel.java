import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


/**
 * Created by connorusry on 8/29/15.
 */

class TrainingLevel {
    private int width;
    private int height;
    private Scene myScene;
    private ArrayList<Circle> lives;

    public Scene init(int w, int h, int l) {
        width = w;
        height = h;

        setLives(l);
        createBackground();
        return myScene;
    }


    private void createBackground() {
        Label trainingLevel = new Label("This is the training level");
        StackPane courtPane = new StackPane();
        courtPane.getChildren().addAll(getLivesHbox(),trainingLevel);
        myScene = new Scene(courtPane, width, height);
    }

    public void step (double elapsedTime) {

    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                //Move Joe Right
                break;
            case LEFT:
                //Move Joe Left
                break;
            case UP:
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
