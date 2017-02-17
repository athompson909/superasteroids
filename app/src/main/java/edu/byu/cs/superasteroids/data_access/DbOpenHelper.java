package edu.byu.cs.superasteroids.data_access;

/**
 * Created by athom909 on 2/13/16.
 */
import android.content.Context;
import android.database.sqlite.*;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "asteroids.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * this is only called once after being installed
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    // what is this even for????
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
//        createAllTables(db);
    }

    /**
     * for clearing out the database in onOpen()
     * @param db
     */
    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("drop table if exists objects");
        db.execSQL("drop table if exists asteroids");
        db.execSQL("drop table if exists levels");
        db.execSQL("drop table if exists levelObjects");
        db.execSQL("drop table if exists levelAsteroids");
        db.execSQL("drop table if exists mainBodies");
        db.execSQL("drop table if exists cannons");
        db.execSQL("drop table if exists extraParts");
        db.execSQL("drop table if exists engines");
        db.execSQL("drop table if exists powerCores");
    }

    /**
     * for clearing out the database in onOpen() and onCreate()
     * @param db
     */
    private void createAllTables(SQLiteDatabase db) {
        dropAllTables(db);
        final String SQLObjects =
                "create table objects " +
                        "(" +
                        "   id integer not null primary key autoincrement," +
                        "   name varchar(255) not null" +
                        ")";
        db.execSQL(SQLObjects);

        final String SQLAsteroids =
                "create table asteroids " +
                        "(" +
                        "   name varchar(255) not null," +
                        "   image varchar(255) not null," +
                        "   imageWidth int not null," +
                        "   imageHeight int not null," +
                        "   type varchar(255) not null" +
                        ")";
        db.execSQL(SQLAsteroids);

        final String SQLLevels =
                "create table levels " +
                        "(" +
                        "   number int not null," +
                        "   title varchar(255) not null," +
                        "   hint varchar(255) not null," +
                        "   width int not null," +
                        "   height int not null," +
                        "   music varchar(255) not null" +
                        //"   id int not null" +
                        ")";
        db.execSQL(SQLLevels);

        final String SQLLevelObjects =
                "create table levelObjects " +
                        "(" +
                        "   position varchar(255) not null," +
                        "   objectId int not null," +
                        "   scale real not null," +
                        "   level int not null" + // the level is kind of the primary key
                        ")";
        db.execSQL(SQLLevelObjects);

        final String SQLLevelAsteroids =
                "create table levelAsteroids " +
                        "(" +
                        "   number int not null," +
                        "   asteroidId not null," +
                        "   level int not null" +
                        ")";
        db.execSQL(SQLLevelAsteroids);

        final String SQLMainBodies =
                "create table mainBodies " +
                        "(" +
                        "   cannonAttach varchar(255) not null," +
                        "   engineAttach varchar(255) not null," +
                        "   extraAttach varchar(255) not null," +
                        "   image varchar(255) not null," +
                        "   imageWidth int not null," +
                        "   imageHeight int not null" +
                        ")";
        db.execSQL(SQLMainBodies);

        final String SQLCannons =
                "create table cannons " +
                        "(" +
                        "   attachPoint varchar(255) not null," +
                        "   emitPoint varchar(255) not null," +
                        "   image varchar(255) not null," +
                        "   imageWidth int not null," +
                        "   imageHeight int not null," +
                        "   attackImage varchar(255) not null," +
                        "   attackImageWidth int not null," +
                        "   attackImageHeight int not null," +
                        "   attackSound varchar(255) not null," +
                        "   damage int not null" +
                        ")";
        db.execSQL(SQLCannons);

        final String SQLExtraParts =
                "create table extraParts " +
                        "(" +
                        "   attachPoint varchar(255) not null," +
                        "   image varchar(255) not null," +
                        "   imageWidth int not null," +
                        "   imageHeight int not null" +
                        ")";
        db.execSQL(SQLExtraParts);

        final String SQLEngines =
                "create table engines " +
                        "(" +
                        "   baseSpeed int not null," +
                        "   baseTurnRate int not null," +
                        "   attachPoint varchar(255) not null," +
                        "   image varchar(255) not null," +
                        "   imageWidth int not null," +
                        "   imageHeight int not null" +
                        ")";
        db.execSQL(SQLEngines);

        final String SQLPowerCores =
                "create table powerCores " +
                        "(" +
                        "   cannonBoost int not null," +
                        "   engineBoost int not null," +
                        "   image varchar(255) not null" +
                        ")";
        db.execSQL(SQLPowerCores);


    }
}