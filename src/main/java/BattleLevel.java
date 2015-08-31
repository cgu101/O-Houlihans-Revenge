package main.java;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Created by connorusry on 8/30/15.
 */
public class BattleLevel {

    public Scene init(int width, int height){
        Label battleLevel = new Label("This is the battle level");
        StackPane temp = new StackPane();
        temp.getChildren().add(battleLevel);

        Scene battleScene = new Scene(temp, width, height);
        return battleScene;
    }
}
