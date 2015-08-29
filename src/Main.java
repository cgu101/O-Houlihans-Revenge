import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.management.PlatformLoggingMXBean;

public class Main extends Application {

    public static final int SIZE = 500;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //initializes a start menu
        Scene mainMenuScene = startMenu(primaryStage, SIZE, SIZE);
        primaryStage.setTitle("O'Houlihan's Revenge");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public Scene startMenu(Stage primaryStage, int width, int height) {
        BorderPane mainMenuPane = new BorderPane();
        Button trainingButton = new Button("Begin your training!");
        Button battleButton = new Button("Battle Goodman!");
        Button quitButton = new Button("Quit-I hate fun");
        quitButton.setOnAction(e -> Platform.exit());


        VBox gameTypesVbox = new VBox();
        gameTypesVbox.setAlignment(Pos.CENTER);
        gameTypesVbox.getChildren().addAll(trainingButton, battleButton);

        mainMenuPane.autosize();
        mainMenuPane.setCenter(gameTypesVbox);
        mainMenuPane.setBottom(quitButton);
        mainMenuPane.setStyle("-fx-background-color: red;");
        Scene myMenuScene = new Scene(mainMenuPane, width, height);
        return myMenuScene;
    }
}
