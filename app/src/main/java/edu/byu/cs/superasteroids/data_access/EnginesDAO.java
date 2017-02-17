package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.Engine;

/**
 * Created by athom909 on 2/6/16.
 */
public class EnginesDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public EnginesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Engine query function:
     * @return an arraylist of all engine objects from database
     */
    public ArrayList<Engine> query() {

        final String SQL = "select * from engines";
        ArrayList<Engine> engines = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                engines.add(new Engine(cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getString(2), cursor.getInt(0), cursor.getInt(1)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return engines;
    }

    /**
     * Inserts an Engine object into engine table in database
     * @param en an Engine object to be inserted
     */
    public void insert(Engine en) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("baseSpeed", en.getBaseSpeed());
        contentValues.put("baseTurnRate", en.getBaseTurnRate());
        contentValues.put("attachPoint", en.getAttachPoint());
        contentValues.put("image", en.getImage());
        contentValues.put("imageWidth", en.getImageWidth());
        contentValues.put("imageHeight", en.getImageHeight());
        db.insert("engines", null, contentValues);
    }

    /**
     * clears the entire table
     */
    public void clearAll() {}

}
