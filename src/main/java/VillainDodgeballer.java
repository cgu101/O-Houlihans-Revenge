package main.java;

import javafx.scene.image.ImageView;

/**
 * Created by connorusry on 9/1/15.
 */
public class VillainDodgeballer extends Dodgeballer {
    public VillainDodgeballer(int l, int s, ImageView i) {
        setMoveSpeed(s);
        setLives(l);
        setImageView(i);
    }

    public int getMoveSpeed(){
        return this.moveSpeed;
    }
    public void setMoveSpeed(int speed){
        this.moveSpeed = speed;
    }

    int getTossSpeed() {
        return this.tossSpeed;
    }
    void setTossSpeed(int tossSpeed) {
        this.tossSpeed = tossSpeed;
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
