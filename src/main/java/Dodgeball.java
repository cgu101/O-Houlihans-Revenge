package main.java;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Created by connorusry on 9/3/15.
 */
public class Dodgeball extends Circle {
    private double trajectorySlope;
    private double trajectoryYIntercept;
    private boolean enemyBall;
    private boolean beingThrown;

    public Dodgeball(double centerX, double centerY, double destinationX, double destinationY, double radius, Paint fill,boolean enemy, boolean beingThrown) {
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(radius);
        setFill(fill);
        setEnemyBall(enemy);
        setBeingThrown(beingThrown);

        setTrajectorySlope((destinationY - centerY) / (destinationX - centerX));
        setTrajectoryYIntercept(centerY - getTrajectorySlope()*centerX);
    }
    public Dodgeball(double centerX, double centerY, double destinationX, double destinationY, double radius, Paint fill) {
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(radius);
        setFill(fill);

        setTrajectorySlope((destinationY - centerY) / (destinationX - centerX));
        setTrajectoryYIntercept(centerY - getTrajectorySlope()*centerX);
    }

    public double getTrajectorySlope() {
        return trajectorySlope;
    }

    public void setTrajectorySlope(double trajectorySlope) {
        this.trajectorySlope = trajectorySlope;
    }

    public double getTrajectoryYIntercept() {
        return trajectoryYIntercept;
    }

    public void setTrajectoryYIntercept(double trajectoryYIntercept) {
        this.trajectoryYIntercept = trajectoryYIntercept;
    }

    public boolean isEnemyBall() {
        return enemyBall;
    }

    public void setEnemyBall(boolean enemyBall) {
        this.enemyBall = enemyBall;
    }

    public boolean isBeingThrown() {
        return beingThrown;
    }

    public void setBeingThrown(boolean beingThrown) {
        this.beingThrown = beingThrown;
    }
}
