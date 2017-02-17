package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.superasteroids.model_classes.Level;
import edu.byu.cs.superasteroids.model_classes.LevelObject;

/**
 * Created by athom909 on 2/6/16.
 */
public class LevelObjectDAO {

    private SQLiteDatabase db;

    /**
     * constructor
     */
    public LevelObjectDAO(SQLiteDatabase db) {
        this.db = db;
    }


    /**
     * query function
     * @return an arraylist of LevelObjects objects from database
     */
    public Map<Integer, ArrayList<LevelObject>> query() {

        final String SQL = "select * from levelObjects";
        ArrayList<LevelObject> levelObjects = new ArrayList<>();
        Cursor cursor = db.rawQuery(SQL, new String[]{});
        cursor.moveToFirst();
        Map<Integer, ArrayList<LevelObject>> levelObjectsMap = new HashMap<Integer,
                ArrayList<LevelObject>>();
        try {
            while (!cursor.isAfterLast()) {
                int level = cursor.getInt(3);
                String position = cursor.getString(0);
                int objectId = cursor.getInt(1);
                float scale = cursor.getFloat(2);

                if(levelObjectsMap.get(level) == null) {
                    ArrayList<LevelObject> l = new ArrayList<>();
                    l.add(new LevelObject(level, position, objectId, scale));
                    levelObjectsMap.put(level, l);
                }
                else
                    levelObjectsMap.get(level).add(new LevelObject(level, position, objectId, scale));
                // just for the record cursorLo.getInt(3) is the level number
                //   ...verify that level number always matches
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }


        return levelObjectsMap;
    }

    /**
     * insert a Level object in levels table
     * @param lo a LevelObjects object
     */
    public void insert(LevelObject lo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("position", lo.getPosition());
        contentValues.put("objectId", lo.getObjectId());
        contentValues.put("scale", lo.getScale());
        contentValues.put("level", lo.getLevel());
        db.insert("levelObjects", null, contentValues);
    }


    /**
     * clears the entire table
     */
    public void clearAll() {}
}
