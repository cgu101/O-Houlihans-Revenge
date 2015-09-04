package main.java;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static final int WIDTH_SIZE = 1200;
    public static final int HEIGHT_SIZE = 600;

    private static final int FRAMES_PER_SECOND = 60;
    private double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

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
    private void setScene(Scene currentScene, Level level) {
        primaryStage.setScene(currentScene);
        primaryStage.show();

        // sets the game's loop
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> level.step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    //sets scene to training level
    public void makeTrainingLevel() {
        TrainingLevel myTrainingLevel = new TrainingLevel();
        Scene trainingLevelScene = myTrainingLevel.init(primaryStage, WIDTH_SIZE, HEIGHT_SIZE, lives);
        setScene(trainingLevelScene, myTrainingLevel);
    }

    //sets scene to battle level
    public void makeBattleLevel() {
        BattleLevel myBattleLevel = new BattleLevel();
        Scene battleLevelScene = myBattleLevel.init(primaryStage, WIDTH_SIZE,HEIGHT_SIZE, 1);
        setScene(battleLevelScene, myBattleLevel);
    }

    //sets scene to main Menu
    public void makeMainMenu() {
        //Menu Image
        Image menuImage = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/patchesMenu.png"));
        ImageView menuIV = new ImageView(menuImage);

        //Menu Options
        BorderPane mainMenuPane = new BorderPane();
        Label welcomeLbl = new Label("O'Houlihan's Revenge... Pick Your Challenge");
        welcomeLbl.setTextFill(Color.ANTIQUEWHITE);

        Button trainingButton = new Button("Begin your training!");
        trainingButton.setOnAction(e -> makeTrainingLevel());
        Button battleButton = new Button("Battle Goodman!");
         battleButton.setOnAction(e -> makeBattleLevel());

        Button quitButton = new Button("Quit-I hate fun");
        quitButton.setOnAction(e -> Platform.exit());
        quitButton.setLayoutX(menuIV.getBoundsInParent().getWidth() - 120);
        quitButton.setLayoutY(menuIV.getBoundsInParent().getHeight() - 50);

        //Set up the main menu for level selection
        Group root = new Group();
        VBox gameTypesVbox = new VBox(20);
        gameTypesVbox.setAlignment(Pos.CENTER);
        gameTypesVbox.getChildren().addAll(welcomeLbl, trainingButton, battleButton);
        lives = 3;

        mainMenuPane.setCenter(gameTypesVbox);
        mainMenuPane.setAlignment(quitButton, Pos.BOTTOM_RIGHT);

        root.getChildren().addAll(menuIV, mainMenuPane, quitButton);

        Scene mainMenuScene = new Scene(root, menuIV.getBoundsInParent().getWidth(), menuIV.getBoundsInParent().getHeight());
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }



}
