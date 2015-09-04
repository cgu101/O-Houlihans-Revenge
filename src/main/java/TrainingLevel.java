package main.java;

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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by connorusry on 8/29/15.
 */

public class TrainingLevel extends Level {

    private PatchesDodgeballer patchesPlayer;
    private ImageView patchesPlayerIV;

    @Override
    public Scene init(Stage ps, int w, int h, int l){
        //initialize globals
        setCurrentPrimaryStage(ps);
        setMyWidth(w);
        setMyHeight(h);
        setMyStartLives(l);
        setMyStartTime(120);
        setTimeRemaining(getMyStartTime());
        setMyMoveSpeed(30);
        setMyTossSpeed(5);
        setGameStarted(false);
        setBallsInFlightList(new ArrayList<>());

        createHeroDodgeballer();
        createEnemyDodgeballer();
        setMyLivesHBox(getMyStartLives());

        //Create scene
        setMyRoot(new Group());
        setMyScene(new Scene(getMyRoot(), getMyWidth(), getMyHeight(), Color.BISQUE));

        //Make some shapes and set properties
        Line baseline = new Line(0, getMyHeight()*1/3, getMyWidth(), getMyHeight()*1/3);
        baseline.setStrokeWidth(8);
        Line midline = new Line(getMyWidth()/2, getMyHeight()*1/3, getMyWidth()/2, getMyHeight());
        midline.setStrokeWidth(8);
        Circle midCirc = new Circle(getMyWidth()/2, getMyHeight()*2/3, 30);
        midCirc.setStrokeWidth(6);
        Label timerLabel = new Label(Double.toString(getTimeRemaining()));
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.setLayoutX(getMyWidth() / 2 - getMyWidth() * .05);
        timerLabel.setLayoutY(getMyHeight() * .2);
        setTimerLabel(timerLabel);


        baseline.setFill(Color.ROSYBROWN);

        //Order added to group
        getMyRoot().getChildren().addAll(getTimerLabel(), baseline, midline, midCirc, getTimerButtonVBox(), getMyPlayerIV(), patchesPlayerIV, getMyLivesHBox());

        //respond to input
        getMyScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return getMyScene();
    }

    @Override
    public void step (double elapsedTime) {
        // update attributes
        if(getTimeRemaining() < 0 && getGameStarted()){
            exitLevel("You beat training!!");
        }
        if(getGameStarted()) {
            getMyRoot().getChildren().remove(getTimerLabel());
            setTimeRemaining(getTimeRemaining() - elapsedTime);
            Label timerLabel = new Label(Long.toString(Math.round(getTimeRemaining() - elapsedTime)));
            timerLabel.setTextFill(Color.BLACK);
            timerLabel.setStyle("-fx-font-size: 4em;");
            timerLabel.setLayoutX(getMyWidth() / 2 - getMyWidth() * .04);
            timerLabel.setLayoutY(getMyHeight() * .15);
            setTimerLabel(timerLabel);
            getMyRoot().getChildren().add(getTimerLabel());
            if (getBallsInFlightList().size() < 3) {
                if (tossBall()) {
                    Random rand = new Random();
                    int variation = rand.nextInt(3)*20;
                    Dodgeball newBall = new Dodgeball(patchesPlayerIV.getX(),
                            patchesPlayerIV.getY(),
                            getMyPlayerIV().getX() + variation,
                            getMyPlayerIV().getY() + variation,
                            20,
                            Color.RED);
                    getBallsInFlightList().add(newBall);
                    getMyRoot().getChildren().add(newBall);
                }
            }
            Iterator<Dodgeball> iter = getBallsInFlightList().iterator();
            while (iter.hasNext()) {
                Dodgeball patchesBall = iter.next();
                patchesBall.setCenterX(patchesBall.getCenterX() - patchesPlayer.getMyTossSpeed());
                patchesBall.setCenterY(patchesBall.getCenterX() * patchesBall.getTrajectorySlope() + patchesBall.getTrajectoryYIntercept());

                // check for collisions
                if (getMyPlayerIV().getBoundsInParent().intersects(patchesBall.getBoundsInParent())) {
                    iter.remove();
                    getMyRoot().getChildren().removeAll(patchesBall, getMyLivesHBox());

                    setMyLivesHBox(getMyLivesHBox().getChildren().size() - 1);
                    getMyRoot().getChildren().add(getMyLivesHBox());
                    if (getMyLivesHBox().getChildren().size() == 0) {
                        exitLevel("Patches defeated you. Train Harder!!");
                    }
                }
                else {
                    if (patchesBall.getCenterX() + patchesBall.getBoundsInParent().getWidth() / 2 < 0) {
                        iter.remove();
                    }
                }
            }
        }
    }



