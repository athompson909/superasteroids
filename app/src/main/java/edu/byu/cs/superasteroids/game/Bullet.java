package edu.byu.cs.superasteroids.game;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by athom909 on 3/5/16.
 * just stores some values so that I can know where to fire what
 */
public class Bullet {

    private PointF position;
    private double angleRadians;
    private RectF bounds;

    public Bullet(PointF position, double angleRadians, RectF bounds) {
        this.position = position;
        this.angleRadians = angleRadians;
        this.bounds = bounds;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public double getAngleRadians() {
        return angleRadians;
    }

    public void setAngleRadians(double angleRadians) {
        this.angleRadians = angleRadians;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }
}
