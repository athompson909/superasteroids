package edu.byu.cs.superasteroids.model_classes;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.game.GameController;

/**
 * Created by athom909 on 2/5/16.
 */
public class Asteroid extends MovingObject {

    public static final float REGULAR_SCALE = 1.5f;
    public static final float RECT_REGULAR_SCALE = 1.1f;// was 1.25f
    public static final float HALF_REGULAR_SCALE = .75f;
    public static final float HALF_RECT_REGULAR_SCALE = .85f;

    enum AsteroidType {REGULAR, GROWING, OCTEROID};
    // 3 different possible types
    private String name; // the type of the asteroid
    private String type;
    private AsteroidType asteroidType; // same as the first but stored twice in JSON?!? so I'm doing it
    // twice
    // here

    private int contentManagerId;
    private int id;

    // these will only be set via setters in GameController
    private PointF worldCenter;
    private int speed;
    private double angleRadians;
    private RectF bounds;

    private int health;
    private int splitCount;

    private float regularScale;
    private float rectRegularScale;

    public Asteroid(Asteroid a, double angleRadians, PointF worldCenter) {
        super(a.getImage(), a.getImageHeight() / 2, a.getImageWidth() / 2);
        name = a.getName();
        type = a.getType();

        asteroidType = a.getAsteroidType();
        id = a.getId();

        contentManagerId = a.getContentManagerId();

        speed = 255;

        health = 5;
        splitCount = 1;

        regularScale = HALF_REGULAR_SCALE;
        rectRegularScale = HALF_RECT_REGULAR_SCALE;
        setWorldCenterAndBounds(worldCenter);
        this.angleRadians = angleRadians;
    }

    public Asteroid(String image, int imageHeight, int imageWidth, String name, String type) {
        super(image, imageHeight, imageWidth);
        this.name = name;
        this.type = type;
        switch (type) {
            case "regular":
                asteroidType = AsteroidType.REGULAR;
                break;
            case "growing":
                asteroidType = AsteroidType.GROWING;
                break;
            case "octeroid":
                asteroidType = AsteroidType.OCTEROID;
            default:
                asteroidType = null;
        }

        speed = 225; // hard coding this ***

        health = 5;
        splitCount = 0;
        regularScale = REGULAR_SCALE;
        rectRegularScale = RECT_REGULAR_SCALE;
    }

    public Asteroid(String image, int imageHeight, int imageWidth, String name, String type,
                    int contentManagerId) {
        super(image, imageHeight, imageWidth);
        this.name = name;
        this.type = type;
        switch (type) {
            case "regular":
                asteroidType = AsteroidType.REGULAR;
                break;
            case "growing":
                asteroidType = AsteroidType.GROWING;
                break;
            case "octeroid":
                asteroidType = AsteroidType.OCTEROID;
            default:
                asteroidType = null;
        }

        speed = 325; // hard coding this ***

        health = 5;
        splitCount = 0;
        regularScale = REGULAR_SCALE;
        rectRegularScale = RECT_REGULAR_SCALE;

        this.contentManagerId = contentManagerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AsteroidType getAsteroidType() {
        return asteroidType;
    }

    public void setAsteroidType(AsteroidType asteroidType) {
        this.asteroidType = asteroidType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentManagerId() {
        return contentManagerId;
    }

    public void setContentManagerId(int contentManagerId) {
        this.contentManagerId = contentManagerId;
    }

    public PointF getWorldCenter() {
        return worldCenter;
    }

    public void setWorldCenter(PointF worldCenter) {
        this.worldCenter = worldCenter;
    }

    public void setWorldCenterAndBounds(PointF worldCenter) {
        this.worldCenter = worldCenter;
        setBoundsToScale();
    }

    public void setBoundsToScale() {
        int halfHeight = imageHeight / 2;
        int halfWidth = imageWidth / 2;
        bounds = new RectF(worldCenter.x - rectRegularScale * halfWidth, worldCenter.y -
                rectRegularScale * halfHeight, worldCenter.x + rectRegularScale * halfWidth,
                worldCenter.y + rectRegularScale * halfHeight);
    }

    public int getSpeedA() {
        return speed;
    }

    public void setSpeedA(int speed) {
        this.speed = speed;
    }

    public double getAngleRadiansA() {
        return angleRadians;
    }

    public void setAngleRadiansA(double angleRadians) {
        this.angleRadians = angleRadians;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        --health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSplitCount() {
        return splitCount;
    }

    public void setSplitCount(int splitCount) {
        this.splitCount = splitCount;
    }

    public void incrementSplitCount() {
        ++splitCount;
    }

    public float getRegularScale() {
        return regularScale;
    }

    public void setRegularScale(float regularScale) {
        this.regularScale = regularScale;
    }

    public float getRectRegularScale() {
        return rectRegularScale;
    }

    public void setRectRegularScale(float rectRegularScale) {
        this.rectRegularScale = rectRegularScale;
    }


}