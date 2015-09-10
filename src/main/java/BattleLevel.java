// This entire file is part of my masterpiece.
// Connor Usry

package main.java;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by connorusry on 8/30/15.
 */
public class BattleLevel extends Level{

    private VillainDodgeballer villainPlayer;
    private ImageView villainPlayerIV;
    private boolean ducking;
    private int enemyStartLives;
    private HBox enemyLivesHBox;
    private Dodgeball myDodgeball;
    private Label throwBalllbl;


    @Override
    public Scene init(Stage ps,int w, int h, int l) {
        setCurrentPrimaryStage(ps);
        setMyWidth(w);
        setMyHeight(h);
        setMyStartLives(l);
        setEnemyStartLives(5);
        setMyStartTime(120);
        setTimeRemaining(getMyStartTime());
        setMyMoveSpeed(60);
        setMyTossSpeed(8);
        setGameStarted(false);
        setBallsInFlightList(new ArrayList<>());

        createHeroDodgeballer();
        createEnemyDodgeballer();
        setMyLivesHBox(getMyStartLives());
        setEnemyLivesHBox(getEnemyStartLives());

        //Create scene graph to organize scene
        setMyRoot(new Group());
        setMyScene(new Scene(getMyRoot(), getMyWidth(), getMyHeight(), Color.BLANCHEDALMOND));
        setUpScene();


        //respond to input
        getMyScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        getMyScene().setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return getMyScene();
    }

