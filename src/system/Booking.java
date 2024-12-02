package system;

import java.time.LocalDate;

public class Booking {

    private final LocalDate date;
    private final int tableID;
    private final String timeslot;
    private final String restaurantName;
    private final String restaurantUserName;
    private final String customerName;
    private final String customerContact;
    private final int ppl;
    private boolean arrive = false;

    public Booking(LocalDate date, int tableID, String timeslot, String restaurantName, String restaurantUserName, String customerName, String customerContact, int ppl) {
        this.date = date;
        this.tableID = tableID;
        this.timeslot = timeslot;
        this.restaurantName = restaurantName;
        this.restaurantUserName = restaurantUserName;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.ppl = ppl;
    }

    public String getRestaurantUserName() {
        return restaurantUserName;
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
