package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by athom909 on 2/12/16.
 */
public class AttachableShipPart extends ShipPart {
    private String attachPoint;

    public AttachableShipPart(String image, int imageWidth, int imageHeight, String attachPoint,
                              String type) {
        super(image, imageWidth, imageHeight, type);
        this.attachPoint = attachPoint;
    }

    public String getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(String attachPoint) {
        this.attachPoint = attachPoint;
    }

    public PointF getAttachPointAsPoint() {
        return getCoordinates(attachPoint);
    }
}