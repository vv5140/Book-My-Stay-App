import java.util.LinkedList;
import java.util.Queue;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 *
 * Represents a booking request made by a guest.
 *
 * @version 5.0
 */
class Reservation {

    // Guest name
    private String guestName;

    // Requested room type
    private String roomType;

    // Constructor
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    // Getter for guest name
    public String getGuestName() {
        return guestName;
    }

    // Getter for room type
    public String getRoomType() {
        return roomType;
    }
}

/**
 * ============================================================
 * CLASS - BookingRequestQueue
 * ============================================================
 *
 * Handles booking requests using FIFO queue.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    // Queue to store booking requests
    private Queue<Reservation> requestQueue;

    // Constructor
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    // Get next request (FIFO)
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    // Check if queue has requests
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        // Display header
        System.out.println("Booking Request Queue\n");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create reservations
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Yamunathri", "Suite");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();

            System.out.println("Processing booking for Guest: "
                    + r.getGuestName()
                    + ", Room Type: "
                    + r.getRoomType());
        }
    }
}
