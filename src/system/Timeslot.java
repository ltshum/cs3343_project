package system;

import java.time.LocalDate;

public class Timeslot implements Comparable<Timeslot>{

    private LocalDate date;
    private String session;

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

    @Override
    public int compareTo(Timeslot other){
        if (date != other.date){
            return date.compareTo(other.date);
        }
        String sessionProcessed = session.replace("(", "").replace(")", "");
        String[] times = sessionProcessed.split("-");
        for (int i = 0; i < times[0].length(); i++){
            if (times[0].charAt(i) != times[1].charAt(i)){
                return Integer.compare(times[0].charAt(i), times[1].charAt(i));
            }
        }
        return 0;
    }
}
