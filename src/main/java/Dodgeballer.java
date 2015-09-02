package main.java;

import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 8/31/15.
 */
public abstract class Dodgeballer {

    private int myLives;
    private int myMoveSpeed;
    private int myTossSpeed;
    private ImageView myImageView;
    private boolean myHoldingBall;


    public Dodgeballer() {}


    public int getMyLives() {
        return myLives;
    }

    public void setMyLives(int myLives) {
        this.myLives = myLives;
    }

    public int getMyMoveSpeed() {
        return myMoveSpeed;
    }

    public void setMyMoveSpeed(int myMoveSpeed) {
        this.myMoveSpeed = myMoveSpeed;
    }

    public int getMyTossSpeed() {
        return myTossSpeed;
    }

    public void setMyTossSpeed(int myTossSpeed) {
        this.myTossSpeed = myTossSpeed;
    }

    public ImageView getMyImageView() {
        return myImageView;
    }

    public void setMyImageView(ImageView myImageView) {
        this.myImageView = myImageView;
    }

    public boolean isMyHoldingBall() {
        return myHoldingBall;
    }

    public void setMyHoldingBall(boolean myHoldingBall) {
        this.myHoldingBall = myHoldingBall;
    }
}
