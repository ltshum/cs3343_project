package testSystem;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import system.Booking;

public class BookingTest {

    Booking booking;

    @Before
    public void setUp() {
          booking = new Booking(LocalDate.now(), 1, "12:00 - 13:00", "restaurantName", "username", "customerName", "12345678", 1);
    }

    @Test
    public void testGetRestaurantUserName() {
        assertEquals("username", booking.getRestaurantUserName());
    }

    @Test  
    public void testGetCustomerBookingData() {
        assertEquals("restaurantName: 12:00 - 13:00 1ppl\n", booking.getCustomerBookingData());
    }


    @Test
    public void testGetBookingDate() {
        assertEquals(LocalDate.now(), booking.getBookingDate());
    }

    @Test
    public void testGetCustomerName() {
        assertEquals("customerName", booking.getCustomerName());
    }

    @Test
    public void testGetBookingTableID() {
        assertEquals(1, booking.getBookingTableID());
    }

    @Test
    public void testGetBookingTimeslot() {
        assertEquals("12:00 - 13:00", booking.getBookingTimeslot());
    }
    
    @Test
    public void testGetBookingCustomerContact() {
        assertEquals("12345678", booking.getBookingCustomerContact());
    }

    @Test
    public void testGetBookingPpl() {
        assertEquals(1, booking.getBookingPpl());
    }

    @Test
    public void testHasArrived() {
        assertEquals(false, booking.hasArrived());
    }

    @Test
    public void testTakeAttendance() {
        booking.takeAttendance();
        assertEquals(true, booking.hasArrived());
    }

    @Test  
    public void testSetArrive() {
        booking.setArrive(true);
        assertEquals(true, booking.hasArrived());
    }

}
