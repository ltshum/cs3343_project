
import java.time.LocalDate;

public class Timeslot implements Comparable<Timeslot>{



    private LocalDate date;
    private String session;
    private boolean status; // true if available, false if booked
    private String bookerName;
    private String bookerContact;
    private int pplNum;

    public Timeslot(String session) {
        this.session = session;
        this.status = true; // Initially available
        this.bookerName = null;
        this.bookerContact = null;
        this.pplNum = 0;
    }

    // Getters and setters
    public String getSession() {
        return session;
    }

    public boolean isAvailable() {
        return status;
    }

    public String getBookerName() {
        return bookerName;
    }

    public String getBookerContact() {
        return bookerContact;
    }

    public int getPplNum() {
        return pplNum;
    }

    public void book(String bookerName, String bookerContact, int pplNum) {
        this.status = false;
        this.bookerName = bookerName;
        this.bookerContact = bookerContact;
        this.pplNum = pplNum;
    }

    public void cancel() {
        this.status = true;
        this.bookerName = null;
        this.bookerContact = null;
        this.pplNum = 0;
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