    @Override
    public void step(double elapsedTime) {
        Random rand = new Random();
        if(getTimeRemaining() < 0 && getGameStarted()){
            exitLevel("You got beat, you didn't defeat Goodman!!");
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
            if (myDodgeball == null && rand.nextInt(100) == 0) {

                myDodgeball = new Dodgeball(getMyWidth() * .45,
                        getMyHeight() * 2 / 3,
                        villainPlayerIV.getX(),
                        villainPlayerIV.getY(),
                        20,
                        Color.RED,
                        false,
                        false);

                getBallsInFlightList().add(myDodgeball);
                getMyRoot().getChildren().add(myDodgeball);

            }
            if (tossBall()) {
                int highOrLow = rand.nextInt(3) * 20;
                Dodgeball newEnemyBall = new Dodgeball(villainPlayerIV.getX(),
                        villainPlayerIV.getY(),
                        getMyPlayerIV().getX(),
                        getMyPlayerIV().getY() + highOrLow,
                        20,
                        Color.PURPLE,
                        true,
                        true);

                getBallsInFlightList().add(newEnemyBall);
                getMyRoot().getChildren().add(newEnemyBall);
            }
            Iterator<Dodgeball> iter = getBallsInFlightList().iterator();
            while (iter.hasNext()) {
                Dodgeball currBall = iter.next();

                if (currBall.isBeingThrown()) {
                    if (!currBall.isEnemyBall()) {
                        currBall.setCenterX(currBall.getCenterX() + villainPlayer.getMyTossSpeed());
                        currBall.setCenterY(currBall.getCenterX() * currBall.getTrajectorySlope() + currBall.getTrajectoryYIntercept());
                        // check for collisions
                        if (villainPlayerIV.getBoundsInParent().intersects(currBall.getBoundsInParent())) {
                            int currLives = enemyLivesHBox.getChildren().size();
                            getMyRoot().getChildren().removeAll(currBall, enemyLivesHBox);
                            setEnemyLivesHBox(currLives - 1);
                            getMyRoot().getChildren().add(getEnemyLivesHBox());
                            iter.remove();
                            myDodgeball = null;
                            if (enemyLivesHBox.getChildren().size() == 0) {
                                exitLevel("You won!!");
                            }
                        }
                    } else {
                        currBall.setCenterX(currBall.getCenterX() - villainPlayer.getMyTossSpeed());
                        currBall.setCenterY(currBall.getCenterX() * currBall.getTrajectorySlope() + currBall.getTrajectoryYIntercept());
//                     check for collisions
                        if (getMyPlayerIV().getBoundsInParent().intersects(currBall.getBoundsInParent())) {
                            iter.remove();
                            getMyRoot().getChildren().removeAll(currBall, getMyLivesHBox());

                            setMyLivesHBox(getMyLivesHBox().getChildren().size() - 1);
                            getMyRoot().getChildren().add(getMyLivesHBox());
                            if (getMyLivesHBox().getChildren().size() == 0) {
                                exitLevel("You got beat!!");
                            }
                        }

                    }
                    double currX = currBall.getCenterX();
                    if (currX < 0) {
                        iter.remove();
                        getMyRoot().getChildren().remove(currBall);
                    }
                }

            }
        }
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        double xLoc = getMyPlayerIV().getBoundsInParent().getMinX();
        double yLoc = getMyPlayerIV().getY();
        double yLocBottom = yLoc + getMyPlayerIV().getBoundsInParent().getHeight()/2;
        double moveSpeed = getMyPlayer().getMyMoveSpeed();
        switch (code) {
            case RIGHT:
                //Check to see if crossed half court or ducking
                if(xLoc + getMyPlayerIV().getBoundsInLocal().getWidth() + moveSpeed < getMyWidth() / 2 && !isDucking()){
                    getMyPlayerIV().setX(xLoc + moveSpeed);
                }
                //Check to see if player picks up ball
                if(myDodgeball != null){
                    if (getMyPlayerIV().getBoundsInParent().intersects(myDodgeball.getBoundsInParent())) {
                        getMyRoot().getChildren().remove(getMyDodgeball());
                        getMyPlayer().setHoldingBall(true);
                        getMyRoot().getChildren().get(0).setVisible(true);
                    }
                }



                break;
            case LEFT:
                //Make sure not too far out of window or ducking
                if(xLoc - moveSpeed > 0 && !isDucking()){
                    getMyPlayerIV().setX(xLoc - moveSpeed);
                }
                break;
            case UP:
                //Joe Jumps, prevents jump if already in the middle of one
                if(getJumpTransition() == null || getJumpTransition().getStatus() == Animation.Status.STOPPED) {
                    setJumpTransition(new TranslateTransition(Duration.millis(300), getMyPlayerIV()));
                    getJumpTransition().interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
                    getJumpTransition().setByY(-getMyPlayerIV().getY());
                    getJumpTransition().setAutoReverse(true);
                    getJumpTransition().setCycleCount(2);
                    getJumpTransition().play();
                }
                break;
            case DOWN:
                //Prevents "duck" if already ducking
                if(!isDucking()) {
                    Boolean wasHoldingBall = getMyPlayer().isHoldingBall();
                    getMyRoot().getChildren().removeAll(getMyPlayerIV());
                    Image duck = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/duck.png"));
                    setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(duck)));
                    getMyPlayer().setHoldingBall(wasHoldingBall);
                    setMyPlayerIV(getMyPlayer().getMyImageView());
                    getMyPlayer().getMyImageView().setX(xLoc);
                    getMyPlayer().getMyImageView().setY(yLocBottom);
                    getMyRoot().getChildren().add(getMyPlayerIV());
                    setDucking(true);
                }
                break;
            case SPACE:
                if (getMyPlayer().isHoldingBall()) {
                    setMyDodgeball(new Dodgeball(getMyPlayerIV().getX(),
                            getMyPlayerIV().getY(),
                            villainPlayerIV.getX(),
                            villainPlayerIV.getY(),
                            20,
                            Color.RED,
                            false,
                            true));
                    getMyPlayer().setHoldingBall(false);
                    getBallsInFlightList().add(getMyDodgeball());
                    getThrowBalllbl().setVisible(false);
                    getMyRoot().getChildren().add(getMyDodgeball());
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
                if(getGameStarted() == false && getMyStartLives() > 1){
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
        }
    }

    @Override
    public void handleKeyRelease(KeyCode code) {
        double xLoc = getMyPlayerIV().getX();
        switch (code) {
            case DOWN:
                boolean holdingBall = getMyPlayer().isHoldingBall();
                getMyRoot().getChildren().removeAll(getMyPlayerIV());
                Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
                setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(stand)));

                getMyPlayer().setHoldingBall(holdingBall);
                getMyPlayer().getMyImageView().setX(xLoc);
                getMyPlayer().getMyImageView().setY((.6 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
                setMyPlayerIV(getMyPlayer().getMyImageView());
                getMyRoot().getChildren().add(getMyPlayerIV());
                setDucking(false);
                break;
            default:
        }
    }

