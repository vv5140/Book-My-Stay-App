import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * ABSTRACT CLASS - Room
 * ============================================================
 * Represents a generic room in the hotel.
 * @version 3.1
 */
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

/**
 * ============================================================
 * CLASS - SingleRoom
 * ============================================================
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * ============================================================
 * CLASS - DoubleRoom
 * ============================================================
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * ============================================================
 * CLASS - SuiteRoom
 * ============================================================
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 *
 * Centralized inventory using HashMap
 * @version 3.1
 */
class RoomInventory {

    // Key = Room Type, Value = Available Count
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes inventory
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initialize default availability
     */
    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Get availability map
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Update availability
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        // Display Single Room
        System.out.println("Single Room:");
        single.displayDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Single") + "\n");

        // Display Double Room
        System.out.println("Double Room:");
        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Double") + "\n");

        // Display Suite Room
        System.out.println("Suite Room:");
        suite.displayDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Suite"));
    }
}
