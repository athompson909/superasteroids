package edu.byu.cs.superasteroids.game;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.model_classes.Asteroid;
import edu.byu.cs.superasteroids.model_classes.AsteroidsGame;
import edu.byu.cs.superasteroids.model_classes.BGImage;
import edu.byu.cs.superasteroids.model_classes.Cannon;
import edu.byu.cs.superasteroids.model_classes.Level;
import edu.byu.cs.superasteroids.model_classes.LevelObject;
import edu.byu.cs.superasteroids.model_classes.Projectile;
import edu.byu.cs.superasteroids.model_classes.Ship;
import edu.byu.cs.superasteroids.model_classes.VisibleObject;

/**
 * Created by athom909 on 3/2/16.
 * <p/>
 * so we're dealing with two different positions here:
 * OBJECT SCREEN POSITION and OBJECT WORLD POSITION
 * <p/>
 * the dimensions of the android view hosting the game can be accessed through DrawingHelper
 */
public class GameController implements IGameDelegate {

    private Ship ship;
    private List<Bullet> bullets;
    private int bulletId;
    private static VisibleObject bulletDimensions;

    private List<Asteroid> asteroids;

    // a perfectly square world
    private int worldWidth;
    private int worldHeight;
    private int levelNumber;
    private Level currentLevel;

    private double time;
    private boolean countTime;

    //private final String SPACE = "images/parts/space.bmp";
    private int spaceId;
    //private PointF spacePosition;

    public static final float SCALE = 0.15f;
    private final float SPACE_SCALE = 1.46484375f; // 3000/2048 for the size of level 1 world
    private final float BG_OBJECT_SCALE = 1f;

    // ship drawing values
    private double angleRadians;
    //private Point center; // this value is the center of wherever the ship should be
    // actually I'm storing that in Ship
    private boolean startPhase;

    @Override
    public void update(double elapsedTime) {
        PointF p = InputManager.movePoint;
        boolean b = InputManager.firePressed;

        moveShip(p, elapsedTime);

        shoot(b);

        moveAsteroids(elapsedTime);

        moveBullets(elapsedTime);

        checkForShipCollision(elapsedTime);

        //checkForAsteroidCollisions(elapsedTime);
    }

    // *** FOR THE SHIP ***
    private void moveShip(PointF p, double elapsedTime) {
        if (p != null) {
            setAngleRadians(p);
            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(ship.getWorldCenter(),
                    ship.getShipBounds(), ship.getEngine().getBaseSpeed(), angleRadians, elapsedTime);

            ship.setWorldCenter(result.getNewObjPosition());
            ship.setShipBounds(result.getNewObjBounds());

            //angleRadians += GraphicsUtils.HALF_PI; // this helps drawImage(...) to work
        }
    }

    private void checkForShipCollision(double elapsedTime) {
        if(countTime) {
            time += elapsedTime;
            ViewPort.setCircleOn();
            if(time >= 5.0) {
                time = 0;
                countTime = false;
                ViewPort.setCircleOff();
            }
        }

        for(Asteroid asteroid : asteroids) {
            RectF bounds = new RectF(asteroid.getBounds());
            if(asteroid.getBounds().intersect(ship.getShipBounds())) {
                countTime = true;
                asteroid.setBounds(bounds);
                ship.decrementLives();
                // drawText("You have x lives left");
                // if ship.getLives() == 0
                //      drawText("Game Over"); setGameToBeOver(); (return to main menu)
            }
        }


    }
    // *************************

    // *** FOR THE ASTEROIDS ***
    private void moveAsteroids(double elapsedTime) {

        for (Asteroid asteroid : asteroids) {
            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(asteroid.getWorldCenter(),
                    asteroid.getBounds(), asteroid.getSpeedA(), asteroid.getAngleRadiansA(),
                    elapsedTime);
            asteroid.setWorldCenter(result.getNewObjPosition());
            asteroid.setBounds(result.getNewObjBounds());

            GraphicsUtils.RicochetObjectResult ricochetResult =
                    GraphicsUtils.ricochetObject(asteroid.getWorldCenter(), asteroid.getBounds(),
                            asteroid.getAngleRadiansA(), worldWidth, worldHeight);
            asteroid.setWorldCenter(ricochetResult.getNewObjPosition());
            asteroid.setBounds(ricochetResult.getNewObjBounds());
            asteroid.setAngleRadiansA(ricochetResult.getNewAngleRadians());
        }

    }


