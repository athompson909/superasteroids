package edu.byu.cs.superasteroids.model_classes;

/**
 * Created by athom909 on 2/5/16.
 * contains the names of all the background images, this kind of has ties with the levelObjects
 *  object
 */
public class BGImage {

    private String name;
    private int id;

    private int contentManagerId;

    /**
     * This constructor is used in the ObjecsDAO object
     * @param name the path to the image
     * @param id this an index that is autoincremented by the database
     */
    public BGImage(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public BGImage(String name, int id, int contentManagerId) {
        this.name = name;
        this.id = id;
        this.contentManagerId = contentManagerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentManagerId() {
        return contentManagerId;
    }

    /**
     * This is used when actually loading the game content
     * @param contentManagerId
     */
    public void setContentManagerId(int contentManagerId) {
        this.contentManagerId = contentManagerId;
    }
}
