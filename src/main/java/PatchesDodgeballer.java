package main.java;

import javafx.scene.image.ImageView;

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
}
