package edu.byu.cs.superasteroids.data_access;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

import edu.byu.cs.superasteroids.importer.IGameDataImporter;
import edu.byu.cs.superasteroids.model_classes.Asteroid;
import edu.byu.cs.superasteroids.model_classes.AsteroidsGame;
import edu.byu.cs.superasteroids.model_classes.Cannon;
import edu.byu.cs.superasteroids.model_classes.Engine;
import edu.byu.cs.superasteroids.model_classes.ExtraPart;
import edu.byu.cs.superasteroids.model_classes.Level;
import edu.byu.cs.superasteroids.model_classes.LevelAsteroid;
import edu.byu.cs.superasteroids.model_classes.LevelObject;
import edu.byu.cs.superasteroids.model_classes.MainBody;
import edu.byu.cs.superasteroids.model_classes.PowerCore;

/**
 * Created by athom909 on 2/13/16.
 */
public class DataImporter implements IGameDataImporter {

    private SQLiteDatabase db;

    private JSONObject asteroidsGameObj;

    public DataImporter(Context context) {
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        asteroidsGameObj = null;
    }

    /**
     *
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return true if data was imported successfully else returns false
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {

        JSONObject jsonTree = null;
        try {
            jsonTree = new JSONObject(makeString(dataInputReader));
            asteroidsGameObj = jsonTree.getJSONObject("asteroidsGame");

            db.beginTransaction();
            // inserting all the files from the DOM into the database
            insertValues();

            AsteroidsGame.initialize(db);

            db.setTransactionSuccessful();
            db.endTransaction();

        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    private String makeString(InputStreamReader reader) {

        StringBuilder sb;
        sb = new StringBuilder();

        Scanner scanner = new Scanner(reader);
        scanner.useDelimiter("\n"); // so it ignores endlines
        while(scanner.hasNext())
            sb.append(removeSpacesFromString(scanner.next()) + " ");

        return sb.toString();
    }

    private String removeSpacesFromString(String str) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); ++i) {
            if(!Character.isSpaceChar(str.charAt(i)))
                sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    private void insertValues() throws Exception {
        insertInObjects();
        insertInAsteroids();
        insertInLevels();
        insertInMainBodies();
        insertInCannons();// doesn't put anything into database
        insertInExtraParts();
        insertInEngines();// doesn't put anything into database
        insertInPowerCores();
    }

    private void insertInObjects() throws Exception {
        JSONArray objectsArr = asteroidsGameObj.getJSONArray("objects");
        ObjectsDAO objectsDAO = new ObjectsDAO(db);
        for(int i = 0; i < objectsArr.length(); ++i) {
            objectsDAO.insert(objectsArr.getString(i));
        }
    }

    private void insertInAsteroids() throws Exception {
        JSONArray asteroidsArr = asteroidsGameObj.getJSONArray("asteroids");
        AsteroidsDAO asteroidsDAO = new AsteroidsDAO(db);
        for(int i = 0; i < asteroidsArr.length(); ++i) {
            JSONObject elemObject = asteroidsArr.getJSONObject(i);
            Asteroid asteroid = new Asteroid(elemObject.getString("image"),
                    elemObject.getInt("imageHeight"), elemObject.getInt("imageWidth"),
                    elemObject.getString("name"), elemObject.getString("type"));
            asteroidsDAO.insert(asteroid);
        }
    }

    /**
     * this also inserts in levelObjects and levelAsteroids
     * there is a level id in levelObjects and levelAsteroids that ties them to the levels table
     * @throws Exception if there are problems with the JSON or database
     */
    private void insertInLevels() throws Exception {
        JSONArray levelsArr = asteroidsGameObj.getJSONArray("levels");
        LevelsDAO levelsDAO = new LevelsDAO(db);
        for(int i = 0; i < levelsArr.length(); ++i) {
            JSONObject elemObject = levelsArr.getJSONObject(i);
            int level = elemObject.getInt("number");
            ArrayList<LevelObject> levelObjects = new ArrayList<>();
            ArrayList<LevelAsteroid> levelAsteroids = new ArrayList<>();

            // getting all the levelObjects...
            LevelObjectDAO levelObjectDAO = new LevelObjectDAO(db);
            JSONArray levelObjectsArr = elemObject.getJSONArray("levelObjects");
            for (int j = 0; j < levelObjectsArr.length(); ++j) {
                JSONObject levObjElemObj = levelObjectsArr.getJSONObject(j);
                levelObjectDAO.insert(new LevelObject(level, levObjElemObj.getString("position"),
                        levObjElemObj.getInt("objectId"),
                        (float) levObjElemObj.getDouble("scale")));
            }

            // getting all the levelAsteroids...
            LevelAsteroidDAO levelAsteroidDAO = new LevelAsteroidDAO(db);
            JSONArray levelAsteroidsArr = elemObject.getJSONArray("levelAsteroids");
            for (int j = 0; j < levelAsteroidsArr.length(); ++j) {
                JSONObject levAstElemObj = levelAsteroidsArr.getJSONObject(j);
                levelAsteroidDAO.insert(new LevelAsteroid(level, levAstElemObj.getInt("number"),
                        levAstElemObj.getInt("asteroidId")));
            }

            // sticking it all into one Level object
            Level level_ = new Level(level, elemObject.getString("title"),
                    elemObject.getString("hint"), elemObject.getInt("width"),
                    elemObject.getInt("height"), elemObject.getString("music"), null, null);
            levelsDAO.insert(level_);
        }
    }

