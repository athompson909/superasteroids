package edu.byu.cs.superasteroids.model_classes;

import java.util.ArrayList;

/**
 * Created by athom909 on 2/9/16.
 */
public class LevelAsteroid {

    private int level;

    private int number;
    private int asteroidId;
    private ArrayList<Asteroid> asteroids;

    public LevelAsteroid() {
        level = 0;
        number = 0;
        asteroidId = 0;
        asteroids = null;
    }

    public LevelAsteroid(int level, int number, int asteroidId) {
        this.level = level;
        this.number = number;
        this.asteroidId = asteroidId;
        asteroids = null;
    }

    public LevelAsteroid(int level, int number, int asteroidId, ArrayList<Asteroid> asteroids) {
        this.level = level;
        this.number = number;
        this.asteroidId = asteroidId;
        this.asteroids = asteroids;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAsteroidId() {
        return asteroidId;
    }

    public void setAsteroidId(int asteroidId) {
        this.asteroidId = asteroidId;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}