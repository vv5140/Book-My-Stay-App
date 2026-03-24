import java.util.*;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 * @version 8.0
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
 * CLASS - BookingHistory
 * ============================================================
 *
 * Stores confirmed reservations in order
 * @version 8.0
 */
class BookingHistory {

    // List to store reservations (ordered)
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    /**
     * Add reservation to history
     */
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    /**
     * Get all reservations
     */
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * ============================================================
 * CLASS - BookingReportService
 * ============================================================
 *
 * Generates reports from history
 * @version 8.0
 */
class BookingReportService {

    /**
     * Display booking report
     */
    public void generateReport(BookingHistory history) {

        System.out.println("Booking History and Reporting\n");

        System.out.println("Booking History Report");

        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println("Guest: "
                    + r.getGuestName()
                    + ", Room Type: "
                    + r.getRoomType());
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Yamunathri", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}
