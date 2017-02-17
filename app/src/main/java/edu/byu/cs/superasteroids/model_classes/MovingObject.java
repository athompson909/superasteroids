package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by athom909 on 2/5/16.
 * DOES NOT EXTEND POSITIONED OBJECT BECAUSE IT IS NOT STATIONARY and has no fixed position
 * probably does not need getCoordinates(...) from VisibleObject?
 */
public class MovingObject extends VisibleObject {

    private int speed;
    private float angleRadians;


    /**
     * So pretty much MovingObject stores speed and angleRadians but those don't get set until later
     * @param image for superclass
     * @param imageHeight for superclass
     * @param imageWidth for superclass
     */
    public MovingObject(String image, int imageHeight, int imageWidth) {
        super(image, imageWidth, imageHeight);
        speed = 0;
        angleRadians = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getAngleRadians() {
        return angleRadians;
    }

    public void setAngleRadians(float angleRadians) {
        this.angleRadians = angleRadians;
    }
}
