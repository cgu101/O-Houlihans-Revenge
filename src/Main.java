import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int SIZE = 500;
    private double MILLISECOND_DELAY;
    private double SECOND_DELAY;

    private int lives;
    private Stage primaryStage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("O'Houlihan's Revenge");
        primaryStage.setOnCloseRequest(e -> Platform.exit());

        makeMainMenu();
    }

    //shows to proper scene
    private void setScene(Scene currentScene) {
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    //sets scene to training level
    public void makeTrainingLevel() {
        TrainingLevel myTrainingLevel = new TrainingLevel();
        Scene trainingLevelScene = myTrainingLevel.init(SIZE, SIZE, lives);
        setScene(trainingLevelScene);
    }

    //sets scene to battle level
    public void makeBattleLevel() {
        BattleLevel myBattleLevel = new BattleLevel();
        Scene battleLevelScene = myBattleLevel.init(SIZE,SIZE);
        setScene(battleLevelScene);
    }

    //sets scene to main Menu
    public void makeMainMenu() {
        BorderPane mainMenuPane = new BorderPane();
        Label welcomeLbl = new Label("O'Houlihan's Revenge... Pick Your Challenge");

        //Select Your Training Level
        Button trainingButton = new Button("Begin your training!");
        trainingButton.setOnAction(e -> makeTrainingLevel());
        Button battleButton = new Button("Battle Goodman!");
        battleButton.setOnAction(e -> makeBattleLevel());

        // Quit the game
        Button quitButton = new Button("Quit-I hate fun");
        quitButton.setOnAction(e -> Platform.exit());

        //Set up the main menu for level selection
        VBox gameTypesVbox = new VBox(20);
        gameTypesVbox.setAlignment(Pos.CENTER);
        gameTypesVbox.getChildren().addAll(welcomeLbl, trainingButton, battleButton);
        lives = 3;

        mainMenuPane.setCenter(gameTypesVbox);
        mainMenuPane.setBottom(quitButton);

        mainMenuPane.setAlignment(quitButton, Pos.BOTTOM_RIGHT);
        mainMenuPane.setStyle("-fx-background-color: red;");
        Scene mainMenuScene = new Scene(mainMenuPane, SIZE, SIZE);
        setScene(mainMenuScene);
    }



}
