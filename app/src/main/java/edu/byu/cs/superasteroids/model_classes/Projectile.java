package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by athom909 on 2/5/16.
 * upon creation of a new Cannon object, a Projectile object can be properly instantiated
 * the image of a Projectile is "attackImage" in the JSON
 */
public class Projectile extends MovingObject {

    //private String emitPoint;
    private String attackSound;
    private int damage;

    private int contentManagerId;

    public Projectile(String image, int imageWidth, int imageHeight, String attackSound, int
            damage) {
        super(image, imageWidth, imageHeight);
        this.attackSound = attackSound;
        this.damage = damage;
        contentManagerId = 0;
    }

    public String getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getContentManagerId() {
        return contentManagerId;
    }

    public void setContentManagerId(int contentManagerId) {
        this.contentManagerId = contentManagerId;
    }
}
