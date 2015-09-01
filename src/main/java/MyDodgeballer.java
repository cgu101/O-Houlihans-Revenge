package main.java;

import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 9/1/15.
 */
public class MyDodgeballer extends Dodgeballer {
    public MyDodgeballer(int l, int s, ImageView i) {
        setSpeed(s);
        setLives(l);
        setImageView(i);
    }

    public int getSpeed(){
        return this.speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getLives(){
        return this.lives;
    }
    public void setLives(int lives){
        this.lives = lives;
    }

    public ImageView getImageView(){
        return this.imageView;
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }
}
