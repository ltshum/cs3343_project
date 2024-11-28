package system;

import java.time.LocalDate;

public class Booking {

    private final LocalDate date;
    private final int tableID;
    private final String timeslot;
    private final String restaurantName;
    private final int restaurantId;
    private String customerName;
    private int customerId;
    private final String customerContact;
    private final int ppl;
    private boolean arrive = false;

    public Booking(LocalDate date, int tableID, String timeslot, String restaurantName, int restaurantId, String customerName, int customerId, String customerContact, int ppl) {
        this.date = date;
        this.tableID = tableID;
        this.timeslot = timeslot;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.customerId = customerId;
        this.customerContact = customerContact;
        this.ppl = ppl;
    }

    public String getCustomerBookingData() {
        return restaurantName + ": " + timeslot + " " + ppl + "ppl" + "\n";
    }

    // Getter methods
    public LocalDate getBookingDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getBookingTableID() {
        return tableID;
    }

    public String getBookingTimeslot() {
        return timeslot;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getBookingCustomerContact() {
        return customerContact;
    }

    public int getBookingPpl() {
        return ppl;
    }

    public boolean hasArrived() {
        return arrive;
    }

    public void takeAttendance() {
        arrive = true;
    }

    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }

}
