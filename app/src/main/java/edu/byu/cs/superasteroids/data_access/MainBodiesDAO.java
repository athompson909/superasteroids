package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.MainBody;

/**
 * Created by athom909 on 2/6/16.
 */
public class MainBodiesDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public MainBodiesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * query function
     * @return an arraylist of MainBody objects
     */
    public ArrayList<MainBody> query() {

        final String SQL = "select * from mainBodies";
        ArrayList<MainBody> mainBodies = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                mainBodies.add(new MainBody(cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return mainBodies;
    }

    /**
     * inserts a MainBody object into table in database
     * @param m a MainBody object
     */
    public void insert(MainBody m) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cannonAttach", m.getCannonAttach());
        contentValues.put("engineAttach", m.getEngineAttach());
        contentValues.put("extraAttach", m.getExtraAttach());
        contentValues.put("image", m.getImage());
        contentValues.put("imageWidth", m.getImageWidth());
        contentValues.put("imageHeight", m.getImageHeight());
        db.insert("mainBodies", null, contentValues);
    }

    /**
     * clears the entire table
     */
    public void clearAll() {}
}
