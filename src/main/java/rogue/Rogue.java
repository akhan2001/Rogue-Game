package rogue;

import java.util.ArrayList;
import java.util.Map;

import java.awt.Point;


public class Rogue {

//Declaring constants for player movement
	public static final char UP = 'w';
	public static final char DOWN = 's';
	public static final char LEFT = 'a';
	public static final char RIGHT = 'd';

	private RogueParser parser = new RogueParser();
	private Room theRoom;
	private Item theItem;
	private Door theDoor;
	private Player player;

	private Point itemLocations;
	private Point initialLoc;

	private ArrayList<Room> myRooms = new ArrayList<>();
	private ArrayList<Item> myItems = new ArrayList<>();
	private ArrayList<Door> myDoors = new ArrayList<>();

	/**
	 * single parameter constructor.
	 * @param theDungeonInfo -
	 */
	public Rogue(RogueParser theDungeonInfo) {
		parser = theDungeonInfo;

		Map roomInfo = parser.nextRoom();
		while (roomInfo != null) {
			addRoom(roomInfo);
			roomInfo = parser.nextRoom();
		}

		Map itemInfo = parser.nextItem();
		while (itemInfo != null) {
			addItem(itemInfo);
			itemInfo = parser.nextItem();
		}

		Map doorInfo = parser.nextDoor();
		while (doorInfo != null) {
			addDoor(doorInfo);
			doorInfo = parser.nextDoor();
		}
	}

	/**
	 * Calculates the next display, but does not return it.  It returns a message for the player.
	 * getNextDisplay() must be called to get the display string.
	 * @param input (char)
	 * @return String
	 * @throws InvalidMoveException - exception
	 */
	public String makeMove(char input) throws InvalidMoveException {
		String message;

		if (input == 'w' || input == 'a' || input == 's' || input == 'd') {
			setPlayerLoc(input);
		}

		message = "Good move! - " + Character.toString(input);
		return message;
	}

	/**
	 * sets the player location.
	 * @param input (char)
	 * @throws InvalidMoveException -
	 */
	public void setPlayerLoc(char input) throws InvalidMoveException {
		initialLoc = player.getXyLocation();
		Point newLocation = new Point();

		Double x = initialLoc.getX();
		Double y = initialLoc.getY();

		if (input == UP) {
			y--;
			//y++;
			//x--;
			//x++;
			if (y == 1) {
				throw new InvalidMoveException();
			}
			newLocation.setLocation(x, y);
		} else if (input == DOWN) {
			//y--;
			y++;
			//x--;
			//x++;
			if (y == myRooms.get(0).getHeight()) {
				throw new InvalidMoveException();
			}
			newLocation.setLocation(x, y);
		} else if (input == RIGHT) {
			//y--;
			//y++;
			x--;
			//x++;
			if (x == myRooms.get(0).getWidth()) {
				throw new InvalidMoveException();
			}
			newLocation.setLocation(x, y);
		} else if (input == LEFT) {
			//y--;
			//y++;
			//x--;
			x++;
			if (x == 1) {
				throw new InvalidMoveException();
			}
			newLocation.setLocation(x, y);
		}
		player.setXyLocation(newLocation);
	}

	/**
	 * Returns the updated display string after a move has been made.
	 * @return String
	 */
	public String getNextDisplay() {
		String display = "\n\n";
		int height = myRooms.get(0).getHeight();
		int width = myRooms.get(0).getWidth();
		Point locPlayer = player.getXyLocation();
		Double x = locPlayer.getX();
		Double y = locPlayer.getY();

		int nDoor = myDoors.get(0).getWallPosition("N");
		int eDoor = myDoors.get(0).getWallPosition("E");
		int sDoor = myDoors.get(0).getWallPosition("S");
		int wDoor = myDoors.get(0).getWallPosition("W");

		for (int i = 1; i <= height; i++) {
			for (int j = 1; j <= width; j++) {

				display = itemDisplays(display, j, i);

//Prints the player at the beginning of the room
				if (i == x && j == y) {
					display += parser.getSymbol("PLAYER");
				} else if (j == 1 && i == wDoor + 1) {
//Prints doors for south and north of the room
					display += parser.getSymbol("DOOR");
				} else if (j == width && i == eDoor + 1) {
					display += parser.getSymbol("DOOR");
				} else if (i == 1 && j == nDoor + 1) {
//Prints doors for east and west of the room
					display += parser.getSymbol("DOOR");
				} else if (i == height && j == sDoor + 1) {
					display += parser.getSymbol("DOOR");
				} else if (i == 1 || i == height) {
//Prints the walls for the room
					display += parser.getSymbol("NS_WALL");
				} else if (j == 1 || j == width) {
					display += parser.getSymbol("EW_WALL");
				} else {
					display += parser.getSymbol("FLOOR");
				}
			}
			display += "\n";
		}
		return display;
	}

