package edu.byu.cs.superasteroids.game;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.Asteroid;
import edu.byu.cs.superasteroids.model_classes.Ship;
import edu.byu.cs.superasteroids.model_classes.VisibleObject;

/**
 * Created by athom909 on 3/2/16.
 *
 * Your viewport should have the same dimensions as the Android view hosting
 the game. These dimensions can be accessed through the DrawingHelper.
 The viewport should be made up of static variables and functions. This allows
 its functionality to be accessed by any game element. The viewport rectangle
 MUST stay in the bounds of the world/level. Give your viewport functionality
 to transform world coordinates into view coordinates. When possible, the
 viewport should stay centered on the ship position.
 *
 * I just decided to make this a singleton so I hope that's ok...
 *
 *
 *
 * MAIN IDEA: objScreenPos = objWorldPos - viewportWorldPos
 */
public class ViewPort {

    private static float worldWidth;
    private static float worldHeight;

    private static float height;
    private static float width;
    private static PointF center; // the center of the viewport
    private static PointF worldPosition; // the (world coordinates of) top left corner of the
    // viewport
    private static PointF worldCenter; // the exact center of the world (doesn't change)
        // 1500 x 1500
    private static VisibleObject bulletDimensions;
    private static PointF emitPoint;

    private static boolean circleOn = false; // can you do this?


    public ViewPort() {
        height = DrawingHelper.getGameViewHeight();
        width = DrawingHelper.getGameViewWidth();
        center = new PointF(height / 2, width / 2);
        bulletDimensions = GameController.getBulletDimensions();
    }

    public static void updateValues() {
        height = DrawingHelper.getGameViewHeight();
        width = DrawingHelper.getGameViewWidth();
        center = new PointF(height / 2, width / 2);
        bulletDimensions = GameController.getBulletDimensions();
    }

    public static void setWorldDimensions(float width, float height) {
        worldHeight = height;
        worldWidth = width;
        worldCenter = new PointF(worldWidth / 2, worldHeight / 2);
        worldPosition = new PointF((worldWidth / 2) - (DrawingHelper.getGameViewWidth() / 2),
                (worldHeight / 2) - (DrawingHelper.getGameViewHeight() / 2));
    }

    public static void updateWorldDimensions() {
        worldPosition = new PointF((worldWidth / 2) - (DrawingHelper.getGameViewWidth() / 2),
                (worldHeight / 2) - (DrawingHelper.getGameViewHeight() / 2));
    }

    public static PointF transformToWorld(PointF shipCenter, PointF screenCoordinates) {
        return new PointF(screenCoordinates.x + shipCenter.x, screenCoordinates
                .y + shipCenter.y);
    }


    /**
     *
     * some method to give ViewPort the ability to transform world coordinates into view coordinates
     */
    public static PointF transformCoordinates(PointF worldCoordinates) {
        //updateWorldDimensions();
        return new PointF(worldCoordinates.x - worldPosition.x, worldCoordinates
                .y - worldPosition.y);
    }

    private static float transformX(float x) {
        return x - worldPosition.x;
    }

    private static float transformY(float y) {
        return y - worldPosition.y;
    }

    public static void drawImage(int imageId, PointF p, float rotationDegrees, float scaleX,
                                 float scaleY, int alpha) {
        PointF screenPosition = transformCoordinates(p);
        DrawingHelper.drawImage(imageId, screenPosition.x, screenPosition.y, rotationDegrees,
                scaleX, scaleY, alpha);
    }

    /**
     * Takes in some world coordinates that get remembered in the ship and then transforms them
     * to screen coordinates and draws the Ship
     * @param ship
     * @param p in worldCoordinates
     * @param angleRadians angle
     * @param scale SCALE constant
     */
    public static void drawShip(Ship ship, PointF p, double angleRadians, float scale) {
        setNewWorldPosition(p);
        ship.setWorldCenter(p);
        PointF screenPosition = transformCoordinates(p);

        ship.calculateShipBounds();
        RectF r = ship.getShipBounds();
        ship.drawShip(screenPosition, angleRadians, scale);
        //drawRect(r);
        if(circleOn)
            DrawingHelper.drawFilledCircle(screenPosition, ship.getRadius() *
                    GameController.SCALE, Color.RED, 125);
    }

    public static void setCircleOn() {
        circleOn = true;
    }

    public static void setCircleOff() {
        circleOn = false;
    }

    private static void setNewWorldPosition(PointF p) {
        float x = p.x - (width / 2), y = p.y - (height / 2);
        if(p.x < (width / 2) || p.x > (worldWidth - (width / 2)))
            x = worldPosition.x;
        if(p.y < (height / 2) || p.y > (worldHeight - (height / 2)))
            y = worldPosition.y;
        worldPosition.set(x, y);
    }

    public static void drawRect(RectF r) {
        DrawingHelper.drawRectangle(new Rect((int) transformX(r.left), (int) transformY(r.top),
                (int) transformX(r.right), (int) transformY(r.bottom)), Color.RED, 255);
    }

    public static void drawBullets(List<Bullet> bullets, int imageId, double
            angleRadians, float scale) {
        PointF screenPosition;
        for(Bullet bullet : bullets) {

            screenPosition = transformCoordinates(bullet.getPosition());
            DrawingHelper.drawImage(imageId, screenPosition.x, screenPosition.y,
                    (float) Math.toDegrees(bullet.getAngleRadians() + GraphicsUtils.HALF_PI), scale,
                    scale, 255);
            RectF r = bullet.getBounds();
            //drawRect(r);
        }
    }

    public static RectF getBulletBounds(PointF p) {
        int halfWidth = bulletDimensions.getImageWidth() / 2;
        int halfHeight = bulletDimensions.getImageHeight() / 2;
        return new RectF(p.x - GameController.SCALE * halfWidth, p.y - GameController.SCALE *
                halfWidth, p.x + GameController.SCALE * halfWidth, p.y  + GameController.SCALE
                * halfWidth);
    }

    public static void drawAsteroids(List<Asteroid> asteroids) {
        for(Asteroid asteroid : asteroids) {
            drawImage(asteroid.getContentManagerId(), asteroid.getWorldCenter(), (float)
                            Math.toDegrees(asteroid.getAngleRadiansA()), asteroid.getRegularScale(),
                    asteroid.getRegularScale(), 255);
            //ViewPort.drawRect(asteroid.getBounds());
        }
    }

    public static PointF getCenter() {
        updateValues();
        return center;
    }

    public static PointF getWorldPosition() {
        return worldPosition;
    }

    public static void setWorldPosition(PointF worldPosition) {
        ViewPort.worldPosition = worldPosition;
    }

    public static PointF getWorldCenter() {
        return worldCenter;
    }

    public static void setWorldCenter(PointF worldCenter) {
        ViewPort.worldCenter = worldCenter;
    }
}
