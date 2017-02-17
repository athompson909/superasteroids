package edu.byu.cs.superasteroids.model_classes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.data_access.AsteroidsDAO;
import edu.byu.cs.superasteroids.data_access.CannonsDAO;
import edu.byu.cs.superasteroids.data_access.EnginesDAO;
import edu.byu.cs.superasteroids.data_access.ExtraPartsDAO;
import edu.byu.cs.superasteroids.data_access.LevelsDAO;
import edu.byu.cs.superasteroids.data_access.MainBodiesDAO;
import edu.byu.cs.superasteroids.data_access.ObjectsDAO;
import edu.byu.cs.superasteroids.data_access.PowerCoresDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by athom909 on 2/5/16.
 * This object holds all of the data for the Asteroids game configuration
 * THIS CLASS IS A SINGLETON CLASS!
 */
public class AsteroidsGame {

    // the singleton instance
    private static volatile AsteroidsGame instance;

    private List<BGImage> objects;
    private List<Asteroid> asteroids;
    private List<Level> levels;
    private List<MainBody> mainBodies;
    private List<Cannon> cannons;
    private List<ExtraPart> extraParts;
    private List<Engine> engines;
    private List<PowerCore> powerCores;

    private static Ship ship;

    /**
     * this constructor loads all the data from the model classes into the database here in
     * AsteroidsGame
     * use all the DAO's query functions to get the data
     *
     * @param db
     */
    private AsteroidsGame(SQLiteDatabase db) {
        objects = new ObjectsDAO(db).query();
        asteroids = new AsteroidsDAO(db).query();
        levels = new LevelsDAO(db).query();
        mainBodies = new MainBodiesDAO(db).query();
        cannons = new CannonsDAO(db).query();
        extraParts = new ExtraPartsDAO(db).query();
        engines = new EnginesDAO(db).query();
        powerCores = new PowerCoresDAO(db).query();

        ship = null;
    }

    /**
     * this will just be done once in the DataImporter
     * this must be called before getInstance() is ever called
     *
     * @param db the database that was just populated in DataImporter.importData()
     * @return a new instance db
     */
    public static AsteroidsGame initialize(SQLiteDatabase db) {
        //if(instance == null)
        instance = new AsteroidsGame(db);
        return instance;
    }

    /**
     * this should only be called after initialize(db) is called
     *
     * @return instance
     */
    public static AsteroidsGame getInstance() {
        return instance;
    }

    public static void setInstance(AsteroidsGame instance) {
        AsteroidsGame.instance = instance;
    }


    public static Ship getShip() {
        return ship;
    }

    public static void setShip(Ship ship) {
        AsteroidsGame.ship = ship;
    }

    public void setShip() {
        if (mainBodies == null || cannons == null || extraParts == null || engines == null ||
                powerCores == null)
            return;
        ship = new Ship(mainBodies.get(1), cannons.get(1), extraParts.get(1), engines.get(1),
                powerCores.get(1));
    }

    public List<List<Integer>> loadShipParts(ContentManager content) {
        List<List<Integer>> list = new ArrayList<>(5);
        /*
        list[0] = mainBodyIds
        list[1] = cannonIds
        list[2] = extraPartIds
        list[3] = engineIds
        list[4] = powerCoreIDs
         */
        for (int i = 0; i < 5; ++i) {
            list.add(new ArrayList<Integer>());
        }

        int index = 0;
        for (MainBody m : AsteroidsGame.getInstance().getMainBodies()) {
            index = content.loadImage(m.getImage());
            m.setId(index);
            list.get(0).add(index);
        }
        for (Cannon c : AsteroidsGame.getInstance().getCannons()) {
            index = content.loadImage(c.getImage());
            c.setId(index);
            list.get(1).add(index);
        }
        for (ExtraPart ep : AsteroidsGame.getInstance().getExtraParts()) {
            index = content.loadImage(ep.getImage());
            ep.setId(index);
            list.get(2).add(index);
        }
        for (Engine e : AsteroidsGame.getInstance().getEngines()) {
            index = content.loadImage(e.getImage());
            e.setId(index);
            list.get(3).add(index);
        }
        for (PowerCore p : AsteroidsGame.getInstance().getPowerCores()) {
            index = content.loadImage(p.getImage());
            p.setId(index);
            list.get(4).add(index);
        }
        return list;
    }

//    public void drawShip(double angleRadians, float scale) {
//        // mainBody's center right now is gameViewCenter
//
//        float angleDegrees = (float) Math.toDegrees(angleRadians);
//
//        if (ship.getMainBody() != null) {
//            // main body:
//            DrawingHelper.drawImage(ship.getMainBody().getId(), ship.getCenter().x,
//                    ship.getCenter().y, angleDegrees, scale, scale, 255);
//        }
//
//        if (ship.getCannon() != null) {
//            PointF offset = ship.getCannonOffsets(scale, angleRadians);
//            DrawingHelper.drawImage(ship.getCannon().getId(),
//                    ship.getCenter().x + offset.x, ship.getCenter().y + offset.y, angleDegrees,
//                    scale, scale, 255);
//        }
//
//        if (ship.getExtraPart() != null) {
//            PointF offset = ship.getExtraPartOffsets(scale, angleRadians);
//            DrawingHelper.drawImage(ship.getExtraPart().getId(),
//                    ship.getCenter().x + offset.x, ship.getCenter().y + offset.y, angleDegrees,
//                    scale, scale, 255);
//        }
//
//        if (ship.getEngine() != null) {
//            PointF offset = ship.getEngineOffsets(scale, angleRadians);
//            DrawingHelper.drawImage(ship.getEngine().getId(),
//                    ship.getCenter().x + offset.x, ship.getCenter().y + offset.y, angleDegrees,
//                    scale, scale, 255);
//        }
//    }


    public List<BGImage> getObjects() {
        return objects;
    }

    public void setObjects(List<BGImage> objects) {
        this.objects = objects;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public List<MainBody> getMainBodies() {
        return mainBodies;
    }

    public void setMainBodies(List<MainBody> mainBodies) {
        this.mainBodies = mainBodies;
    }

    public List<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(List<Cannon> cannons) {
        this.cannons = cannons;
    }

    public List<ExtraPart> getExtraParts() {
        return extraParts;
    }

    public void setExtraParts(List<ExtraPart> extraParts) {
        this.extraParts = extraParts;
    }

    public List<Engine> getEngines() {
        return engines;
    }

    public void setEngines(List<Engine> engines) {
        this.engines = engines;
    }

    public List<PowerCore> getPowerCores() {
        return powerCores;
    }

    public void setPowerCores(List<PowerCore> powerCores) {
        this.powerCores = powerCores;
    }
}