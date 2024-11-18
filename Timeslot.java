
import java.time.LocalDate;

public class Timeslot {

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

    public LocalDate getDate() {
        return date;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public void setBookerContact(String bookerContact) {
        this.bookerContact = bookerContact;
    }

    public void setPplNum(int pplNum) {
        this.pplNum = pplNum;
    }

}
