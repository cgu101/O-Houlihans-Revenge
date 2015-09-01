package main.java;

import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Created by connorusry on 9/1/15.
 */
public class PatchesDodgeballer extends Dodgeballer {

    public PatchesDodgeballer(int l, int ms, int ts, ImageView i) {
        setLives(l);
        setMoveSpeed(ms);
        setTossSpeed(ts);
        setImageView(i);
    }


    public int getLives(){
        return this.lives;
    }
    public void setLives(int lives){
        this.lives = lives;
    }

    public int getMoveSpeed(){
        return this.moveSpeed;
    }
    public void setMoveSpeed(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    public int getTossSpeed(){
        return this.tossSpeed;
    }
    public void setTossSpeed(int tossSpeed){
        this.tossSpeed = tossSpeed;
    }

    public ImageView getImageView(){
        return this.imageView;
    }
    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public boolean tossBall(){
        Random rand = new Random();
        if (rand.nextInt(100) == 0) { //tosses a ball 10% of the time
            return true;
        }
        return false;
    }
}
