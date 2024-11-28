package system;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final int tableID;
    private int seatNum;
    private List<Timeslot> allTimeslots;
    private List<Timeslot> bookedTimeSlot;

    public Table(int tableID) {
        this.tableID = tableID;
        this.allTimeslots = new ArrayList<>();
        this.bookedTimeSlot = new ArrayList<>();
    }

    public void addTimeslot(String session) {
        Timeslot timeslot = new Timeslot(session);
        allTimeslots.add(timeslot);
        allTimeslots.sort((t1, t2) -> {
            LocalTime time1 = LocalTime.parse(t1.getTimeslotSession().split(" - ")[0]);
            LocalTime time2 = LocalTime.parse(t2.getTimeslotSession().split(" - ")[0]);
            return time1.compareTo(time2);
        });

    }

    public int getTableID() {
        return tableID;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public List<Timeslot> getAllTimeslots() {
        return allTimeslots;
    }

    public boolean isTimeslotAvailable(String timeslotSession, LocalDate date) {
        for (Timeslot timeslot : bookedTimeSlot) {
            if (timeslot.getTimeslotSession().equals(timeslotSession) && timeslot.getTimeslotDate().equals(date)) {
                return false;
            }
        }
        return true;
    }

    public void setTimeslotUnavailable(String timeslotSession, LocalDate date) {
        Timeslot t = new Timeslot(timeslotSession);
        t.setTimeslotDate(date);
        bookedTimeSlot.add(t);
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public boolean canbook(int customernumber, Duration timeMins) {
        if (customernumber > this.getSeatNum()) {
            return false;
        }

        long durationMinutes = (timeMins == null) ? 0 : timeMins.toMinutes();

        //boolean available = false;
        for (Timeslot timeSlot : allTimeslots) {
            // Skip booked time slots
            if (bookedTimeSlot.contains(timeSlot)) {
                continue;
            }

            // Parse start and end times
            LocalTime startTime = LocalTime.parse(timeSlot.getTimeslotSession().split(" - ")[0]);
            LocalTime endTime = LocalTime.parse(timeSlot.getTimeslotSession().split(" - ")[1]);

            // Calculate available time in minutes
            long availableMinutes = Duration.between(startTime, endTime).toMinutes();

            // If the available time is enough, return true
            if (availableMinutes >= durationMinutes) {
                return true;
            }

            // Check consecutive time slots if not enough time
            for (int i = allTimeslots.indexOf(timeSlot) + 1; i < allTimeslots.size(); i++) {
                Timeslot nextTimeSlot = allTimeslots.get(i);

                // Skip booked time slots
                if (bookedTimeSlot.contains(nextTimeSlot)) {
                    continue;
                }

                endTime = LocalTime.parse(nextTimeSlot.getTimeslotSession().split(" - ")[1]);
                availableMinutes = Duration.between(startTime, endTime).toMinutes();

                // If the available time is enough, return true
                if (availableMinutes >= durationMinutes) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canbookWithStartTime(int customernumber, Duration timeMins, LocalTime startTime) {
        if (customernumber > this.getSeatNum()) {
            return false;
        }

        long timeMinsInt = timeMins == null ? 0 : timeMins.toMinutes();

        LocalTime startTimeT = startTime;
        LocalTime endTimeT = startTimeT.plusMinutes(timeMinsInt);

        Timeslot ts;
        //boolean available = false;
        for (int i = 0; i < allTimeslots.size(); i++) {
            ts = allTimeslots.get(i);
            if (LocalTime.parse(ts.getTimeslotSession().split(" - ")[1]).isBefore(startTimeT)) {
                continue;
            }
            //now the current ts has the start time. Now we need to find the ts that has the end time. If any ts inbetween is in bookedTimeSlot return false
            if (bookedTimeSlot.contains(ts)) {
                return false;
            }
            if (LocalTime.parse(ts.getTimeslotSession().split(" - ")[1]).isAfter(endTimeT)) {
                return true;
            }

        }
        return false;
    }

    public void setAllTimeslots(List<Timeslot> allTimeslots) {
        this.allTimeslots = allTimeslots;
    }

    public void setBookedTimeslot(List<Timeslot> bookedTimeSlot) {
        this.bookedTimeSlot = bookedTimeSlot;
    }
}
