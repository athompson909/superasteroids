package edu.byu.cs.superasteroids.model_classes;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.GameController;
import edu.byu.cs.superasteroids.game.ViewPort;

/**
 * Created by athom909 on 2/5/16.
 * The draw function on Ship should be a little different because it's a combination of different
 * images
 * this class does not need to extend anything
 */
public class Ship {

    // data from the JSON:
    private MainBody mainBody;
    private Cannon cannon;
    private ExtraPart extraPart;
    private Engine engine;
    private PowerCore powerCore;

    // data for GameController
    private int lives;

    private PointF cannonLocation;

    private PointF emitDiff;

    private PointF center; // the center in viewport coordinates
    private PointF worldCenter; // the center of the ship in world coordinates
    private RectF shipBounds;
    private float radius;

    /**
     * a default constructor so that a Ship object can be created and then its parts can be set
     * later
     */
    public Ship() {
        this.mainBody = null;
        this.cannon = null;
        this.extraPart = null;
        this.engine = null;
        this.powerCore = null;
        //center = null;
    }

    public Ship(MainBody mainBody, Cannon cannon, ExtraPart extraPart,
                Engine engine, PowerCore powerCore) {
        this.mainBody = mainBody;
        this.cannon = cannon;
        this.extraPart = extraPart;
        this.engine = engine;
        this.powerCore = powerCore;
        //center = null;
        lives = 3;
    }


    public MainBody getMainBody() {
        return mainBody;
    }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public ExtraPart getExtraPart() {
        return extraPart;
    }

    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public PowerCore getPowerCore() {
        return powerCore;
    }

    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    public boolean allShipPartsChosen() {
        return !(mainBody == null || cannon == null || extraPart == null || engine == null ||
                powerCore == null);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public PointF getCenter() {
        return center;
    }

    public void setCenter(PointF center) {
        this.center = center;
    }

    public PointF getWorldCenter() {
        return worldCenter;
    }

    public void setWorldCenter(PointF worldCenter) {
        this.worldCenter = worldCenter;
    }

    public RectF getShipBounds() {
        return shipBounds;
    }

    public void setShipBounds(RectF shipBounds) {
        this.shipBounds = shipBounds;
    }

    public void calculateShipBounds() {
        int halfWidth = (extraPart.getImageWidth() + mainBody.getImageWidth() +
                engine.getImageWidth()) / 2;
        int halfHeight = (cannon.getImageHeight() + engine.getImageHeight()) / 2;
        radius = (halfWidth + halfHeight) / 2;
        RectF r = new RectF(worldCenter.x - GameController.SCALE * halfWidth, worldCenter.y -
                GameController.SCALE * halfHeight, worldCenter.x + GameController.SCALE *
                halfWidth, worldCenter.y + GameController.SCALE * halfHeight);
        shipBounds = r;
    }

    public PointF getCannonOffsets(float scale, double angleRadians) {
        PointF attachInMb = mainBody.getCannonAttachAsPoint();
        PointF attachInCannon = cannon.getAttachPointAsPoint();
        PointF cannonOffset = new PointF(scale * getOffsetX(attachInMb,
                attachInCannon, cannon), scale * getOffsetY(attachInMb, attachInCannon, cannon));

        PointF emitP = cannon.getEmitPointP();
        emitDiff = GraphicsUtils.rotate(new PointF(scale * (attachInCannon.x + emitP.x),
                scale * (attachInCannon.y - emitP.y)), angleRadians);

        return GraphicsUtils.rotate(cannonOffset, angleRadians);
    }

    public PointF getExtraPartOffsets(float scale, double angleRadians) {
        PointF attachInMb = mainBody.getExtraAttachAsPoint();
        PointF attachInExtra = extraPart.getAttachPointAsPoint();
        return GraphicsUtils.rotate(new PointF(scale * getOffsetX(attachInMb, attachInExtra,
                        extraPart), scale * getOffsetY(attachInMb, attachInExtra, extraPart)),
                angleRadians);
    }

    public PointF getEngineOffsets(float scale, double angleRadians) {
        PointF attachInMb = mainBody.getEngineAttachAsPoint();
        PointF attachInEngine = engine.getAttachPointAsPoint();
        return GraphicsUtils.rotate(new PointF(scale * getOffsetX(attachInMb, attachInEngine,
                        engine), scale * getOffsetY(attachInMb, attachInEngine, engine)),
                angleRadians);
    }



    // using our SCALE final
    private float getOffsetX(PointF a, PointF b, ShipPart s) {
        return ((a.x - AsteroidsGame.getShip().getMainBody().getCenter().x) + (s.getCenter().x
                - b.x));
    }

    private float getOffsetY(PointF a, PointF b, ShipPart s) {
        return ((a.y - AsteroidsGame.getShip().getMainBody().getCenter().y) + (s.getCenter().y -
                b.y));
    }
    /**
     * This will be also called in a ViewPort static function
     * @param center this is just where the ship will be drawn in screen coordinates, the private
     *               center variable is in worldCoordinates
     * @param angleRadians for rotating
     * @param scale so the ship aint so big
     */
    public void drawShip(PointF center, double angleRadians, float scale) {
        this.center = center;
        angleRadians += GraphicsUtils.HALF_PI;
        float angleDegrees = (float) Math.toDegrees(angleRadians);

        if (mainBody != null) {
            // main body:
            DrawingHelper.drawImage(mainBody.getId(), center.x,
                    center.y, angleDegrees, scale, scale, 255);
        }

        if (cannon != null) {
            PointF offset = getCannonOffsets(scale, angleRadians);
            cannonLocation = new PointF(center.x + offset.x, center.y + offset.y);
            DrawingHelper.drawImage(cannon.getId(), cannonLocation.x, cannonLocation.y,
                    angleDegrees, scale, scale, 255);
            //setEmitCannonDiff();
        }

        if (extraPart != null) {
            PointF offset = getExtraPartOffsets(scale, angleRadians);
            DrawingHelper.drawImage(extraPart.getId(),
                    center.x + offset.x, center.y + offset.y, angleDegrees,
                    scale, scale, 255);
        }

        if (engine != null) {
            PointF offset = getEngineOffsets(scale, angleRadians);
            DrawingHelper.drawImage(engine.getId(),
                    center.x + offset.x, center.y + offset.y, angleDegrees,
                    scale, scale, 255);
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decrementLives() {
        --lives;
    }

//
//    public void setEmitCannonDiff() {
//        PointF p = cannon.getEmitPointP();
//        PointF q = cannon.getAttachPointAsPoint();
//        float x = p.x - q.x;
//        float y = p.y - q.y;
//        emitDiff = new PointF(cannonLocation.x + x, cannonLocation.y + y);
//    }

    public PointF getCannonLocation() {
        return cannonLocation;
    }

    public void setCannonLocation(PointF cannonLocation) {
        this.cannonLocation = cannonLocation;
    }

    public PointF getEmitDiff() {
        return emitDiff;
    }

    public void setEmitDiff(PointF emitDiff) {
        this.emitDiff = emitDiff;
    }
}
