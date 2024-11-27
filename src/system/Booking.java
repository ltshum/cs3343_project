package system;

import java.time.LocalDate;

public class Booking {

    private final LocalDate date;
    private final int tableID;
    private final String timeslot;
    private final Restaurant restaurant;
    private final Customer customer;
    private final String customerContact;
    private final int ppl;
    private boolean arrive = false;

    public Booking(LocalDate date, int tableID, String timeslot, Restaurant restaurant, Customer customer, String customerContact, int ppl) {
        this.date = date;
        this.tableID = tableID;
        this.timeslot = timeslot;
        this.restaurant = restaurant;
        this.customer = customer;
        this.customerContact = customerContact;
        this.ppl = ppl;
    }

    public String getCustomerBookingData() {
        return restaurant.getRestaurantName() + ": " + timeslot + " " + ppl + "ppl" + "\n";
    }

    // Getter methods
    public LocalDate getBookingDate() {
        return date;
    }

    public int getBookingTableID() {
        return tableID;
    }

    public String getBookingTimeslot() {
        return timeslot;
    }

    public Restaurant getBookingRestaurant() {
        return restaurant;
    }

    public Customer getBookingCustomer() {
        return customer;
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
