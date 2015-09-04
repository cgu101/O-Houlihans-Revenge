package main.java;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    Scene init(int w, int h, int l) {
        setMyWidth(w);
        setMyHeight(h);
        setMyStartLives(l);
        setEnemyStartLives(5);
        setMyStartTime(120);
        setMyMoveSpeed(60);
        setMyTossSpeed(8);
        setBallsInFlightList(new ArrayList<>());

        createHeroDodgeballer();
        createEnemyDodgeballer();
        setMyLivesHBox(getMyStartLives());
        setEnemyLivesHBox(getEnemyStartLives());

        //Create scene graph to organize scene
        setMyRoot(new Group());
        setMyScene(new Scene(getMyRoot(), getMyWidth(), getMyHeight(), Color.BLANCHEDALMOND));
        Line baseline = new Line(0, getMyHeight()*2/3, getMyWidth(), getMyHeight()*2/3);
        baseline.setStrokeWidth(8);
        Line midline = new Line(getMyWidth()*.55, getMyHeight()*2/3, getMyWidth()*.45, getMyHeight());
        midline.setStrokeWidth(8);
        Circle midCirc = new Circle(getMyWidth()/2, getMyHeight()*5/6, 15);
        midCirc.setStrokeWidth(6);
        setThrowBalllbl(new Label("'Space' -> Throw Ball"));
        getThrowBalllbl().visibleProperty().set(false);
        getThrowBalllbl().setStyle("-fx-font-size: 3em;");
        getThrowBalllbl().setLayoutY(getMyHeight() - 50);

        baseline.setFill(Color.ROSYBROWN);

        getMyRoot().getChildren().addAll(getThrowBalllbl(), baseline, midline, midCirc, getMyPlayerIV(), villainPlayerIV, getMyLivesHBox(), getEnemyLivesHBox());
        //respond to input
        getMyScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        getMyScene().setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return getMyScene();
    }

    @Override
    void step(double elapsedTime) {
        Random rand = new Random();
        if(myDodgeball == null && rand.nextInt(100) == 0){
            myDodgeball = new Dodgeball(getMyWidth()*.45,
                    getMyHeight()*2/3,
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

            if(currBall.isBeingThrown()) {
                if(!currBall.isEnemyBall()) {
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
                            Platform.exit();
                        }
                    }
                }
                else{
                    currBall.setCenterX(currBall.getCenterX() - villainPlayer.getMyTossSpeed());
                    currBall.setCenterY(currBall.getCenterX() * currBall.getTrajectorySlope() + currBall.getTrajectoryYIntercept());
//                     check for collisions
                    if (getMyPlayerIV().getBoundsInParent().intersects(currBall.getBoundsInParent())) {
                        iter.remove();
                        getMyRoot().getChildren().removeAll(currBall, getMyLivesHBox());

                        setMyLivesHBox(getMyLivesHBox().getChildren().size() - 1);
                        getMyRoot().getChildren().add(getMyLivesHBox());
                        if (getMyLivesHBox().getChildren().size() == 0) {
                            Platform.exit();
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

    @Override
    protected void handleKeyInput(KeyCode code) {
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
            default:
        }
    }

    @Override
    protected void handleKeyRelease(KeyCode code) {
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
        getMyPlayer().getMyImageView().setX(0);
        getMyPlayer().getMyImageView().setY((.6 * getMyHeight()) - getMyPlayer().getMyImageView().getBoundsInLocal().getHeight() / 2);
        setMyPlayerIV(getMyPlayer().getMyImageView());

    }

    @Override
    protected void createEnemyDodgeballer() {
        Image stand = new Image(getClass().getClassLoader().getResourceAsStream("main/resources/images/villainStand.png"));
        villainPlayer = new VillainDodgeballer(0, getMyMoveSpeed(), getMyTossSpeed(), new ImageView(stand));
        villainPlayer.getMyImageView().setX(getMyWidth() - villainPlayer.getMyImageView().getBoundsInLocal().getWidth());
        villainPlayer.getMyImageView().setY((.6 * getMyHeight()) - villainPlayer.getMyImageView().getBoundsInLocal().getHeight() / 2);
        villainPlayerIV= villainPlayer.getMyImageView();
    }

    @Override
    protected VBox getTimerVbox() {
        return null;
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
}
