package rogue;

import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item  {

    private int itemID;
    private String itemName;
    private String itemType;
    private String descriptionItem;
    private char displayCharacter;

    private Point itemXyLocation;

    /**
     * Default constructor.
     */
    public Item() {
         itemXyLocation = new Point();
    }

    /**
     * Constructor.
     * @param id -
     * @param name -
     * @param type -
     * @param xyLocation -
     */
    public Item(int id, String name, String type, Point xyLocation) {
    //parse from json files
    }

    /**
     * @return int - ID num of item
     */
    public int getId() {
        return itemID;
    }

    /**
     * @param id - sets id of item
     */
    public void setId(int id) {
        this.itemID = id;
    }

    /**
     * @return String - name of the item
     */
    public String getName() {
        return itemName;
    }

    /**
     * @param name - sets name of item
     */
    public void setName(String name) {
        this.itemName = name;
    }

    /**
     * @return String - type of the item
     */
    public String getType() {
        return itemType;
    }

    /**
     * @param type - sets type of item
     */
    public void setType(String type) {
        this.itemType = type;
    }

    /**
     * @return Char - display character
     */
    public Character getDisplayCharacter() {
        return displayCharacter;
    }

    /**
     * @param newDisplayCharacter - sets displayCharacter of item
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        this.displayCharacter = newDisplayCharacter;
    }

    /**
     * @return String - description of the item
     */
    public String getDescription() {
        return descriptionItem;
    }

    /**
     * @param newDescription - sets description of item
     */
    public void setDescription(String newDescription) {
        this.descriptionItem = newDescription;
    }

    /**
     * @return Point - returns the location of the item
     */
    public Point getXyLocation() {
        return itemXyLocation;
    }

    /**
     * @param newXyLocation - sets (X, Y) location of item
     */
    public void setXyLocation(Point newXyLocation) {
        this.itemXyLocation = newXyLocation;
    }

    /**
     * @return Room - returns the current room
     */
    public Room getCurrentRoom() {
        return null;
    }

    /**
     * @param newCurrentRoom - sets room of item
     */
    public void setCurrentRoom(Room newCurrentRoom) {

    }
}
