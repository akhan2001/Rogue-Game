package rogue;
import java.awt.Point;

/**
 * The player character.
 */
public class Player {

    private String playerName;
    private Point playerXyLocation;
    private Room currentRoom;

    /**
     * Default constructor.
     */
    public Player() {
        playerXyLocation = new Point();
    }

    /**
     * Constructor.
     * @param name -
     */
    public Player(String name) {
        playerName = name;
        playerXyLocation = new Point();
    }

    /**
     * @return String - return playername of Player
     */
    public String getName() {
        return playerName;
    }

    /**
     * @param newName - sets name of player
     */
    public void setName(String newName) {
        playerName = newName;
    }

    /**
     * @return Point - return (X,Y) location of Player
     */
    public Point getXyLocation() {
        return playerXyLocation;
    }

    /**
     * @param newXyLocation - sets (X, Y) location of player
     */
    public void setXyLocation(Point newXyLocation) {
        playerXyLocation = newXyLocation;
    }

    /**
     * @return Room - return room of Player
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @param newRoom - sets room of player
     */
    public void setCurrentRoom(Room newRoom) {
        currentRoom = newRoom;
    }

}
