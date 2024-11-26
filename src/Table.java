package src;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

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
        allTimeslots.sort(null);
    }

    public void addBookingTimeslot(Timeslot bookingTime) {
        this.bookedTimeSlot.add(bookingTime);
        this.bookedTimeSlot.sort(null);
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

    public boolean canbook(int customernumber, Duration timeMins) {
        if (customernumber > 0 && customernumber > this.getSeatNum()) {
            return false;
        }

        long timeMinsInt = timeMins == null ? 0 : timeMins.toMinutes();


        //boolean available = false;
        for (int i = 0; i < allTimeslots.size(); i++){
            Timeslot ts = allTimeslots.get(i);
            if (bookedTimeSlot.contains(ts)){
                continue;
            }
            //current timeslot is available

            LocalTime originalStartT = LocalTime.parse(ts.getSession().split(" - ")[0]);
            LocalTime currentEndT = LocalTime.parse(ts.getSession().split(" - ")[1]);
            LocalTime diff = currentEndT.minusNanos(originalStartT.toNanoOfDay());
            if (diff.toSecondOfDay() >= timeMinsInt){
                return true;
            }
            
            while (diff.toSecondOfDay() < timeMinsInt){ //if not enough time
                i++;
                Timeslot ts_2 = allTimeslots.get(i);
                if (i >= allTimeslots.size() || bookedTimeSlot.contains(ts_2)){
                    break;
                }
                currentEndT = LocalTime.parse(ts_2.getSession().split(" - ")[1]);
                diff = currentEndT.minusNanos(originalStartT.toNanoOfDay());
                if (diff.toSecondOfDay() >= timeMinsInt){
                    return true;
                }
            }
        }
        
        return false;
    }

    public boolean canbook(int customernumber, Duration timeMins, LocalTime startTime) {
        if (customernumber > 0 && customernumber > this.getSeatNum()) {
            return false;
        }

        long timeMinsInt = timeMins == null ? 0 : timeMins.toMinutes();

        LocalTime startTimeT = startTime;
        LocalTime endTimeT = startTimeT.plusMinutes(timeMinsInt);

        Timeslot ts;
        //boolean available = false;
        for (int i = 0; i < allTimeslots.size(); i++){
            ts = allTimeslots.get(i);
            if (LocalTime.parse(ts.getSession().split(" - ")[1]).isBefore(startTimeT) ){
                continue;
            }
            //now the current ts has the start time. Now we need to find the ts that has the end time. If any ts inbetween is in bookedTimeSlot return false
            if(bookedTimeSlot.contains(ts)){
                return false;
            }
            if (LocalTime.parse(ts.getSession().split(" - ")[1]).isAfter(endTimeT) ){
                return true;
            }
            
        }
        return false;
    }
}
