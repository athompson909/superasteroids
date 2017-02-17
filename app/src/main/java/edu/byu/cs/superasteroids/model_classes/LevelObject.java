package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Arrays;
import java.util.List;

/**
 * Created by athom909 on 2/9/16.
 */
public class LevelObject {

    private int level;

    private String position;
    private int objectId;
    private float scale;

    public LevelObject(int level, String position, int objectId, float scale) {
        this.level = level;
        this.position = position;
        this.objectId = objectId;
        this.scale = scale;
    }

//    private ArrayList<Cannon> cannons;
//    private ArrayList<Engine> engines;
//    private ArrayList<ExtraPart> extraParts;
//    private ArrayList<MainBody> mainBodies;
//    private ArrayList<PowerCore> powerCores;
//    private ArrayList<ShipPart> shipParts;

//    private ArrayList<ImageObject> imageObjects;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return
     */
    public PointF getCoordinates() {
        if (position != null) {
            List<String> list = Arrays.asList(position.split(",")); // should always be of size two
            return new PointF(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
        } else // this would be an error
            return null;
    }
}
