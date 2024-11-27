package testSystem;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import system.Booking;
import system.Customer;
import system.Restaurant;

public class BookingTest {

    Booking booking1;
    Booking booking2;
    Customer customer1;
    Customer customer2;
    Restaurant restaurant1;
    Restaurant restaurant2;

    @Before
    public void setUp() {
        customer1 = new Customer("testUser1", "password123", "John Doe", "12345678");
        restaurant1 = new Restaurant("username2", 
									"password", 
									"name", 
									"type", 
									"district", 
									"address", 
									"12345678", 
									LocalTime.parse("10:00"), 
									LocalTime.parse("20:00"), 
									Duration.ofMinutes(60),
									3);
        booking1 = new Booking(LocalDate.now(), 1, "12:00 - 13:00", restaurant1 ,customer1, "12345678", 1);

        customer2 = new Customer("testUser2", "password123", "John Doe", "87654321");
        restaurant2 = new Restaurant("username2", 
                                    "password", 
                                    "name", 
                                    "type", 
                                    "district", 
                                    "address", 
                                    "12345678", 
                                    LocalTime.parse("10:00"), 
                                    LocalTime.parse("20:00"), 
                                    Duration.ofMinutes(60),
                                    3);
        booking2 = new Booking(LocalDate.parse("2024-11-27"), 2, "13:00 - 14:00", restaurant2, customer2, "87654321", 2);            
    }

    @Test
    public void testGetBookingDate() {
        assertEquals(LocalDate.now(), booking1.getBookingDate());
        assertEquals(LocalDate.parse("2024-11-27"), booking2.getBookingDate());
    }

    @Test
    public void testGetTableID() {
        assertEquals(1, booking1.getBookingTableID());
        assertEquals(2, booking2.getBookingTableID());
    }

    @Test
    public void testGetTimeslot() {
        assertEquals("12:00 - 13:00", booking1.getBookingTimeslot());
        assertEquals("13:00 - 14:00", booking2.getBookingTimeslot());
    }

    @Test
    public void testGetRestaurant() {
        assertEquals(restaurant1, booking1.getBookingRestaurant());
        assertEquals(restaurant2, booking2.getBookingRestaurant());
    }

    @Test  
    public void testGetCustomer() {
        assertEquals(customer1, booking1.getBookingCustomer());
        assertEquals(customer2, booking2.getBookingCustomer());
    }

    @Test
    public void testGetCustomerContact() {
        assertEquals("12345678", booking1.getBookingCustomerContact());
        assertEquals("87654321", booking2.getBookingCustomerContact());
    }

    @Test
    public void testGetPpl() {
        assertEquals(1, booking1.getBookingPpl());
        assertEquals(2, booking2.getBookingPpl());
    }

    @Test
    public void testHasArrived() {
        assertEquals(false, booking1.hasArrived());
        assertEquals(false, booking2.hasArrived());
    }

    @Test
    public void testTakeAttendance() {
        booking1.takeAttendance();
        assertEquals(true, booking1.hasArrived());
        booking2.takeAttendance();
        assertEquals(true, booking2.hasArrived());
    }

}
