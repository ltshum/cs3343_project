package system;

import java.time.LocalDate;

public class Timeslot{

    private LocalDate date;
    private final String session;

    public Timeslot(String session) {
        this.session = session;
    }

    // Getters and setters
    public String getTimeslotSession() {
        return session;
    }

    public LocalDate getTimeslotDate() {
        return date;
    }

    public void setTimeslotDate(LocalDate date) {
        this.date = date;
    }

}
