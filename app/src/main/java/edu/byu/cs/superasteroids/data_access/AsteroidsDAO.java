package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.Asteroid;

/**
 * Created by athom909 on 2/6/16.
 */
public class AsteroidsDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public AsteroidsDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * query function
     * @return an arraylist of LevelAsteroids objects
     */
    public ArrayList<Asteroid> query() {

        final String SQL = "select * from asteroids";

        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                asteroids.add(new Asteroid(cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
                        cursor.getString(0), cursor.getString(4)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return asteroids;
    }

    /**
     * inserts an asteroid object into the database
     * @param a an LevelAsteroids object
     */
    public void insert(Asteroid a) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", a.getName());
        contentValues.put("image", a.getImage());
        contentValues.put("imageWidth", a.getImageWidth());
        contentValues.put("imageHeight", a.getImageHeight());
        contentValues.put("type", a.getType());
        db.insert("asteroids", null, contentValues);
    }

}
