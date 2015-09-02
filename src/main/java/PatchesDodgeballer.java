package main.java;

import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Created by connorusry on 9/1/15.
 */
public class PatchesDodgeballer extends Dodgeballer {

    public PatchesDodgeballer(int l, int ms, int ts, ImageView i) {
        setMyLives(l);
        setMyMoveSpeed(ms);
        setMyTossSpeed(ts);
        setMyImageView(i);
    }

    public boolean tossBall(){
        Random rand = new Random();
        if (rand.nextInt(100) == 0) { //tosses a ball 10% of the time
            return true;
        }
        return false;
    }


}
