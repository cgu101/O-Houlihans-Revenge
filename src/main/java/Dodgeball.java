package main.java;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Created by connorusry on 9/3/15.
 */
public class Dodgeball extends Circle {
    private double trajectorySlope;
    private double trajectoryYIntercept;

    public Dodgeball(double centerX, double centerY, double destinationX, double destinationY, double radius, Paint fill) {
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(radius);
        setFill(fill);

        setTrajectorySlope((destinationY - centerY)/(destinationX - centerX));
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
}
