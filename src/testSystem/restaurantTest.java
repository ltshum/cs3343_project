package testSystem;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.Home;
import system.Booking;
import system.Customer;
import system.Restaurant;
import system.Server;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class restaurantTest {

    // Preparation start
    InputStream systemIn = System.in;
    Restaurant restaurant;
    Scanner scanner;

    @Before
    public void setUp() throws Exception {
        restaurant = new Restaurant("username",
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

    }

    @After
    public void tearDown() throws Exception {
        scanner.close();
        System.setIn(systemIn);
    }

    public void setInput(String[] in) {
        scanner = testInput.input(in);
    }
    // Preparation end

    @Test
    public void test_edit_InvalidSelections() {
        String[] input = {"3", "J", "1", "testInvalidSelections", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals("testInvalidSelections", restaurant.getRestaurantName());
    }

    @Test
    public void test_edit_SetName() {
        String[] input = {"1", "testSetName", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetName", restaurant.getRestaurantName());
    }

    @Test
    public void test_edit_SetType() {
        String[] input = {"2", "testSetType", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetType", restaurant.getType());
    }

    @Test
    public void test_edit_SetDistrict() {
        String[] input = {"3", "testSetDistrict", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetDistrict", restaurant.getDistrict());
    }

    @Test
    public void test_edit_SetAddress() {
        String[] input = {"4", "testSetAddress", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetAddress", restaurant.getAddress());
    }

    @Test
    public void test_edit_SetPhone() {
        String[] input = {"5", "735759", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("735759", restaurant.getRestaurantContact());
    }

//	@Test
//	public void test_edit_InvalidPhone() {
//		String[] input = { "5", "testInvalidPhone", "735719", "X" };
//		setInput(input);
//		restaurant.edit(scanner);
//		System.out.println("This is the contact of the restaurant "+restaurant.getRestaurantContact());
//		assertEquals("12345678", restaurant.getRestaurantContact());
//	} 
    @Test
    public void test_edit_SetOpenTime() {
        String[] input = {"6", "11:00", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("11:00"), restaurant.getOpenTime());
    }

    @Test
    public void test_edit_InvalidOpenTimeInput() {
        String[] input = {"6", "testInvalidOpenTime", "12:00", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("12:00"), restaurant.getOpenTime());
    }

    @Test
    public void test_edit_InvalidOpenTimeLogic1() {
        String[] input = {"6", "", "13:00", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("13:00"), restaurant.getOpenTime());
    }

    @Test
    public void test_edit_InvalidOpenTimeLogic2() {
        String[] input = {"6", "23:00", "12:00", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("12:00"), restaurant.getOpenTime());
    }

    @Test
    public void test_edit_SetCloseTime() {
        String[] input = {"7", "21:00", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("21:00"), restaurant.getCloseTime());
    }

    @Test
    public void test_edit_InvalidCloseTimeInput() {
        String[] input = {"7", "testInvalidCloseTime", "22:00", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("22:00"), restaurant.getCloseTime());
    }

    @Test
    public void test_edit_InvalidCloseTimeLogic() {
        System.out.println("This is the error case");
        String[] input = {"7", "09:00", "22:00", "X"};
        setInput(input);

        restaurant.edit(scanner); // Call the edit method

        // After trying to set an invalid close time, it should remain unchanged
        System.out.println("This is the actual closing time of error case: " + restaurant.getCloseTime());
        assertEquals(LocalTime.parse("22:00"), restaurant.getCloseTime()); // Expecting the original close time
    }

    @Test
    public void test_edit_SetDuration() {
        String[] input = {"8", "70", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(70, restaurant.getSessionDuration());
    }

    @Test
    public void test_edit_InvalidDurationInput() {
        String[] input = {"8", "testInvalidDuration", "80", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(80, restaurant.getSessionDuration());
    }

    @Test
    public void test_edit_InvalidDurationLogic() {
        String[] input = {"8", "-1", "80", "X"};
        setInput(input);

        restaurant.edit(scanner);
        System.out.println("This is the error case duartion " + restaurant.getSessionDuration());
        assertEquals(80, restaurant.getSessionDuration());
    }

    @Test
    public void test_edit_SetTable() {
        String[] input = {"9", "4", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(4, restaurant.getAllTables().size());
    }

    @Test
    public void test_edit_InvalidTableInput() {
        String[] input = {"9", "testInvalidTable", "5", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(5, restaurant.getAllTables().size());
    }

    @Test
    public void test_edit_InvalidTableLogic() {
        String[] input = {"9", "-1", "6", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(6, restaurant.getAllTables().size());
    }

    @Test
    public void test_edit_deafult() {
        String[] input = {"10", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals("name", restaurant.getRestaurantName());
        assertEquals("type", restaurant.getType());
        assertEquals("address", restaurant.getAddress());
        assertEquals("address", restaurant.getAddress());
        assertEquals("12345678", restaurant.getRestaurantContact());
    }

    @Test
    public void test_edit_Format() {
        String[] input = {"HI", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals("name", restaurant.getRestaurantName());
        assertEquals("type", restaurant.getType());
        assertEquals("address", restaurant.getAddress());
        assertEquals("address", restaurant.getAddress());
        assertEquals("12345678", restaurant.getRestaurantContact());
    }

    @Test
    public void test_addComment() {
        String customerName = "User3";
        String content = "This is a great restaurant!";
        int rate = 5;
        restaurant.addComment(customerName, content, rate, LocalDate.now());

        assertEquals(3, restaurant.getAllComments().size());
        System.out.println("This is all the comment " + restaurant.getCommentString().trim());
        assertEquals("User1: Great 3.0\nUser2: Good 4.0\nUser3: This is a great restaurant! 5.0", restaurant.getCommentString().trim());

    }

    @Test
    public void test_addMultipleComments() {
        restaurant.addComment("User1", "Great service!", 4, LocalDate.now());
        restaurant.addComment("User2", "Good food!", 5, LocalDate.now());

        assertEquals(4, restaurant.getAllComments().size());
        assertEquals(4.0, restaurant.getRate(), 0.01); // Average rating should be 4.5
    }

    @Test
    public void TestsetRate() {
        System.out.println("This is the original rate " + restaurant.getRate());
        assertEquals(0.0, restaurant.getRate(), 0.01);
        restaurant.setRate(4.9f);
        assertEquals(4.9, restaurant.getRate(), 0.01);

    }

    @Test
    public void test_getPeriodComment() {
        LocalDate today = LocalDate.now();
        restaurant.addComment("User1", "Nice ambiance", 4, today.plusDays(2));
        restaurant.addComment("User2", "Will come again!", 5, today.minusDays(1));
        System.out.println("This is the size of the minusDay1 " + restaurant.getPeriodComment(today.minusDays(1), today).size());
        System.out.println("This is the size of the plueDay3 " + restaurant.getPeriodComment(today.plusDays(1), today.plusDays(3)).size());

        assertEquals(3, restaurant.getPeriodComment(today.minusDays(1), today).size());
        assertEquals(1, restaurant.getPeriodComment(today.plusDays(1), today.plusDays(3)).size()); // Should return 0
    }

    @Test
    public void TestaddBooking() {
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        // Create a booking instance
        Booking booking = new Booking(LocalDate.now(), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        Booking booking2 = new Booking(LocalDate.now(), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
        // Add the booking to the customer
        restaurant.addBooking(booking);
        restaurant.addBooking(booking2);

        // Assert that the booking was added successfully
        assertEquals(2, restaurant.getAllBookings().size());
        assertEquals(booking, restaurant.getAllBookings().get(0));
        assertEquals(booking2, restaurant.getAllBookings().get(1));
    }

    @Test
    public void TestgetPeriodBooking() {
        LocalDate today = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(today.plusDays(1), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        Booking booking2 = new Booking(today.minusDays(2), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
        Booking booking3 = new Booking(today.minusDays(1), 3, "13:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
        restaurant.addBooking(booking);
        restaurant.addBooking(booking2);
        restaurant.addBooking(booking3);
        ArrayList<Booking> compare = new ArrayList<Booking>();
        compare.add(booking2);
        compare.add(booking3);
        ArrayList<Booking> res1 = restaurant.getPeriodBooking(today, today.plusDays(2));
        ArrayList<Booking> res2 = restaurant.getPeriodBooking(today.minusDays(6), today.minusDays(1));
        assertEquals(1, res1.size());
        assertEquals(2, res2.size());
        assertEquals(booking2, res2.get(0));
        assertEquals(compare, res2);

    }

    @Test
    public void test_getPeriodBooking_OnlyFutureBookings() {
        LocalDate today = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking futureBooking = new Booking(today.plusDays(3), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        restaurant.addBooking(futureBooking);

        ArrayList<Booking> result = restaurant.getPeriodBooking(today, today.plusDays(2));
        assertEquals(0, result.size()); // Expecting no bookings in the specified range
    }

    @Test
    public void test_getPeriodBooking_OnlyPastBookings() {
        LocalDate today = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking pastBooking = new Booking(today.minusDays(4), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        restaurant.addBooking(pastBooking);

        ArrayList<Booking> result = restaurant.getPeriodBooking(today, today.plusDays(2));
        assertEquals(0, result.size()); // Expecting no bookings in the specified range
    }

    @Test
    public void test_getPeriodBooking_BookingsOnBoundaryDates() {
        LocalDate today = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking bookingOnStart = new Booking(today, 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        Booking bookingOnEnd = new Booking(today.plusDays(1), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
        restaurant.addBooking(bookingOnStart);
        restaurant.addBooking(bookingOnEnd);

        ArrayList<Booking> result = restaurant.getPeriodBooking(today, today.plusDays(1));
        assertEquals(2, result.size()); // Both bookings should be included
    }

    @Test
    public void test_getPeriodBooking_NonOverlappingDateRange() {
        LocalDate today = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(today.plusDays(1), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        restaurant.addBooking(booking);

        ArrayList<Booking> result = restaurant.getPeriodBooking(today.minusDays(2), today);
        assertEquals(0, result.size()); // Expecting no bookings as the range does not overlap
    }

    @Test
    public void test_getPeriodBooking_MultipleBookingsInDifferentRanges() {
        LocalDate today = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking1 = new Booking(today.minusDays(1), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
        Booking booking2 = new Booking(today.minusDays(2), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
        Booking booking3 = new Booking(today.plusDays(1), 3, "13:00-14:00", restaurant, customer, customer.getCustomerContact(), 2);
        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);
        restaurant.addBooking(booking3);

        ArrayList<Booking> result = restaurant.getPeriodBooking(today.minusDays(2), today);
        assertEquals(2, result.size()); // Should include booking1 and booking2
        assertEquals(booking1, result.get(0));
        assertEquals(booking2, result.get(1));
    }

}
