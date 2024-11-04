import java.time.LocalDate;
import java.time.LocalTime;


public class Booking {
    private LocalTime bookingTime; // To store booking time
    private int numberOfSeats; // To store number of seats
    private LocalDate bookingdate;
    private String contactnumber;
    public Booking( LocalTime bookingTime, int numberOfSeats,String contactnumber) {
        this.bookingTime = bookingTime;
        this.numberOfSeats = numberOfSeats;
        this.contactnumber=contactnumber;
        this.bookingdate = LocalDate.now();
    }
    public Booking(LocalTime bookingTime, int numberOfSeats,LocalDate bookingdate,String contactnumber) {
        this.bookingTime = bookingTime;
        this.numberOfSeats = numberOfSeats;
        this.bookingdate =bookingdate;
        this.contactnumber=contactnumber;
    }

    // Method to handle booking success
    public void bookingSuccess() {
        System.out.println("Booking confirmed!");
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Number of Seats: " + numberOfSeats);
        System.out.println("Date: " + bookingdate);
        System.out.println("Number: " + contactnumber);
    }

    // Add getters for the fields if needed
}