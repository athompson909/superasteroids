package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.PowerCore;

/**
 * Created by athom909 on 2/6/16.
 */
public class PowerCoresDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public PowerCoresDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * query function
     * @return an arraylist of all PowerCore objects from table
     */
    public ArrayList<PowerCore> query() {
        final String SQL = "select * from powerCores";
        ArrayList<PowerCore> powerCores = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                powerCores.add(new PowerCore(cursor.getString(2), cursor.getInt(0),
                        cursor.getInt(1)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return powerCores;
    }

    /**
     * insert function
     * @param pc a PowerCore object
     */
    public void insert(PowerCore pc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cannonBoost", pc.getCannonBoost());
        contentValues.put("engineBoost", pc.getEngineBoost());
        contentValues.put("image", pc.getImage());
        db.insert("powerCores", null, contentValues);
    }

    /**
     * clears the entire table
     */
    public void clearAll() {}
}
