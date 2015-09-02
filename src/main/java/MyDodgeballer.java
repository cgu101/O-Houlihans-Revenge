package main.java;

import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 9/1/15.
 */
public class MyDodgeballer extends Dodgeballer {
    public MyDodgeballer(int l, int s, ImageView i) {
        setMyMoveSpeed(s);
        setMyLives(l);
        setMyImageView(i);
    }


}
