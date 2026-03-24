import java.util.*;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 * @version 6.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * ============================================================
 * CLASS - BookingRequestQueue (FIFO)
 * ============================================================
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

/**
 * ============================================================
 * CLASS - RoomAllocationService
 * ============================================================
 * Prevents double booking using Set
 */
class RoomAllocationService {

    // All allocated room IDs
    private Set<String> allocatedRooms;

    // Track assigned rooms by type
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRooms = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Allocate room safely
     */
    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        // Check availability
        if (inventory.getAvailable(type) <= 0) {
            System.out.println("No rooms available for " + type);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Store allocation
        allocatedRooms.add(roomId);

        assignedRoomsByType.putIfAbsent(type, new HashSet<>());
        assignedRoomsByType.get(type).add(roomId);

        // Update inventory
        inventory.reduceRoom(type);

        // Confirmation
        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName()
                + ", Room ID: " + roomId);
    }

    /**
     * Generate unique room ID
     */
    private String generateRoomId(String type) {

        assignedRoomsByType.putIfAbsent(type, new HashSet<>());

        int count = assignedRoomsByType.get(type).size() + 1;
        String roomId = type + "-" + count;

        // Ensure uniqueness
        while (allocatedRooms.contains(roomId)) {
            count++;
            roomId = type + "-" + count;
        }

        return roomId;
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Create queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Yamunathri", "Suite"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Allocation service
        RoomAllocationService service = new RoomAllocationService();

        // Process FIFO
        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}
