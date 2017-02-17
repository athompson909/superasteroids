package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by athom909 on 2/5/16.
 */
public class MainBody extends ShipPart {
    private String cannonAttach;
    private String engineAttach;
    private String extraAttach;
    private PointF cannonAttachAsPoint;
    private PointF engineAttachAsPoint;
    private PointF extraAttachAsPoint;

    public MainBody(String image, int imageWidth, int imageHeight, String cannonAttach,
                    String engineAttach, String extraAttach) {
        super(image, imageWidth, imageHeight, "MainBody");
        this.cannonAttach = cannonAttach;
        this.engineAttach = engineAttach;
        this.extraAttach = extraAttach;
        cannonAttachAsPoint = getCoordinates(cannonAttach);
        engineAttachAsPoint = getCoordinates(engineAttach);
        extraAttachAsPoint = getCoordinates(extraAttach);
    }

    public String getCannonAttach() {
        return cannonAttach;
    }

    public void setCannonAttach(String cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public String getEngineAttach() {
        return engineAttach;
    }

    public void setEngineAttach(String engineAttach) {
        this.engineAttach = engineAttach;
    }

    public String getExtraAttach() {
        return extraAttach;
    }

    public void setExtraAttach(String extraAttach) {
        this.extraAttach = extraAttach;
    }

    public PointF getCannonAttachAsPoint() {
        return cannonAttachAsPoint;
    }

    public void setCannonAttachAsPoint(PointF cannonAttachAsPoint) {
        this.cannonAttachAsPoint = cannonAttachAsPoint;
    }

    public PointF getEngineAttachAsPoint() {
        return engineAttachAsPoint;
    }

    public void setEngineAttachAsPoint(PointF engineAttachAsPoint) {
        this.engineAttachAsPoint = engineAttachAsPoint;
    }

    public PointF getExtraAttachAsPoint() {
        return extraAttachAsPoint;
    }

    public void setExtraAttachAsPoint(PointF extraAttachAsPoint) {
        this.extraAttachAsPoint = extraAttachAsPoint;
    }
}
