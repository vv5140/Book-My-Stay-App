import java.util.*;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
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
 * CLASS - BookingRequestQueue
 * ============================================================
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

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

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
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

    public Map<String, Integer> getAll() {
        return availability;
    }
}

/**
 * ============================================================
 * CLASS - RoomAllocationService
 * ============================================================
 */
class RoomAllocationService {

    private Map<String, Integer> counters = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (inventory.getAvailable(type) <= 0) {
            return;
        }

        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);

        String roomId = type + "-" + count;

        inventory.reduceRoom(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName()
                + ", Room ID: " + roomId);
    }
}

/**
 * ============================================================
 * CLASS - ConcurrentBookingProcessor
 * ============================================================
 */
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // Critical section 1: queue access
            synchronized (bookingQueue) {
                if (!bookingQueue.hasRequests()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            // Critical section 2: inventory + allocation
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        // Shared resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Yamunathri", "Double"));
        bookingQueue.addRequest(new Reservation("Kunal", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // Create threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Final inventory
        System.out.println("\nRemaining Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
