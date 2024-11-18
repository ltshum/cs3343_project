
import java.time.LocalDate;

public class Booking {

    private LocalDate date;
    private int tableID;
    private String timeslot;
    private String restaurantName;
    private String customerName;
    private String customerContact;
    private int ppl;
    private boolean arrive = false;

    public Booking(LocalDate date, int tableID, String timeslot, String restaurantName, String customerName, String customerContact, int ppl) {
        this.date = date;
        this.tableID = tableID;
        this.timeslot = timeslot;
        this.restaurantName = restaurantName;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.ppl = ppl;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getCustomerName() {
        return customerName;
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

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