    @Override
    public void setMyLivesHBox(int l) {
        // TODO: Figure out why standard iter isn't working for circle
        setMyLivesHBox(new HBox());
        for(int i = 0; i < l; i++){
            Circle life = new Circle(20, Color.RED);
            getMyLivesHBox().getChildren().add(life);
        }

    }

    @Override
    public void createHeroDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/stand.png"));
        setMyPlayer(new MyDodgeballer(getMyStartLives(), getMyMoveSpeed(), new ImageView(stand)));
        getMyPlayer().getMyImageView().setX(0);
        getMyPlayer().getMyImageView().setY((.6 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
        setMyPlayerIV(getMyPlayer().getMyImageView());

    }

    @Override
    public void createEnemyDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/villainStand.png"));
        villainPlayer = new VillainDodgeballer(0, getMyMoveSpeed(), getMyTossSpeed(), new ImageView(stand));
        villainPlayer.getMyImageView().setX(getMyWidth() - villainPlayer.getMyImageView().getBoundsInLocal().getWidth());
        villainPlayer.getMyImageView().setY((.6 * getMyHeight()) - villainPlayer.getMyImageView().getBoundsInLocal().getHeight() / 2);
        villainPlayerIV= villainPlayer.getMyImageView();
    }

    //Add the countdown timer
    @Override
    public VBox getTimerButtonVBox() {
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

    public boolean isDucking() {
        return ducking;
    }

    public void setDucking(boolean ducking) {
        this.ducking = ducking;
    }

    public int getEnemyStartLives() {
        return enemyStartLives;
    }

    public void setEnemyStartLives(int enemyStartLives) {
        this.enemyStartLives = enemyStartLives;
    }

    public HBox getEnemyLivesHBox() {
        return enemyLivesHBox;
    }

    public void setEnemyLivesHBox(int l) {
        HBox enemyLives = new HBox();
        enemyLives.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < l; i++) {
            Circle life = new Circle(20, Color.PURPLE);
            enemyLives.getChildren().add(life);
        }
        this.enemyLivesHBox = enemyLives;
        enemyLivesHBox.setLayoutX(getMyWidth() -getMyWidth()*.17);
    }
    //Prevents overlapping of dodgeball throws
    private boolean allBallsOverLine() {
        for (Dodgeball dodgeball : getBallsInFlightList()) {
            if(dodgeball.isEnemyBall() && dodgeball.getCenterX() > getMyWidth()*.6){
                return false;
            }
        }
        return true;
    }
    //Chance that patches throws a ball
    public boolean tossBall(){
        Random rand = new Random();
        if (rand.nextInt(80) == 0) {
            return allBallsOverLine();
        }
        return false;
    }

    public Dodgeball getMyDodgeball() {
        return myDodgeball;
    }

    public void setMyDodgeball(Dodgeball myDodgeball) {
        this.myDodgeball = myDodgeball;
    }

    public Label getThrowBalllbl() {
        return throwBalllbl;
    }

    public void setThrowBalllbl(Label throwBalllbl) {
        this.throwBalllbl = throwBalllbl;
    }

    @Override
    void setUpScene() {
        Line baseline = new Line(0, getMyHeight()*2/3, getMyWidth(), getMyHeight()*2/3);
        baseline.setStrokeWidth(8);
        baseline.setFill(Color.ROSYBROWN);
        Line midline = new Line(getMyWidth()*.55, getMyHeight()*2/3, getMyWidth()*.45, getMyHeight());
        midline.setStrokeWidth(8);
        Circle midCirc = new Circle(getMyWidth()/2, getMyHeight()*5/6, 15);
        midCirc.setStrokeWidth(6);
        Label timerLabel = new Label(Double.toString(getTimeRemaining()));
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.setLayoutX(getMyWidth() / 2 - getMyWidth() * .05);
        timerLabel.setLayoutY(getMyHeight() * .2);
        setTimerLabel(timerLabel);
        setThrowBalllbl(new Label("'Space' -> Throw Ball"));
        getThrowBalllbl().visibleProperty().set(false);
        getThrowBalllbl().setStyle("-fx-font-size: 3em;");
        getThrowBalllbl().setLayoutY(getMyHeight() - 50);


        getMyRoot().getChildren().addAll(getTimerLabel(), getTimerButtonVBox(), getThrowBalllbl(), baseline, midline, midCirc, getMyPlayerIV(), villainPlayerIV, getMyLivesHBox(), getEnemyLivesHBox());
    }
}

