package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.superasteroids.model_classes.LevelAsteroid;

/**
 * Created by athom909 on 2/6/16.
 */
public class LevelAsteroidDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public LevelAsteroidDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * query function
     * @return an arraylist of Level objects from database
     */
    public Map<Integer, ArrayList<LevelAsteroid>> query() {
        final String SQL = "select * from levelAsteroids";
        ArrayList<LevelAsteroid> levelAsteroids = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});

        Map<Integer, ArrayList<LevelAsteroid>> levelAsteroidsMap =
                new HashMap<Integer, ArrayList<LevelAsteroid>>();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int level = cursor.getInt(2);

                if(levelAsteroidsMap.get(level) == null) {
                    ArrayList<LevelAsteroid> l = new ArrayList<>();
                    l.add(new LevelAsteroid(level, cursor.getInt(1), cursor.getInt(0)));
                    levelAsteroidsMap.put(level, l);
                }
                else
                    levelAsteroidsMap.get(level).add(new LevelAsteroid(level, cursor.getInt(1),
                            cursor.getInt(0)));

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return levelAsteroidsMap;
    }

    /**
     * insert a Level object in levels table
     * @param la a Level object
     */
    public void insert(LevelAsteroid la) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", la.getNumber());
        contentValues.put("asteroidId", la.getAsteroidId());
        contentValues.put("level", la.getLevel());
        db.insert("levelAsteroids", null, contentValues);
    }


    /**
     * clears the entire table
     */
    public void clearAll() {}
}
