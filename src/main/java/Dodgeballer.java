package main.java;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 8/31/15.
 */
public abstract class Dodgeballer {

    int lives;
    int speed;
    ImageView imageView;

    public Dodgeballer() {}

    abstract int getSpeed();
    abstract void setSpeed(int speed);

    abstract int getLives();
    abstract void setLives(int lives);

    abstract ImageView getImageView();
    abstract void setImageView(ImageView imageView);
}