    private void checkForAsteroidCollisions(double elapsedTime) {
        for(Asteroid mainAsteroid : asteroids) {
            for( Asteroid eachAsteroid : asteroids) {
                if(mainAsteroid == eachAsteroid)
                    continue;
                RectF mainBounds = new RectF(mainAsteroid.getBounds());
                RectF eachbounds = new RectF(eachAsteroid.getBounds());
                if(eachAsteroid.getBounds().intersect(mainBounds)) {
                    countTime = true;
                    mainAsteroid.setBounds(mainBounds);
                    eachAsteroid.setBounds(eachbounds);
                }

                mainAsteroid.setAngleRadiansA(mainAsteroid.getAngleRadiansA() + (Math.PI / 2));
                eachAsteroid.setAngleRadiansA(eachAsteroid.getAngleRadiansA() - (Math.PI / 2));

            }
        }
    }
    // *************************


    // *** FOR THE BULLET ***
    private void shoot(boolean b) {
        if (b) {
            //PointF bullet = ship.getWorldCenter();
            PointF bullet = ViewPort.transformToWorld(ship.getWorldCenter(), ship
                    .getEmitDiff());
            bullets.add(new Bullet(bullet, angleRadians, ViewPort.getBulletBounds(bullet)));
            ContentManager.getInstance().playSound(ship.getCannon().getSoundContentManagerId(),
                    1, 1);
        }
    }

    private void moveBullets(double elapsedTime) {

        TreeSet<Integer> indexesToRemove = new TreeSet<>();
        for (int i = 0; i < bullets.size(); ++i) {

            Bullet bullet = bullets.get(i);
            if (isOutOfBounds(bullet.getPosition())) {
                indexesToRemove.add(i);
                continue;
            }
            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(bullet.getPosition(),
                    bullet.getBounds(), ship.getEngine().getBaseSpeed() * 2,
                    bullet.getAngleRadians(), elapsedTime);
            bullet.setPosition(result.getNewObjPosition());
            bullet.setBounds(result.getNewObjBounds());

            checkForCollisions(bullet, i, indexesToRemove);
        }

        Iterator iterator;
        iterator = indexesToRemove.descendingIterator();
        while (iterator.hasNext()) {
            bullets.remove((int) iterator.next());
        }
    }

    private void checkForCollisions(Bullet b, int index, Set<Integer> indexesToRemove) {
        TreeSet<Integer> asteroidsToRemove = new TreeSet<>();
        RectF rB = b.getBounds();

        for (int i = 0; i < asteroids.size(); ++i) {
            Asteroid a = asteroids.get(i);
            RectF rA = a.getBounds();

            // checking for collisions between and asteroid and a bullet
            if (rB.intersect(rA)) {
                destroy(a, i, asteroidsToRemove);
                indexesToRemove.add(index);
            }
        }

        Iterator iterator;
        iterator = asteroidsToRemove.descendingIterator();
        while (iterator.hasNext()) {
            asteroids.remove((int) iterator.next());
        }
    }

    /**
     * This first decreases health counts of the asteroid and when that  is 0 then it splits it
     */
    private void destroy(Asteroid a, int index, Set<Integer> asteroidsToRemove) {
        a.decrementHealth();
        if (a.getHealth() == 0) {

            if (a.getSplitCount() < 1) {
                // this might push something out of bounds...
                Asteroid newA = new Asteroid(a, a.getAngleRadiansA() + GraphicsUtils.FOURTH_PI,
                        new PointF(a.getWorldCenter().x + 100, a.getWorldCenter().y + 100));
                Asteroid newB = new Asteroid(a, a.getAngleRadiansA() + GraphicsUtils.FOURTH_PI,
                        new PointF(a.getWorldCenter().x - 100, a.getWorldCenter().y - 100));
                asteroids.add(newA);
                asteroids.add(newB);

                // split them (this should only happen once for now)

            }
            asteroidsToRemove.add(index);
        }
    }

    private boolean isOutOfBounds(PointF p) {
        return (p.x < 0 || p.x > worldWidth || p.y < 0 || p.y > worldHeight);
    }
    // **********************

    /**
     * this little chunk is done in viewport coordinates, hopefully it's the only place that is
     * like this
     *
     * @param p
     */
    private void setAngleRadians(PointF p) {
        float x = p.x - ship.getCenter().x, y = p.y - ship.getCenter().y;
        if (y >= 0)
            angleRadians = Math.atan2(y, x);
        else
            angleRadians = (2 * Math.PI + Math.atan2(y, x));
    }

