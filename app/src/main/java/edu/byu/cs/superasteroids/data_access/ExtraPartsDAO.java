package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.ExtraPart;

/**
 * Created by athom909 on 2/6/16.
 */
public class ExtraPartsDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public ExtraPartsDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * ExtraParts query function
     * @return an arraylist of all ExtraPart objects from database
     */
    public ArrayList<ExtraPart> query() {

        final String SQL = "select * from extraParts";
        ArrayList<ExtraPart> extraParts = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                extraParts.add(new ExtraPart(cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3), cursor.getString(0)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return extraParts;
    }

    /**
     * inserts an ExtraPart object into extraparts table
     * @param ep an ExtraPart object
     */
    public void insert(ExtraPart ep) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("attachPoint", ep.getAttachPoint());
        contentValues.put("image", ep.getImage());
        contentValues.put("imageWidth", ep.getImageWidth());
        contentValues.put("imageHeight", ep.getImageHeight());
        db.insert("extraParts", null, contentValues);
    }


    /**
     * clears the entire table
     */
    public void clearAll() {}
}
