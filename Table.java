
import java.util.ArrayList;
import java.util.List;

public class Table {

    private int tableID;
    private int seatNum;
    private boolean status;
    private List<Timeslot> allTimeslots;
    private List<Timeslot> bookedTimeSlot;


    public Table(int tableID) {
        this.tableID = tableID;
        this.status = true; // Initially available
        this.allTimeslots = new ArrayList<>();
        this.bookedTimeSlot=new ArrayList<>();
    }

    public void addTimeslot(Timeslot timeslot) {
        allTimeslots.add(timeslot);
    }
    public void addBookingTimeslot(Timeslot bookingTime) {
        this.bookedTimeSlot.add(bookingTime);
    }

    public int getTableID() {
        return tableID;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public String getStatus() {
        if (status) {
            return "Available";
        } else {
            return "Not Available";
        }
    }

    public List<Timeslot> getAllTimeslots() {
        return allTimeslots;
    }

    public List<Timeslot> getBookingTimeslots() {
        return bookedTimeSlot;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAllTimeslots(List<Timeslot> allTimeslots) {
        this.allTimeslots = allTimeslots;
    }

    public void setBookingTimeslots(List<Timeslot> bookingTimeslot) {
        this.bookedTimeSlot = bookingTimeslot;
    }

}
