package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by athom909 on 2/5/16.
 * this is not visible actually so it's kind of a complicated deal, but I think it is in the
 *  ship builder part actually
 */
public class PowerCore extends ShipPart {
    private int cannonBoost;
    private int engineBoost;

    public PowerCore(String image, int cannonBoost,
                     int engineBoost) {
        super(image, 0, 0, "PowerCore");
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }
}
