package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.byu.cs.superasteroids.model_classes.Level;
import edu.byu.cs.superasteroids.model_classes.LevelAsteroid;
import edu.byu.cs.superasteroids.model_classes.LevelObject;

/**
 * Created by athom909 on 2/6/16.
 */
public class LevelsDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public LevelsDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public ArrayList<Level> query() {

        Map<Integer, ArrayList<LevelObject>> levelObjectsMap = new LevelObjectDAO(db).query();
        Map<Integer, ArrayList<LevelAsteroid>> levelAsteroidsMap = new LevelAsteroidDAO(db).query();

        final String SQL = "select * from levels";
        ArrayList<Level> levels = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int level = cursor.getInt(0);

                levels.add(new Level(level, cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getString(5),
                        levelObjectsMap.get(level), levelAsteroidsMap.get(level)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return levels;
    }

    public void insert(Level l) {
        ContentValues contentValues = new ContentValues();
        int number = l.getNumber();
        contentValues.put("number",  number);
        contentValues.put("title", l.getTitle());
        contentValues.put("hint", l.getHint());
        contentValues.put("width", l.getWidth());
        contentValues.put("height", l.getHeight());
        contentValues.put("music", l.getMusic());
//        LevelObjectDAO levelObjectsDAO = new LevelObjectDAO(db);
//        ArrayList<LevelObject> levelObjects = l.getLevelObjects();
//        for(int i = 0; i < levelObjects.size(); ++i)
//            levelObjectsDAO.insert(levelObjects.get(i));
//        LevelAsteroidDAO levelAsteroidDAO = new LevelAsteroidDAO(db);
//        ArrayList<LevelAsteroid> levelAsteroids = l.getLevelAsteroids();
//        for (int i = 0; i < levelAsteroids.size(); ++i)
//            levelAsteroidDAO.insert(levelAsteroids.get(i));
        db.insert("levels", null, contentValues);
    }

    /**
     * clears the entire table
     */
    public void clearAll() {}
}
