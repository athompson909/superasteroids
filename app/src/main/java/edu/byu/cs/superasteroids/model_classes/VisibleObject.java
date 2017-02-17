package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Arrays;
import java.util.List;

/**
 * Created by athom909 on 2/5/16.
 * in the database and JSON everything is ordered width then height but in
 *  VisibleObject it is height then width ***remember that***
 */
public class VisibleObject {

    protected String image;
    protected int imageWidth;
    protected int imageHeight;

    protected PointF center;

    public VisibleObject(String image, int imageWidth, int imageHeight) {
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        center = new PointF(imageWidth / 2, imageHeight / 2);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public PointF getCenter() {
        return center;
    }

    public void setCenter(PointF center) {
        this.center = center;
    }

    /**
     *
     * @param str only strings that look like "(...,...)" can be acceptable
     * @return
     */
    public PointF getCoordinates(String str) {
        if (str != null) {
            List<String> list = Arrays.asList(str.split(",")); // should always be of size two
            return new PointF(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
        }
        else // this would be an error
            return null;
    }

}
