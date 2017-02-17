package edu.byu.cs.superasteroids.model_classes;

import java.util.ArrayList;

/**
 * Created by athom909 on 2/9/16.
 */
public class Level {
    private int number;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String music;
    private ArrayList<LevelObject> levelObjects;
    private ArrayList<LevelAsteroid> levelAsteroids;

    public Level() {
        number = 0;
        title = null;
        hint = null;
        width = 0;
        height = 0;
        music = null;
        levelObjects = null;
        levelAsteroids = null;
    }

    // we're going width, then height, which is correct!
    public Level(int number, String title, String hint, int width, int height, String music,
                 ArrayList<LevelObject> levelObjects, ArrayList<LevelAsteroid> levelAsteroids) {
        this.number = number;
        this.title = title;
        this.hint = hint;
        this.width = width;
        this.height = height;
        this.music = music;
        this.levelObjects = levelObjects;
        this.levelAsteroids = levelAsteroids;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public ArrayList<LevelObject> getLevelObjects() {
        return levelObjects;
    }

    public void setLevelObjects(ArrayList<LevelObject> levelObjects) {
        this.levelObjects = levelObjects;
    }

    public ArrayList<LevelAsteroid> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(ArrayList<LevelAsteroid> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }
}
