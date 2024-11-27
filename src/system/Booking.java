package system;

import java.time.LocalDate;

public class Booking {

    private LocalDate date;
    private int tableID;
    private String timeslot;
    private Restaurant restaurant;
    private Customer customer;
    private String customerContact;
    private int ppl;
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

    public String getCustomerBookingData(){
        return restaurant.getRestaurantName() + ": " + timeslot + " " + ppl + "ppl" + "\n";
    }

    // Getter methods
    public LocalDate getDate() {
        return date;
    }

    public int getTableID() {
        return tableID;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public int getPpl() {
        return ppl;
    }

    public boolean hasArrived() {
        return arrive;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public void setPpl(int ppl) {
        this.ppl = ppl;
    }

    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }

    public void takeAttendance() {
        arrive = true;
    }
}
