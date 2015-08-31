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
    private Image image;
    private ImageView imageView;

    public Dodgeballer() {}

    public Dodgeballer(int l, int s, Image i){
        setSpeed(s);
        setLives(l);
        setImage(i);
        setImageView(new ImageView(i));
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
