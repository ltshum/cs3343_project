import java.time.LocalDate;
import java.time.LocalTime;


public class Booking {
    private final LocalTime startTime;// To store booking time
    private final LocalTime endTime;
    private final int numberOfSeats; // To store number of seats
    private final LocalDate bookingDate;
    private final String contactNumber;
    private final Restaurant restaurant;
    private final Customer customer;
    private boolean arrived = false;

    public Booking(LocalTime startTime, LocalTime endTime, int numberOfSeats, String contactNumber, Restaurant restaurant, Customer customer) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = LocalDate.now();
        this.contactNumber = contactNumber;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public Booking(LocalTime startTime, LocalTime endTime, int numberOfSeats, LocalDate bookingDate, String contactNumber, Restaurant restaurant, Customer customer) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = bookingDate;
        this.contactNumber = contactNumber;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    // Method to handle booking success
    public void bookingSuccess() {
        System.out.println("Booking confirmed!");
        System.out.println("Booking Time: " + startTime+" - "+ endTime);
        System.out.println("Number of Seats: " + numberOfSeats);
        System.out.println("Date: " + bookingDate);
        System.out.println("Number: " + contactNumber);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "StartTime=" + startTime +
                ",EndTime=" + endTime +
                ", numberOfSeats=" + numberOfSeats +
                ", bookingDate=" + bookingDate +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }

    // Add getters for the fields if needed
}