package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by athom909 on 2/5/16.
 * This is also visible but it does not inherit from Visible Object, and it will be updated and drawn from Overridden methods in the Ship class
 */
public class ShipPart extends VisibleObject {

//    private String image;
//    private int imageHeight;
//    private int imageWidth;
    private String type;

    private int id;

    public ShipPart(String image, int imageHeight, int imageWidth, String type, int id) {
        super(image, imageHeight, imageWidth);
        this.type = type;
        this.id = id;
    }


    /**
     * This is the constructor that I'm actually using
     *  well it looks like things are getting kind of messy tsk tsk
     * @param image
     * @param imageWidth
     * @param imageHeight
     * @param type
     */
    public ShipPart(String image, int imageWidth, int imageHeight, String type) {
        super(image, imageWidth, imageHeight);
        this.type = type;
        id = 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}