    @Override
    protected void handleKeyInput(KeyCode code) {
        double xLoc = getMyPlayerIV().getX();
        double yLoc = getMyPlayerIV().getY();
        double moveSpeed = getMyPlayer().getMyMoveSpeed();
        switch (code) {
            case RIGHT:
                //Move Joe Right
                //Check to see if crossed half court
                if (xLoc + getMyPlayerIV().getBoundsInLocal().getWidth() + moveSpeed < getMyWidth() / 2) {
                    getMyPlayerIV().setX(xLoc + moveSpeed);
                }
                break;
            case LEFT:
                //Move Joe Left
                //Make sure not too far out of window
                if (xLoc - moveSpeed > 0) {
                    getMyPlayerIV().setX(xLoc - moveSpeed);
                }
                break;
            case UP:
                if (yLoc - moveSpeed > getMyHeight() * 1 / 3) {
                    getMyPlayerIV().setY(yLoc - moveSpeed);
                }
                break;
            case DOWN:
                if (yLoc + moveSpeed + getMyPlayerIV().getBoundsInParent().getHeight() < getMyHeight()) {
                    getMyPlayerIV().setY(yLoc + moveSpeed);
                }
                break;
            case L:
                if(getGameStarted() == false){
                    setMyStartLives(getMyStartLives() + 1);
                    getMyRoot().getChildren().remove(getMyLivesHBox());
                    setMyLivesHBox(getMyStartLives());
                    getMyRoot().getChildren().add(getMyLivesHBox());
                }
                break;
            case D:
                if(getGameStarted() == false && getMyStartLives() > 0){
                    setMyStartLives(getMyStartLives() -1);
                    getMyRoot().getChildren().remove(getMyLivesHBox());
                    setMyLivesHBox(getMyStartLives());
                    getMyRoot().getChildren().add(getMyLivesHBox());
                }
                break;
            case T:
                if(getGameStarted() == false){
                    setMyStartTime(getMyStartTime() + 5);
                    setTimeRemaining(getMyStartTime());
                    getTimerButtonVBox();
                    getMyRoot().getChildren().remove(getTimerLabel());


                    Label timerLabel = new Label(Double.toString(getTimeRemaining()));
                    timerLabel.setTextFill(Color.BLACK);
                    timerLabel.setStyle("-fx-font-size: 4em;");
                    timerLabel.setLayoutX(getMyWidth() / 2 - getMyWidth() * .05);
                    timerLabel.setLayoutY(getMyHeight() * .2);
                    setTimerLabel(timerLabel);
                    getMyRoot().getChildren().add(getTimerLabel());
                }
                break;
            case Y:
                if(getGameStarted() == false && getMyStartTime() > 5){
                    setMyStartTime(getMyStartTime() - 5);
                    setTimeRemaining(getMyStartTime());
                    getTimerButtonVBox();
                    getMyRoot().getChildren().remove(getTimerLabel());


                    Label timerLabel = new Label(Double.toString(getTimeRemaining()));
                    timerLabel.setTextFill(Color.BLACK);
                    timerLabel.setStyle("-fx-font-size: 4em;");
                    timerLabel.setLayoutX(getMyWidth() / 2 - getMyWidth() * .05);
                    timerLabel.setLayoutY(getMyHeight() * .2);
                    setTimerLabel(timerLabel);
                    getMyRoot().getChildren().add(getTimerLabel());
                }
                break;
            default:
                // do nothing
        }
    }

    @Override
    void handleKeyRelease(KeyCode code) {}

    @Override
    protected void setMyLivesHBox(int l) {
        // TODO: Figure out why standard iter isn't working for circle
        setMyLivesHBox(new HBox());
        for(int i = 0; i < l; i++){
            Circle life = new Circle(20, Color.RED);
            getMyLivesHBox().getChildren().add(life);
        }

    }
    @Override
    protected void createHeroDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
        setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(stand)));
        getMyPlayer().getMyImageView().setX((.2 * getMyWidth())  - getMyPlayer().getMyImageView().getBoundsInLocal().getWidth() / 2);
        getMyPlayer().getMyImageView().setY((.8 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
        setMyPlayerIV(getMyPlayer().getMyImageView());

    }


    @Override
    protected void createEnemyDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/patchesStand.png"));
        patchesPlayer = new PatchesDodgeballer(0, getMyMoveSpeed(), getMyTossSpeed(), new ImageView(stand));
        patchesPlayer.getMyImageView().setX((.8 * getMyWidth()) - patchesPlayer.getMyImageView().getBoundsInLocal().getWidth() / 2);
        patchesPlayer.getMyImageView().setY((.8 * getMyHeight()) - patchesPlayer.getMyImageView().getBoundsInLocal().getHeight() / 2);
        patchesPlayerIV= patchesPlayer.getMyImageView();
    }

    //Add the countdown timer
    @Override
    protected VBox getTimerButtonVBox() {
        Button button = new Button();
        button.setText("Start Game!");
        button.setOnAction(e -> {
                    button.visibleProperty().set(false);
                    setGameStarted(true);
                }
        );

        //Puts timer in Vbox
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPrefWidth(getMyScene().getWidth());
        vb.getChildren().add(button);
        vb.setLayoutY(30);

        return vb;
    }

    //Prevents overlapping of dodgeball throws
    private boolean allBallsOverLine() {
        for (Dodgeball dodgeball : getBallsInFlightList()) {
            if(dodgeball.getCenterX() > getMyWidth()/2){
                return false;
            }
        }
        return true;
    }
    //Chance that patches throws a ball
    public boolean tossBall(){
        Random rand = new Random();
        if (rand.nextInt(100) == 0) { //tosses a ball 1% of the steps
            return allBallsOverLine();
        }
        return false;
    }
}

//timeseconds =  SimpleIntegerProperty (START_TIME)
//start time = Integer