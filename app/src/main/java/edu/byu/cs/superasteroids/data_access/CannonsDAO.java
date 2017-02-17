package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.Cannon;
import edu.byu.cs.superasteroids.model_classes.Projectile;

/**
 * Created by athom909 on 2/6/16.
 */
public class CannonsDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public CannonsDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     *
     * @return an arraylist of all the cannons in the cannon table
     */
    public ArrayList<Cannon> query() {

        final String SQL = "select * from cannons";
        ArrayList<Cannon> cannons = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                cannons.add(new Cannon(cursor.getString(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getString(0), cursor.getString(1), cursor.getString(5),
                        cursor.getInt(6), cursor.getInt(7), cursor.getString(8), cursor.getInt(9)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return cannons;
    }

    /**
     * inserts a cannon object into the database
     * @param c a Cannon object
     */
    public void insert(Cannon c) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("attachPoint", c.getAttachPoint());
        contentValues.put("emitPoint", c.getEmitPoint());
        contentValues.put("image", c.getImage());
        contentValues.put("imageWidth", c.getImageWidth());
        contentValues.put("imageHeight", c.getImageHeight());
        contentValues.put("attackImage", c.getBullet().getImage());
        contentValues.put("attackImageWidth", c.getBullet().getImageWidth());
        contentValues.put("attackImageHeight", c.getBullet().getImageHeight());
        contentValues.put("attackSound", c.getBullet().getAttackSound());
        contentValues.put("damage", c.getBullet().getDamage());
        db.insert("cannons", null, contentValues);
    }

    /**
     * clears the entire table
     */
    public void clearAll() {}



}
