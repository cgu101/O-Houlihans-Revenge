package main.java;

import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 9/1/15.
 */
public class VillainDodgeballer extends Dodgeballer {
    public VillainDodgeballer(int l, int s, int ts, ImageView i) {
        setMyLives(l);
        setMyMoveSpeed(s);
        setMyTossSpeed(ts);
        setMyImageView(i);
    }

}
