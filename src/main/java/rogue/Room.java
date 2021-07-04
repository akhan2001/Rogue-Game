package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;


/**
 * A room within the dungeon - contains monsters, treasure.
 * doors out, etc.
 */
public class Room  {

  private int width;
  private int height;
  private int id;
  private boolean start;

  private Player player;

  private ArrayList<Item> roomItems;
  private ArrayList<Room> totalRooms;

  private HashMap<String, Integer> doors;

  /**
   * Default constructor.
   */
  public Room() {
    roomItems = new ArrayList<>();
    totalRooms = new ArrayList<>();
    doors = new HashMap<>();
  }


  /**
   * @param toAdd - add items
   * @throws ImpossiblePositionException -
   * @throws NoSuchItemException -
   */
  public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException {
    Point location = toAdd.getXyLocation();
    // int x = Integer.parseInt(location.getX());
    // int y = Integer.parseInt(location.getY());
    Double x = location.getX();
    Double y = location.getY();

    if ((x == width) || (x == height) || (y == width) || (y == height)) {
      throw new ImpossiblePositionException("~ The item cannot be placed here ~");
    }

    // if (x != 0 && y != 0) {

    // }
  }

  /**
   * @return boolean - true or false for room verification
   * @throws NotEnoughDoorsException -
   */
  public boolean verifyRoom() throws NotEnoughDoorsException {

    return false;
  }

  /**
   * @return int - return width of room
   */
  public int getWidth() {
    return width;
  }

  /**
   * @param newWidth - sets new width for room
   */
  public void setWidth(int newWidth) {
    width = newWidth;
  }

  /**
   * @return int - return height of room
   */
  public int getHeight() {
    return height;
  }

  /**
   * @param newHeight - sets new height for room
   */
  public void setHeight(int newHeight) {
    height = newHeight;
  }

  /**
   * @return int - returns the id for room
   */
  public int getId() {
    return id;
  }

  /**
   * @param newId - sets new id for room
   */
  public void setId(int newId) {
    id = newId;
  }

  /**
   * @return ArrayList of room items
   */
  public ArrayList<Item> getRoomItems() {
    return roomItems;
  }

  /**
   * @param newRoomItems - sets roomItems to new RoomItems
   */
  public void setRoomItems(ArrayList<Item> newRoomItems) {
    roomItems = newRoomItems;
  }

  /**
   * @return Player - returns player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * @param newPlayer - sets player for room
   */
  public void setPlayer(Player newPlayer) {
    player = newPlayer;
  }

  /**
   * @param direction -
   * @return door - returns room
   */
  public int getDoor(String direction) {
    return doors.get(direction);
  }


  /**
   * direction is one of NSEW.
   * location is a number between 0 and the length of the wall
   * @param direction -
   * @param location -
   */
  public void setDoor(String direction, int location) {
    doors.put(direction, location);
  }

  /**
   * @return start - can be true or false
   */
  public boolean isPlayerInRoom() {
    return start;
  }

  /**
   * @param newStart -
   */
  public void setStart(boolean newStart) {
    start = newStart;
  }

  /**
  * Produces a string that can be printed to produce an ascii rendering.
  * of the room and all of its contents
  * @return (String) String representation of how the room looks
  */
  public String displayRoom() {
    return null;
  }

}
