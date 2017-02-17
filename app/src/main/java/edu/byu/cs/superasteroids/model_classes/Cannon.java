package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by athom909 on 2/5/16.
 */
public class Cannon extends AttachableShipPart {

    private String emitPoint;

    private PointF emitPointP;
    Projectile bullet;

    private int soundContentManagerId;

    public Cannon(String image, int imageWidth, int imageHeight, String attachPoint,
                  String emitPoint, String attackImage, int attackImageWidth, int attackImageHeight,
                  String attackSound, int damage) {
        super(image, imageWidth, imageHeight, attachPoint, "Cannon");
        this.emitPoint = emitPoint;
        this.emitPointP = getCoordinates(emitPoint);
        bullet = new Projectile(attackImage, attackImageWidth, attackImageHeight, attackSound,
                damage);
    }

    public PointF getEmitPointP() {
        return emitPointP;
    }

    public void setEmitPointP(PointF emitPointP) {
        this.emitPointP = emitPointP;
    }

    public String getEmitPoint() {
        return emitPoint;
    }

    public void setEmitPoint(String emitPoint) {
        this.emitPoint = emitPoint;
    }

    public Projectile getBullet() {
        return bullet;
    }

    /**
     * should only work if bullet is a Projectile object
     *
     * @param bullet
     */
    public void setBullet(Projectile bullet) {
        this.bullet = bullet;
    }

    public int getSoundContentManagerId() {
        return soundContentManagerId;
    }

    public void setSoundContentManagerId(int soundContentManagerId) {
        this.soundContentManagerId = soundContentManagerId;
    }
}
