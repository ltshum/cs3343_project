import java.time.LocalDate;
import java.time.LocalTime;


public class Booking {
    private LocalTime startTime;// To store booking time
    private LocalTime endTime;
    private int numberOfSeats; // To store number of seats
    private LocalDate bookingdate;
    private String contactnumber;
    public Booking( LocalTime bookingTime, LocalTime bktime,int numberOfSeats,String contactnumber) {
        this.startTime = bookingTime;
        this.endTime = bktime;
        this.numberOfSeats = numberOfSeats;
        this.contactnumber=contactnumber;
        this.bookingdate = LocalDate.now();
    }
    public Booking(LocalTime bookingTime, LocalTime bktime,int numberOfSeats,LocalDate bookingdate,String contactnumber) {
        this.startTime = bookingTime;
        this.endTime = bktime;
        this.numberOfSeats = numberOfSeats;
        this.bookingdate =bookingdate;
        this.contactnumber=contactnumber;
    }

    // Method to handle booking success
    public void bookingSuccess() {
        System.out.println("Booking confirmed!");
        System.out.println("Booking Time: " + startTime+" - "+ endTime);
        System.out.println("Number of Seats: " + numberOfSeats);
        System.out.println("Date: " + bookingdate);
        System.out.println("Number: " + contactnumber);
    }

    // Add getters for the fields if needed
}