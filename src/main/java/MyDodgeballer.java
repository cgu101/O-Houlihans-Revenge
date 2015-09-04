package main.java;

import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 9/1/15.
 */
public class MyDodgeballer extends Dodgeballer {
    private boolean holdingBall;

    public MyDodgeballer(int l, int s, ImageView i) {
        setMyMoveSpeed(s);
        setMyLives(l);
        setMyImageView(i);
    }


    @Override
    public boolean isHoldingBall() {
        return holdingBall;
    }

    @Override
    public void setHoldingBall(boolean holdingBall) {
        this.holdingBall = holdingBall;
    }
}
