package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.BGImage;

/**
 * Created by athom909 on 2/6/16.
 */
public class ObjectsDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public ObjectsDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * query function
     * @return an arraylist of names of objects from table
     */
    public ArrayList<BGImage> query() {

        final String SQL = "select * " + // or name
                "from objects";
        // of "select * from objects";
        ArrayList<BGImage> objects = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                objects.add(new BGImage(cursor.getString(1), cursor.getInt(0)));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return objects;
    }

    /**
     * insert function
     * @param name a string that represents a name of an object to be put into the table
     */
    public void insert(String name) {//this is not working?
        //Log.d("asdf", name);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert("objects", null, contentValues);
    }

    /**
     * clears the entire table
     */
    public void clearAll() {}

}