    private void insertInMainBodies() throws Exception {
        JSONArray mainBodiesArr = asteroidsGameObj.getJSONArray("mainBodies");
        MainBodiesDAO mainBodiesDAO = new MainBodiesDAO(db);
        for(int i = 0; i < mainBodiesArr.length(); ++i) {
            JSONObject elemObject = mainBodiesArr.getJSONObject(i);
            MainBody m = new MainBody(elemObject.getString("image"),
                    elemObject.getInt("imageWidth"), elemObject.getInt("imageHeight"),
                    elemObject.getString("cannonAttach"), elemObject.getString("engineAttach"),
                    elemObject.getString("extraAttach"));
            mainBodiesDAO.insert(m);
        }
    }

    private void insertInCannons() throws Exception {
        JSONArray cannonsArr = asteroidsGameObj.getJSONArray("cannons");
        CannonsDAO cannonsDAO = new CannonsDAO(db);
        for (int i = 0; i < cannonsArr.length(); ++i) {
            JSONObject elemObject = cannonsArr.getJSONObject(i);
            Cannon c = new Cannon(elemObject.getString("image"), elemObject.getInt("imageWidth"),
                    elemObject.getInt("imageHeight"), elemObject.getString("attachPoint"),
                    elemObject.getString("emitPoint"), elemObject.getString("attackImage"),
                    elemObject.getInt("attackImageWidth"), elemObject.getInt("attackImageHeight"),
                    elemObject.getString("attackSound"), elemObject.getInt("damage"));
            cannonsDAO.insert(c);
        }
    }

    private void insertInExtraParts() throws Exception {
        JSONArray extraPartsArr = asteroidsGameObj.getJSONArray("extraParts");
        ExtraPartsDAO extraPartsDAO = new ExtraPartsDAO(db);
        for (int i = 0; i < extraPartsArr.length(); ++i) {
            JSONObject elemObject = extraPartsArr.getJSONObject(i);
            ExtraPart ep = new ExtraPart(elemObject.getString("image"),
                    elemObject.getInt("imageWidth"), elemObject.getInt("imageHeight"),
                    elemObject.getString("attachPoint"));
            extraPartsDAO.insert(ep);
        }
    }

    private void insertInEngines() throws Exception {
        JSONArray enginesArr = asteroidsGameObj.getJSONArray("engines");
        EnginesDAO enginesDAO = new EnginesDAO(db);
        for (int i = 0; i < enginesArr.length(); ++i) {
            JSONObject elemObject = enginesArr.getJSONObject(i);
            Engine en = new Engine(elemObject.getString("image"), elemObject.getInt("imageWidth"),
                    elemObject.getInt("imageHeight"), elemObject.getString("attachPoint"),
                    elemObject.getInt("baseSpeed"), elemObject.getInt("baseTurnRate"));
            enginesDAO.insert(en);
        }
    }

    private void insertInPowerCores() throws Exception {
        JSONArray powerCoresArr = asteroidsGameObj.getJSONArray("powerCores");
        PowerCoresDAO powerCoresDAO = new PowerCoresDAO(db);
        for (int i = 0; i < powerCoresArr.length(); ++i) {
            JSONObject elemObject = powerCoresArr.getJSONObject(i);
            PowerCore pc = new PowerCore(elemObject.getString("image"),
                    elemObject.getInt("cannonBoost"), elemObject.getInt("engineBoost"));
            powerCoresDAO.insert(pc);
        }
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
