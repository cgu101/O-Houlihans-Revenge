package main.java;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 8/31/15.
 */
public abstract class Dodgeballer {

    int lives;
    int moveSpeed;
    int tossSpeed;
    ImageView imageView;

    public Dodgeballer() {}

    abstract int getLives();
    abstract void setLives(int lives);

    abstract int getMoveSpeed();
    abstract void setMoveSpeed(int moveSpeed);

    abstract int getTossSpeed();
    abstract void setTossSpeed(int tossSpeed);


    abstract ImageView getImageView();
    abstract void setImageView(ImageView imageView);



}
