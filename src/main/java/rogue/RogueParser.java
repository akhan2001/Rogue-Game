package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser {

    private ArrayList<Map<String, String>> rooms;
    private ArrayList<Map<String, String>> items;
    private ArrayList<Map<String, String>> itemLocations;
    private ArrayList<Map<String, Integer>> doors;
    private HashMap<String, Character> symbols;

    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> itemIterator;
    private Iterator<Map<String, Integer>> doorIterator;

    private int numOfRooms = 0;
    private int numItemsInRoom = 0;
    private int numOfItems = 0;

    /**
     * Default constructor.
     */
    public RogueParser() {
        rooms = new ArrayList<>();
        items = new ArrayList<>();
        itemLocations = new ArrayList<>();
        doors = new ArrayList<>();
        symbols = new HashMap<>();
    }

    /**
     * Constructor that takes filename and sets up the object.
     * @param filename  (String) name of file that contains file location for rooms and symbols
     */
    public RogueParser(String filename) {
        rooms = new ArrayList<>();
        items = new ArrayList<>();
        itemLocations = new ArrayList<>();
        doors = new ArrayList<>();
        symbols = new HashMap<>();
        parse(filename);
    }

    /**
     * Return the next door.
     * @return Map information about a door.
     */
    public Map nextDoor() {
        if (doorIterator.hasNext()) {
            return doorIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Return the next room.
     * @return (Map) information about a room.
     */
    public Map nextRoom() {
        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the next item.
     * @return (Map) Information about an item
     */
    public Map nextItem() {
        if (itemIterator.hasNext()) {
            return itemIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Get the character for a symbol.
     * @param symbolName (String) Symbol Name
     * @return (Character) Display character for the symbol
     */
    public Character getSymbol(String symbolName) {
        if (symbols.containsKey(symbolName)) {
            return symbols.get(symbolName);
        }

        // Does not contain the key
        return null;
    }

    /**
     * Get the number of doors.
     * @return (int) Number of doors.
     */
    public int getNumOfDoors() {
        return (doors.size());
    }

    /**
     * Get the number of items.
     * @return (int) Number of items
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     * Get the number of rooms.
     * @return (int) Number of rooms
     */
    public int getNumOfRooms() {
        return numOfRooms;
    }

    /**
     * Get the number of items in the rooms.
     * @return (int) Num of items in the rooms
     */
    public int getNumOfItemsInRoom() {
        return numItemsInRoom;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     */
    private void parse(String filename) {

        JSONParser parser = new JSONParser();
        JSONObject roomsJSON;
        JSONObject symbolsJSON;

        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject configurationJSON = (JSONObject) obj;

            // Extract the Rooms value from the file to get the file location for rooms
            String roomsFileLocation = (String) configurationJSON.get("Rooms");
            // Extract the Symbols value from the file to get the file location for symbols-map
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");

            Object roomsObj = parser.parse(new FileReader(roomsFileLocation));
            roomsJSON = (JSONObject) roomsObj;
            Object symbolsObj = parser.parse(new FileReader(symbolsFileLocation));
            symbolsJSON = (JSONObject) symbolsObj;

            extractRoomInfo(roomsJSON);
            extractItemInfo(roomsJSON);
            extractSymbolInfo(symbolsJSON);
            roomIterator = rooms.iterator();
            itemIterator = items.iterator();
            doorIterator = doors.iterator();

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }
    }

    /**
     * Get the symbol information.
     * @param symbolsJSON  (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject symbolsJSON) {

        JSONArray symbolsJSONArray = (JSONArray) symbolsJSON.get("symbols");

        // Make an array list of room information as maps
        for (int i = 0; i < symbolsJSONArray.size(); i++) {
            JSONObject symbolObj = (JSONObject) symbolsJSONArray.get(i);
            symbols.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")).charAt(0));
        }
    }

    /**
     * Get the room information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject roomsJSON) {

        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");

        // Make an array list of room information as maps
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            rooms.add(singleRoom((JSONObject) roomsJSONArray.get(i)));
            numOfRooms++;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {

        HashMap<String, String> room = new HashMap<>();
        HashMap<String, Integer> door = new HashMap<>();

        room.put("id", roomJSON.get("id").toString());
        room.put("start", roomJSON.get("start").toString());
        room.put("height", roomJSON.get("height").toString());
        room.put("width", roomJSON.get("width").toString());

        // Update the map with any doors in the room
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int j = 0; j < doorArray.size(); j++) {
            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = String.valueOf(doorObj.get("dir"));
            door.put(dir, Integer.parseInt(doorObj.get("wall_pos").toString()));
            door.put(dir + "connected", Integer.parseInt(doorObj.get("con_room").toString()));
        }
        doors.add(door);
        //System.out.println(door + "ooga");

        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        // Loop through each item and update the hashmap
        for (int j = 0; j < lootArray.size(); j++) {
            itemLocations.add(itemPosition((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }

        return room;
    }

    private Map<String, Integer> singleDoor(JSONObject roomJSON) {

        HashMap<String, Integer> door = new HashMap<>();

        // Cheap way of making sure all 4 directions have a sentinel value in the map
        /*door.put("E", -1);
        door.put("N", -1);
        door.put("S", -1);
        door.put("W", -1);*/

        //JSONObject doorObj = (JSONObject) roomJSON;
        String dir = String.valueOf(roomJSON.get("dir"));
        door.put(dir, Integer.parseInt(roomJSON.get("wall_pos").toString()));
        door.put(dir + "connected", Integer.parseInt(roomJSON.get("con_room").toString()));

        //System.out.println("\n" + door);

        return door;
    }

    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map<String, String>) Contains information about the item, where it is and what room
     */
    private Map<String, String> itemPosition(JSONObject lootJSON, String roomID) {

        HashMap<String, String> loot = new HashMap<>();

        loot.put("room", roomID);
        loot.put("id", lootJSON.get("id").toString());
        loot.put("x", lootJSON.get("x").toString());
        loot.put("y", lootJSON.get("y").toString());

        return loot;
    }

    /**
     * Get the Item information from the Item key.
     * @param roomsJSON (JSONObject) The entire JSON file that contains keys for room and items
     */
    private void extractItemInfo(JSONObject roomsJSON) {

        JSONArray itemsJSONArray = (JSONArray) roomsJSON.get("items");

        for (int i = 0; i < itemsJSONArray.size(); i++) {
            items.add(singleItem((JSONObject) itemsJSONArray.get(i)));
            numOfItems++;
        }
    }

    /**
     * Get a single item from its JSON object.
     * @param itemsJSON (JSONObject) JSON version of an item
     * @return (Map<String, String>) Contains information about a single item
     */
    private Map<String, String> singleItem(JSONObject itemsJSON) {

        HashMap<String, String> item = new HashMap<>();
        item.put("id", itemsJSON.get("id").toString());
        item.put("name", itemsJSON.get("name").toString());
        item.put("type", itemsJSON.get("type").toString());
        item.put("description", itemsJSON.get("description").toString());

        for (Map<String, String> itemLocation : itemLocations) {
            if (itemLocation.get("id").toString().equals(item.get("id").toString())) {
                item.put("room", itemLocation.get("room"));
                item.put("x", itemLocation.get("x"));
                item.put("y", itemLocation.get("y"));
                numItemsInRoom++;
                break;
            }
        }
        //System.out.println("\n" + item);
        return item;
    }
}
