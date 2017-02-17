package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;

/**
 * Created by athom909 on 2/5/16.
 * for stationary objects
 */
public class PositionedObject extends VisibleObject {

    protected PointF position;

    public PositionedObject(String image, int imageHeight, int imageWidth, String position) {
        super(image, imageHeight, imageWidth);
        this.position = getCoordinates(position);
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }
}
