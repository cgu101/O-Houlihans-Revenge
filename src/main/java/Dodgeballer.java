package main.java;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 8/31/15.
 */
public class Dodgeballer {

    private int lives;
    private int speed;
    private ImageView imageView;

    public Dodgeballer() {}

    public Dodgeballer(int l, int s, ImageView i){
        setSpeed(s);
        setLives(l);
        setImageView(i);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
