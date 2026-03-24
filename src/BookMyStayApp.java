import java.util.*;

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

    public void increaseRoom(String type) {
        availability.put(type, availability.get(type) + 1);
    }
}

/**
 * ============================================================
 * CLASS - CancellationService
 * ============================================================
 * @version 10.0
 */
class CancellationService {

    // Stack to track released room IDs (LIFO)
    private Stack<String> releasedRoomIds;

    // Map reservation ID -> room type
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Register confirmed booking
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancel booking and rollback
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        // Validate reservation
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation request.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Push to rollback stack
        releasedRoomIds.push(reservationId);

        // Restore inventory
        inventory.increaseRoom(roomType);

        // Remove booking
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    /**
     * Show rollback history
     */
    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize cancellation service
        CancellationService service = new CancellationService();

        // Simulate confirmed booking
        String reservationId = "Single-1";
        service.registerBooking(reservationId, "Single");

        // Cancel booking
        service.cancelBooking(reservationId, inventory);

        // Show rollback history
        service.showRollbackHistory();

        // Show updated availability
        System.out.println("\nUpdated Single Room Availability: " + inventory.getAvailable("Single"));
    }

}