	/**
	 * @param display (String) -
	 * @param j (int) -
	 * @param i (int) -
	 * @return (String)
	*/
	public String itemDisplays(String display, int j, int i) {
		char itemDisplay = '*';
		for (Item tempItem : myItems) {
			Point locItems = tempItem.getXyLocation();
//Prints the items at their locations in the room
			if (j == locItems.getX() && i == locItems.getY()) {
				if (tempItem.getType().equals("potion")) {
					itemDisplay = parser.getSymbol("POTION");
				} else if (tempItem.getType().equals("scroll")) {
					itemDisplay = parser.getSymbol("SCROLL");
				} else if (tempItem.getType().equals("armour")) {
					itemDisplay = parser.getSymbol("ARMOR");
				} else if (tempItem.getType().equals("food")) {
					itemDisplay = parser.getSymbol("FOOD");
				} else if (tempItem.getType().equals("gold")) {
					itemDisplay = parser.getSymbol("GOLD");
				}
				display += Character.toString(itemDisplay);
			}
		}
		return display;
	}

	/**
	 * For setting up the game. Adds a door to the game using the map provided by RogueParser.
	 * @param toAdd (Map)
	 */
	public void addDoor(Map<String, Integer> toAdd) {
		theDoor = new Door();

		theDoor.setNumDoors(toAdd.size() / 2);

		if (toAdd.containsKey("N")) {
			theDoor.setWallPosition("N", toAdd.get("N"));
		}
		if (toAdd.containsKey("E")) {
			theDoor.setWallPosition("E", toAdd.get("E"));
		}
		if (toAdd.containsKey("S")) {
			theDoor.setWallPosition("S", toAdd.get("S"));
		}
		if (toAdd.containsKey("W")) {
			theDoor.setWallPosition("W", toAdd.get("W"));
		}

		myDoors.add(theDoor);
	}

	/**
	 * For setting up the game. Adds a room to the game using the map provided by RogueParser.
	 * @param toAdd (Map)
	 */
	public void addRoom(Map<String, String> toAdd) {
		theRoom = new Room();

		theRoom.setId(Integer.parseInt(toAdd.get("id")));
		theRoom.setStart(Boolean.parseBoolean(toAdd.get("start")));
		theRoom.setWidth(Integer.parseInt(toAdd.get("width")));
		theRoom.setHeight(Integer.parseInt(toAdd.get("height")));

		myRooms.add(theRoom);
	}

	/**
	 * For setting up the game. Adds an item to the game using the map provided by RogueParser.
	 * @param toAdd -
	 */
	public void addItem(Map<String, String> toAdd) {
		theItem = new Item();

		theItem.setId(Integer.parseInt(toAdd.get("id")));
		theItem.setName(toAdd.get("name"));
		theItem.setType(toAdd.get("type"));
		theItem.setDescription(toAdd.get("description"));

		if (toAdd.containsKey("x") || toAdd.containsKey("y")) {
			itemLocations = new Point();
			itemLocations.setLocation(Integer.parseInt(toAdd.get("x")), Integer.parseInt(toAdd.get("y")));
			theItem.setXyLocation(itemLocations);
		}

		myItems.add(theItem);
	}

	/**
	 * @param thePlayer -
	 */
	public void setPlayer(Player thePlayer) {
		player = thePlayer;
		initialLoc = new Point(2, 2);
		player.setXyLocation(initialLoc);
	}

	/**
	 * @return Point -
	 */
	public Point getPlayerLoc() {
		return player.getXyLocation();
	}

	/**
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return ArrayList of getRooms
	 */
	public ArrayList<Room> getRooms() {
		return myRooms;
	}

	/**
	 * @return ArrayList of getItems
	 */
	public ArrayList<Item> getItems() {
		return myItems;
	}

}