    @Override
    public void loadContent(ContentManager content) {

        time = 0;
        countTime = false;

        ship = AsteroidsGame.getShip();
        bullets = new ArrayList<>();

        spaceId = content.loadImage("images/space.bmp");

        levelNumber = 1;
        currentLevel = AsteroidsGame.getInstance().getLevels().get(levelNumber - 1);
        worldWidth = currentLevel.getWidth();
        worldHeight = currentLevel.getHeight();

        loadDataToContentManager(content);

        Projectile bullet = ship.getCannon().getBullet();
        bulletId = bullet.getContentManagerId();
        bulletDimensions = new VisibleObject(bullet.getImage(), bullet.getImageWidth(),
                bullet.getImageHeight());

        angleRadians = 0;

        ViewPort.setWorldDimensions(worldWidth, worldHeight);
        ship.setWorldCenter(ViewPort.getWorldCenter());
        ship.calculateShipBounds();
        startPhase = true;

        populateAsteroidsArrayList();
    }

    private void loadDataToContentManager(ContentManager content) {

        // loading all the background images
        for (BGImage bg : AsteroidsGame.getInstance().getObjects()) {
            bg.setContentManagerId(content.loadImage(bg.getName()));
        }
        // loading all the different possible bullet images
        for (Cannon c : AsteroidsGame.getInstance().getCannons()) {
            c.getBullet().setContentManagerId(content.loadImage(c.getBullet().getImage()));
            try {
                c.setSoundContentManagerId(content.loadSound(c.getBullet().getAttackSound()));
            }
            catch (Exception e) {}
        }

        // loading the asteroids
        for (Asteroid a : AsteroidsGame.getInstance().getAsteroids()) {
            a.setContentManagerId(content.loadImage(a.getImage()));
        }

    }

    private void populateAsteroidsArrayList() {
        Random randomNumberGenerator = new Random();
        asteroids = new ArrayList<>();
        // levelAsteroids maybe should not be an array
//        for (int i = 0; i < currentLevel.getLevelAsteroids().get(0).getNumber(); ++i) {
        for (int i = 0; i < 7; ++i) { // for gerrit and gavin
            Asteroid a = AsteroidsGame.getInstance().getAsteroids().get(levelNumber - 1);
            Asteroid ast = new Asteroid(a.getImage(), a.getImageWidth(), a.getImageHeight(),
                    a.getName(), a.getType(), a.getContentManagerId());
            generateRandomPositions(ast, randomNumberGenerator);
            asteroids.add(ast);
        }
    }

    // for some reason they all have the same angle radians, maybe fix that at some point
    private void generateRandomPositions(Asteroid asteroid, Random randomNumberGenerator) {
        int x = 0, y = 0;
        double asteroidAngle;

        boolean notValid = true;
        while (notValid) {
            x = randomNumberGenerator.nextInt(worldWidth);
            if (x > (ViewPort.getCenter().x + ViewPort.getWorldPosition().x) || x < (ViewPort
                    .getCenter().x - ViewPort.getWorldPosition().x))
                notValid = false;
        }

        notValid = true;
        while (notValid) {
            y = randomNumberGenerator.nextInt(worldHeight);
            if (y > (ViewPort.getCenter().y + ViewPort.getWorldPosition().y) || y < (ViewPort
                    .getCenter().y - ViewPort.getWorldPosition().y))
                notValid = false;
        }

        asteroidAngle = randomNumberGenerator.nextDouble() % (2 * Math.PI);
        //asteroidAngle /= (2 * Math.PI); // see what this does

        asteroid.setWorldCenterAndBounds(new PointF(x, y));
        asteroid.setAngleRadiansA(asteroidAngle);
    }


    @Override
    public void unloadContent(ContentManager content) {

    }

    private PointF cannonLocation;
    private PointF emitDiff;

    @Override
    public void draw() {

        ViewPort.drawImage(spaceId, ViewPort.getWorldCenter(), 0, SPACE_SCALE, SPACE_SCALE, 255);

        drawBGImages();

        ViewPort.drawShip(ship, ship.getWorldCenter(), angleRadians, SCALE);

        ViewPort.drawBullets(bullets, bulletId, angleRadians, SCALE);

        ViewPort.drawAsteroids(asteroids);

        if (startPhase) {
            ViewPort.updateValues();
            ViewPort.updateWorldDimensions();
            ship.setWorldCenter(ViewPort.getWorldCenter());
            startPhase = false;
        }
    }

    private void drawBGImages() {

        for (LevelObject lo : currentLevel.getLevelObjects()) {            //error:
            BGImage bgImageToDraw = AsteroidsGame.getInstance().getObjects().get(lo.getObjectId()
                    - 1);
            ViewPort.drawImage(bgImageToDraw.getContentManagerId(), lo.getCoordinates(), 0,
                    BG_OBJECT_SCALE, BG_OBJECT_SCALE, 255);
        }
    }

    public static VisibleObject getBulletDimensions() {
        return bulletDimensions;
    }

    private RectF getBulletBounds(PointF p) {
        return ViewPort.getBulletBounds(p);
    }


}