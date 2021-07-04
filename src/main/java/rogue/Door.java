/*To facilitate player movement, we must maintain information about which rooms are connected
by each door. We will do this using a Door.java class. Door.java must have the following
public methods:

- public Door() //default constructor
- public void connectRoom(Room r);  //specify one of the two rooms that can be attached to a door
- public ArrayList<Room> getConnectedRooms();  //get an Arraylist that contains both rooms
connected by this door
- public Room getOtherRoom(Room currentRoom); //get the connected room by passing in the
current room.  i.e.  I am in the kitchen:  door.getOtherRoom(kitchen) returns dining room.

We will guarantee that no door has more than two rooms attached to it.*/

package rogue;
import java.util.ArrayList;
import java.util.HashMap;

public class Door {

	private Integer room1Location;
	private Integer room2Location;
	private int numofDoors;

	private ArrayList<Room> connectedRooms;

	private HashMap<Room, Integer> wallPositions;
	private HashMap<Room, Integer> wallDirections;
	private HashMap<String, Integer> doors;
	private HashMap<String, Integer> connectedDoors;

	private String directions;

	/**
	 * default constructor.
	 */
	public Door() {
		connectedRooms = new ArrayList<>();
		wallPositions = new HashMap<>();
		wallDirections = new HashMap<>();
		doors = new HashMap<>();
		connectedDoors = new HashMap<>();
	}

	/**
	 * specify one of the two rooms that can be attached to a door.
	 * @param r -
	 */
	public void connectRoom(Room r) {

	}

	/**
	 * //get an Arraylist that contains both rooms connected by this door.
	 * @return ArrayList of rooms
	 */
	public ArrayList<Room> getConnectedRooms() {
		return null;
	}

	/**
	 * get the connected room by passing in the current room.
	 * @param currentRoom -
	 * @return Room - returns the other room
	 */
	public Room getOtherRoom(Room currentRoom) {
		//check that there are rooms
		/*Check if room at pos. 0 in arraylist == currentroom
			return room at position 1
		else
			return room at positon 0*/
		return null;
	}

	/**
	 * @param direction -
	 * @param wallPosition -
	 */
	public void setWallPosition(String direction, Integer wallPosition) {
		doors.put(direction, wallPosition);
	}

	/**
	 * @param direction (String) -
	 * @return (Integer) -
	 */
	public Integer getWallPosition(String direction) {
		if (doors.containsKey(direction)) {
			return doors.get(direction);
		} else {
			return -1;
		}
	}

	/**
	 * @param numDoors -
	 */
	public void setNumDoors(int numDoors) {
		numofDoors = numDoors;
	}

	/**
	 * @return (int) -
	 */
	public int getNumDoors() {
		return numofDoors;
	}
}
