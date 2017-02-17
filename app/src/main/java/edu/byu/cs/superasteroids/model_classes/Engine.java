package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by athom909 on 2/5/16.
 * you could set shipspeed from this I guess
 */
public class Engine extends AttachableShipPart {
    // this is an attachable ship part
    private int baseSpeed;
    private int baseTurnRate;

    public Engine(String image, int imageWidth, int imageHeight, String attachPoint, int baseSpeed,
                  int baseTurnRate) {
        super(image, imageWidth, imageHeight, attachPoint, "Engine");
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }
}
