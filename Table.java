
import java.time.LocalDate;
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
        this.bookedTimeSlot = new ArrayList<>();
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

    public boolean isTimeslotAvailable(String timeslotSession, LocalDate date) {
        for (Timeslot timeslot : bookedTimeSlot) {
            if (timeslot.getSession().equals(timeslotSession) && timeslot.getDate().equals(date)) {
                return false; 
            }
        }
        return true;
    }

    public void setTimeslotUnavailable(String timeslotSession, LocalDate date) {
        Timeslot t = new Timeslot(timeslotSession);
        t.setDate(date);
        bookedTimeSlot.add(t);
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

    public boolean canbook(int customernumber, Timeslot time) {
        return !(customernumber > this.getSeatNum() && !this.bookedTimeSlot.contains(time));
    }

}
