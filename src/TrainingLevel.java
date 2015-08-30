import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


/**
 * Created by connorusry on 8/29/15.
 */
class TrainingLevel {
    public Scene init(int width, int height) {
        Label trainingLevel = new Label("This is the training level");
        StackPane temp = new StackPane();
        temp.getChildren().add(trainingLevel);

        Scene trainingScene = new Scene(temp, width, height);
        return trainingScene;
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
}
