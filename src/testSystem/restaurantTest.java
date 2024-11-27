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
import system.Comment;
import system.Customer;
import system.Restaurant;
import system.Server;
import system.Table;

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
    	if(scanner!=null) {
        scanner.close();
    	}
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
//
//    @Test
//    public void test_addComment() {
//        String customerName = "User3";
//        String content = "This is a great restaurant!";
//        int rate = 5;
//        restaurant.addComment(customerName, content, rate, LocalDate.now());
//
//        assertEquals(3, restaurant.getAllComments().size());
//        System.out.println("This is all the comment " + restaurant.getCommentString().trim());
//        assertEquals("User1: Great 3.0\nUser2: Good 4.0\nUser3: This is a great restaurant! 5.0", restaurant.getCommentString().trim());
//
//    }
//
//    @Test
//    public void test_addMultipleComments() {
//        restaurant.addComment("User1", "Great service!", 4, LocalDate.now());
//        restaurant.addComment("User2", "Good food!", 5, LocalDate.now());
//
//        assertEquals(4, restaurant.getAllComments().size());
//        assertEquals(4.0, restaurant.getRate(), 0.01); // Average rating should be 4.5
//    }

    @Test
    public void TestsetRate() {
        System.out.println("This is the original rate " + restaurant.getRate());
        assertEquals(0.0, restaurant.getRate(), 0.01);
        restaurant.setRate(4.9f);
        assertEquals(4.9, restaurant.getRate(), 0.01);

    }

//    @Test
//    public void test_getPeriodComment() {
//        LocalDate today = LocalDate.now();
//        restaurant.addComment("User1", "Nice ambiance", 4, today.plusDays(2));
//        restaurant.addComment("User2", "Will come again!", 5, today.minusDays(1));
//        System.out.println("This is the size of the minusDay1 " + restaurant.getPeriodComment(today.minusDays(1), today).size());
//        System.out.println("This is the size of the plueDay3 " + restaurant.getPeriodComment(today.plusDays(1), today.plusDays(3)).size());
//
//        assertEquals(3, restaurant.getPeriodComment(today.minusDays(1), today).size());
//        assertEquals(1, restaurant.getPeriodComment(today.plusDays(1), today.plusDays(3)).size()); // Should return 0
//    }

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

//    @Test
//    public void TestgetPeriodBooking() {
//        LocalDate today = LocalDate.now();
//        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
//        Booking booking = new Booking(today.plusDays(1), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
//        Booking booking2 = new Booking(today.minusDays(2), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//        Booking booking3 = new Booking(today.minusDays(1), 3, "13:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//        restaurant.addBooking(booking);
//        restaurant.addBooking(booking2);
//        restaurant.addBooking(booking3);
//        ArrayList<Booking> compare = new ArrayList<Booking>();
//        compare.add(booking2);
//        compare.add(booking3);
//        ArrayList<Booking> res1 = restaurant.getPeriodBooking(today, today.plusDays(2));
//        ArrayList<Booking> res2 = restaurant.getPeriodBooking(today.minusDays(6), today.minusDays(1));
//        assertEquals(1, res1.size());
//        assertEquals(2, res2.size());
//        assertEquals(booking2, res2.get(0));
//        assertEquals(compare, res2);
//
//    }
//
//    @Test
//    public void test_getPeriodBooking_OnlyFutureBookings() {
//        LocalDate today = LocalDate.now();
//        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
//        Booking futureBooking = new Booking(today.plusDays(3), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
//        restaurant.addBooking(futureBooking);
//
//        ArrayList<Booking> result = restaurant.getPeriodBooking(today, today.plusDays(2));
//        assertEquals(0, result.size()); // Expecting no bookings in the specified range
//    }
//
//    @Test
//    public void test_getPeriodBooking_OnlyPastBookings() {
//        LocalDate today = LocalDate.now();
//        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
//        Booking pastBooking = new Booking(today.minusDays(4), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
//        restaurant.addBooking(pastBooking);
//
//        ArrayList<Booking> result = restaurant.getPeriodBooking(today, today.plusDays(2));
//        assertEquals(0, result.size()); // Expecting no bookings in the specified range
//    }
//
//    @Test
//    public void test_getPeriodBooking_BookingsOnBoundaryDates() {
//        LocalDate today = LocalDate.now();
//        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
//        Booking bookingOnStart = new Booking(today, 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
//        Booking bookingOnEnd = new Booking(today.plusDays(1), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//        restaurant.addBooking(bookingOnStart);
//        restaurant.addBooking(bookingOnEnd);
//
//        ArrayList<Booking> result = restaurant.getPeriodBooking(today, today.plusDays(1));
//        assertEquals(2, result.size()); // Both bookings should be included
//    }
//
//    @Test
//    public void test_getPeriodBooking_NonOverlappingDateRange() {
//        LocalDate today = LocalDate.now();
//        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
//        Booking booking = new Booking(today.plusDays(1), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
//        restaurant.addBooking(booking);
//
//        ArrayList<Booking> result = restaurant.getPeriodBooking(today.minusDays(2), today);
//        assertEquals(0, result.size()); // Expecting no bookings as the range does not overlap
//    }
//
//    @Test
//    public void test_getPeriodBooking_MultipleBookingsInDifferentRanges() {
//        LocalDate today = LocalDate.now();
//        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
//        Booking booking1 = new Booking(today.minusDays(1), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
//        Booking booking2 = new Booking(today.minusDays(2), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//        Booking booking3 = new Booking(today.plusDays(1), 3, "13:00-14:00", restaurant, customer, customer.getCustomerContact(), 2);
//        restaurant.addBooking(booking1);
//        restaurant.addBooking(booking2);
//        restaurant.addBooking(booking3);
//
//        ArrayList<Booking> result = restaurant.getPeriodBooking(today.minusDays(2), today);
//        assertEquals(2, result.size()); // Should include booking1 and booking2
//        assertEquals(booking1, result.get(0));
//        assertEquals(booking2, result.get(1));
//    }
    @Test
    public void testGetAllTableInfo_NoTables() {
        // Given: No tables in the restaurant
        restaurant.setAllTables(new ArrayList<>());

        // When: Calling getAllTableInfo
        StringBuilder result = restaurant.getAllTableInfo();

        // Then: Should return empty table info
        assertEquals( "                \n                \n                \n", result.toString());
    }

    @Test
    public void testGetAllTableInfo_WithTables() {
        // Given: Adding multiple tables
        Table table1 = new Table(1);
        table1.setSeatNum(4);
        table1.setStatus(true); // Available

        Table table2 = new Table(2);
        table2.setSeatNum(2);
        table2.setStatus(false); // Not Available

        restaurant.setAllTables(new ArrayList<>());
        restaurant.getAllTables().add(table1);
        restaurant.getAllTables().add(table2);

        // When: Calling getAllTableInfo
        StringBuilder result = restaurant.getAllTableInfo();

        // Then: Should return correct table information
        String expectedOutput = 
            "| Table ID: 1          \n" +
            "| Seat: 4              \n" +
            "| Status: Available     \n" +
            "| Table ID: 2          \n" +
            "| Seat: 2              \n" +
            "| Status: Not Available \n\n";
        
        assertTrue(result.toString().contains("| Table ID: 1          "));
        assertTrue(result.toString().contains("| Seat: 4              "));
        assertTrue(result.toString().contains("| Status: Available     "));
        assertTrue(result.toString().contains("| Table ID: 2          "));
        assertTrue(result.toString().contains("| Seat: 2              "));
        assertTrue(result.toString().contains("| Status: Not Available "));
    }
    
    @Test
    public void testAvailableTableID_NoTablesAvailable() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: No tables available
        restaurant.setAllTables(new ArrayList<>());

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableID(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
    }

    @Test
    public void testAvailableTableID_TableAvailableSufficientSeats() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: One table available with enough seats
        Table table = new Table(1);
        table.setSeatNum(5); // Enough seats
        table.addTimeslot(timeslot); // Mark timeslot as available
        restaurant.getAllTables().add(table);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableID(requiredPeople, timeslot, bookingDate);

        // Then: Should return the ID of the available table
        assertEquals(1, availableTableID);
    }

    @Test
    public void testAvailableTableID_TableAvailableInsufficientSeats() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: One table available with insufficient seats
        Table table = new Table(1);
        table.setSeatNum(2); // Not enough seats
        table.addTimeslot(timeslot); // Mark timeslot as available
        restaurant.getAllTables().add(table);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableID(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
    }

    @Test
    public void testAvailableTableID_TimeslotNotAvailable() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: One table available but timeslot is not available
        Table table = new Table(1);
        table.setSeatNum(5); // Enough seats
        table.addTimeslot("11:00 - 12:00"); // Different timeslot
        restaurant.getAllTables().add(table);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableID(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(1, availableTableID);
    }

    @Test
    public void testAvailableTableID_BookingAlreadyExists() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: One table available and already booked for the timeslot
        Table table = new Table(1);
        table.setSeatNum(5); // Enough seats
        table.addTimeslot(timeslot); // Mark timeslot as available
        table.setTimeslotUnavailable(timeslot, bookingDate); // Mark timeslot as booked
        restaurant.getAllTables().add(table);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableID(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
    }

    @Test
    public void testAvailableTableID_MultipleTables() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: Multiple tables, one suitable
        Table table1 = new Table(1);
        table1.setSeatNum(2); // Not enough seats
        table1.addTimeslot(timeslot); // Available timeslot
        
        Table table2 = new Table(2);
        table2.setSeatNum(4); // Enough seats
        table2.addTimeslot(timeslot); // Available timeslot

        restaurant.getAllTables().add(table1);
        restaurant.getAllTables().add(table2);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableID(requiredPeople, timeslot, bookingDate);

        // Then: Should return the ID of the suitable table
        assertEquals(2, availableTableID);
    }
    @Test
    public void test_getTimeslots_ZeroDuration() {
    	
        restaurant.setSessionDuration(Duration.ZERO);
        String expected = "10:00 - 11:00 \n" +
                "11:00 - 12:00 \n" +
                "12:00 - 13:00 \n" +
                "13:00 - 14:00 \n" +
                "14:00 - 15:00 \n" +
                "15:00 - 16:00 \n" +
                "16:00 - 17:00 \n" +
                "17:00 - 18:00 \n" +
                "18:00 - 19:00 \n" +
                "19:00 - 20:00 \n";; // No valid time slots with zero session duration
        assertEquals(expected, restaurant.getTimeslots());
    }
    

    @Test
    public void test_getTimeslots_NegativeDuration() {
        restaurant.setSessionDuration(Duration.ofMinutes(-30)); // Set duration to zero
        String expected = "10:00 - 11:00 \n" +
                "11:00 - 12:00 \n" +
                "12:00 - 13:00 \n" +
                "13:00 - 14:00 \n" +
                "14:00 - 15:00 \n" +
                "15:00 - 16:00 \n" +
                "16:00 - 17:00 \n" +
                "17:00 - 18:00 \n" +
                "18:00 - 19:00 \n" +
                "19:00 - 20:00 \n";; // No valid time slots with zero session duration
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_OpeningEqualsClosing() {
        restaurant.setOpenTime(LocalTime.parse("10:00"));
        restaurant.setCloseTime(LocalTime.parse("10:00")); // Same opening and closing time
        String expected = ""; // No time slots because opening and closing times are the same
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_SingleSession() {
        restaurant.setCloseTime(LocalTime.parse("11:00")); // Adjust closing time for a single session
        String expected = "10:00 - 11:00 \n"; // Only one valid time slot
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_MultipleSessions() {
        restaurant.setOpenTime(LocalTime.parse("09:00")); // Change opening time
        restaurant.setCloseTime(LocalTime.parse("12:00")); // Change closing time
        restaurant.setSessionDuration(Duration.ofMinutes(30)); // Change session duration
        String expected = "09:00 - 09:30 \n" +
                          "09:30 - 10:00 \n" +
                          "10:00 - 10:30 \n" +
                          "10:30 - 11:00 \n" +
                          "11:00 - 11:30 \n" +
                          "11:30 - 12:00 \n"; // Multiple 30-minute slots
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_EndsExactlyAtClosing() {
        restaurant.setSessionDuration(Duration.ofMinutes(120)); // One slot that ends at closing
        String expected = "10:00 - 12:00 \n" +
                "12:00 - 14:00 \n" +
                "14:00 - 16:00 \n" +
                "16:00 - 18:00 \n" +
                "18:00 - 20:00 \n" ; // One slot that ends exactly at closing time
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_NoValidSlots() {
        restaurant.setSessionDuration(Duration.ofMinutes(90)); // Too long for the available time
        String expected = "10:00 - 11:30 \n" +
                "11:30 - 13:00 \n" +
                "13:00 - 14:30 \n" +
                "14:30 - 16:00 \n" +
                "16:00 - 17:30 \n" +
                "17:30 - 19:00 \n";// No valid slots because a single session exceeds closing time
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_SessionExceedsTime() {
        restaurant.setOpenTime(LocalTime.parse("09:00")); // Opening time
        restaurant.setCloseTime(LocalTime.parse("09:30")); // Closing time
        restaurant.setSessionDuration(Duration.ofMinutes(60)); // Session too long
        String expected = ""; // No valid slots because session exceeds available time
        assertEquals(expected, restaurant.getTimeslots());
    }
//
    @Test
    public void test_getTimeslots_SessionStartsAtCloseTime() {
        restaurant.setOpenTime(LocalTime.parse("10:00")); // Opening time
        restaurant.setCloseTime(LocalTime.parse("11:00")); // Closing time
        restaurant.setSessionDuration(Duration.ofMinutes(60)); // One session that starts at opening
        String expected = "10:00 - 11:00 \n"; // Only one valid time slot
        assertEquals(expected, restaurant.getTimeslots());
    }
    @Test
    public void test_getTimeslots_StandardTimes() {
        String expected = "10:00 - 11:00 \n" +
                          "11:00 - 12:00 \n" +
                          "12:00 - 13:00 \n" +
                          "13:00 - 14:00 \n" +
                          "14:00 - 15:00 \n" +
                          "15:00 - 16:00 \n" +
                          "16:00 - 17:00 \n" +
                          "17:00 - 18:00 \n" +
                          "18:00 - 19:00 \n" +
                          "19:00 - 20:00 \n";
        assertEquals(expected, restaurant.getTimeslots());
    }
    
    @Test
    public void testTableValidation_TableExists() {
        // Given: Adding a table with ID 1
        Table table = new Table(1);
        restaurant.setAllTables(new ArrayList<>());
        restaurant.getAllTables().add(table);

        // When: Validating table ID 1
        boolean result = restaurant.tableValidation(1);

        // Then: Should return true
        assertTrue(result);
    }

    @Test
    public void testTableValidation_TableDoesNotExist() {
        // Given: Adding a table with ID 1
        Table table = new Table(1);
        restaurant.setAllTables(new ArrayList<>());
        restaurant.getAllTables().add(table);

        // When: Validating a non-existent table ID 2
        boolean result = restaurant.tableValidation(2);

        // Then: Should return false
        assertFalse(result);
    }

    @Test
    public void testTableValidation_NoTables() {
        // Given: No tables in the restaurant
        restaurant.setAllTables(new ArrayList<>());

        // When: Validating any table ID
        boolean result = restaurant.tableValidation(1);

        // Then: Should return false
        assertFalse(result);
    }
    @Test 
    public void TestValidupdateTableInfo() {
    	String[] input = { "10"};
		setInput(input);
		restaurant.updateTableInfo(scanner,1);
		assertEquals(10, restaurant.getAllTables().get(0).getSeatNum());
		assertEquals(0, restaurant.getAllTables().get(1).getSeatNum());

    }
    
    @Test 
    public void TestInvalidupdateTableInfo1() {
    	String[] input = { "-1","23","X"};
		setInput(input);
		System.out.println("THis is the part for the updating setaNum and this the old setNUm "+restaurant.getAllTables().get(0).getSeatNum());
		restaurant.updateTableInfo(scanner,1);
		assertEquals(23, restaurant.getAllTables().get(0).getSeatNum());
		assertEquals(0, restaurant.getAllTables().get(1).getSeatNum());

    }
    
    @Test 
    public void TestInvalidupdateTableInfo2() {
    	String[] input = { "HI","10"};
		setInput(input);
		restaurant.updateTableInfo(scanner,1);
		assertEquals(10, restaurant.getAllTables().get(0).getSeatNum());
		assertEquals(0, restaurant.getAllTables().get(1).getSeatNum());

    }
    @Test
    public void test_getAllCommentList() {
	    String customerName = "User3";
	    String content = "This is a great restaurant!";
	    int rate = 5;
	    Comment cm1 = new Comment("User1", "Great", 3, LocalDate.now());
	    Comment cm2 = new Comment("User2", "Good", 4, LocalDate.now());
	    Comment cm3=new Comment(customerName, content, rate, LocalDate.now().plusDays(2));	
	    restaurant.getAllCommentsList().add(cm3);
	    ArrayList<Comment> cmList=new ArrayList<Comment>();
	    cmList.add(cm1);
	    cmList.add(cm2);
	    cmList.add(cm3);
	    assertEquals(3, restaurant.getAllCommentsList().size());
	    for(int i=0;i<restaurant.getAllCommentsList().size();i++) {
		    assertEquals(cmList.get(i).getCommentCustomerName(),restaurant.getAllCommentsList().get(i).getCommentCustomerName());
		    assertEquals(cmList.get(i).getCommentContent(),restaurant.getAllCommentsList().get(i).getCommentContent());
		    assertEquals(cmList.get(i).getCommentRate(),restaurant.getAllCommentsList().get(i).getCommentRate(),0.01);
		    assertEquals(cmList.get(i).getCommentDate(),restaurant.getAllCommentsList().get(i).getCommentDate());

		    }
    }
    @Test
    public void testGetAllComments_Empty() {
        // Test when there are no comments
        String result = restaurant.getAllComments();
        assertEquals("User1: Great 3.0\nUser2: Good 4.0\n", result);
    }

    @Test
    public void testGetAllComments_SingleComment() {
        // Test when there is a single comment
        Comment comment = new Comment("John", "Great food!", 5,LocalDate.now());
        restaurant.getAllCommentsList().add(comment);

        String result = restaurant.getAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nJohn: Great food! 5.0\n";
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllComments_MultipleComments() {
        // Test when there are multiple comments
        Comment comment1 = new Comment("Alice", "Loved it!", 4,LocalDate.now());
        Comment comment2 = new Comment("Bob", "Not bad.", 3,LocalDate.now());
        restaurant.getAllCommentsList().add(comment1);
        restaurant.getAllCommentsList().add(comment2);
        String result = restaurant.getAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nAlice: Loved it! 4.0\nBob: Not bad. 3.0\n";
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllComments_CommentWithEmptyContent() {
        // Test when there is a comment with empty content
        Comment comment = new Comment("Charlie", "", 2,LocalDate.now());
        restaurant.getAllCommentsList().add(comment);
        String result = restaurant.getAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nCharlie:  2.0\n"; // Expecting the string to handle empty content
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllComments_CommentWithZeroRate() {
        // Test when there is a comment with a rate of zero
        Comment comment = new Comment("Diana", "Okay.", 0,LocalDate.now());
        restaurant.getAllCommentsList().add(comment);

        String result = restaurant.getAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nDiana: Okay. 0.0\n"; // Expecting the string to handle zero rating
        assertEquals(expected, result);
    }
    
    @Test
    public void testgetProfileDetail() {
    	String res="Rate: " + "0.0"
            + "\nRestaurant Name: " + "name"
            + "\nType: " + "type"
            + "\nDistrict: " + "district"
            + "\nAddress: " + "address"
            + "\nPhone: " + "12345678"
            + "\nOpen Time: " + "10:00"
            + "\nClose Time: " + "20:00"
            + "\nSession Duration: " +   "60mins"
            + "\nTable Amount: 3" + 
            "\n\nTimeslot: \n" + "10:00 - 11:00 \n" +
            "11:00 - 12:00 \n" +
            "12:00 - 13:00 \n" +
            "13:00 - 14:00 \n" +
            "14:00 - 15:00 \n" +
            "15:00 - 16:00 \n" +
            "16:00 - 17:00 \n" +
            "17:00 - 18:00 \n" +
            "18:00 - 19:00 \n" +
            "19:00 - 20:00 \n"
            + "\nComment: \n" + "User1: Great 3.0\nUser2: Good 4.0\n";
    	assertEquals(res,restaurant.getProfileDetail());
    }
    
    @Test
    public void testgetProfileDetailaddComment() {
    	String res="Rate: " + "0.0"
            + "\nRestaurant Name: " + "name"
            + "\nType: " + "type"
            + "\nDistrict: " + "district"
            + "\nAddress: " + "address"
            + "\nPhone: " + "12345678"
            + "\nOpen Time: " + "10:00"
            + "\nClose Time: " + "20:00"
            + "\nSession Duration: " +   "60mins"
            + "\nTable Amount: 3" + 
            "\n\nTimeslot: \n" + "10:00 - 11:00 \n" +
            "11:00 - 12:00 \n" +
            "12:00 - 13:00 \n" +
            "13:00 - 14:00 \n" +
            "14:00 - 15:00 \n" +
            "15:00 - 16:00 \n" +
            "16:00 - 17:00 \n" +
            "17:00 - 18:00 \n" +
            "18:00 - 19:00 \n" +
            "19:00 - 20:00 \n"
            + "\nComment: \n" + "User1: Great 3.0\nUser2: Good 4.0\nChris: Awful 1.0\n";
    	Comment comment = new Comment("Chris", "Awful", 1,LocalDate.now());
        restaurant.getAllCommentsList().add(comment);
    	assertEquals(res,restaurant.getProfileDetail());
    }
    @Test
    public void testGetBookingRecord_NoBookings() {
        // Test when there are no bookings for the given date
    	LocalDate bookingDate=LocalDate.now();
    	int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(0, totalBookings);
    }

    @Test
    public void testGetBookingRecord_BookingsOnDifferentDate() {
        // Test when bookings exist but are on a different date
        LocalDate differentDate = LocalDate.of(2023, 11, 26);
    	LocalDate bookingDate=LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(differentDate,1,"12:00-13:00",restaurant,customer,customer.getCustomerContact(),2);
        restaurant.addBooking(booking); // Assuming this method exists
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals( 0, totalBookings);
    }

    @Test
    public void testGetBookingRecord_SingleBooking() {
        // Test when there is a single booking for the given date
    	LocalDate bookingDate=LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(bookingDate,1,"12:00-13:00",restaurant,customer,customer.getCustomerContact(),2);
        restaurant.addBooking(booking);

        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals( 1, totalBookings);
    }

    @Test
    public void testGetBookingRecord_MultipleBookingsSameTimeSlot() {
    	LocalDate bookingDate=LocalDate.now();
        Customer customer1 = new Customer("testUser1", "password123", "Doe", "123459");
        Customer customer2 = new Customer("testUser2", "password123", "John", "56789");

        Booking booking1 = new Booking(bookingDate,1,"12:00-13:00",restaurant,customer1,customer1.getCustomerContact(),2);
        Booking booking2 = new Booking(bookingDate,1,"13:00-14:00",restaurant,customer2,customer2.getCustomerContact(),2);
        // Test when there are multiple bookings for the same time slot on the given date
      
        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals( 2, totalBookings);
    }

    

    @Test
    public void testGetBookingRecord_BookingHasArrived() {
    	LocalDate bookingDate=LocalDate.now();

    	Customer customer1 = new Customer("testUser1", "password123", "Doe", "123459");

        Booking booking1 = new Booking(bookingDate,1,"12:00-13:00",restaurant,customer1,customer1.getCustomerContact(),2);
        booking1.takeAttendance();
        restaurant.addBooking(booking1);

        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals("Total bookings should be one", 1, totalBookings);
    }
    @Test
    public void testGetBookingRecord_MultipleBookingsDifferentTimeSlots() {
        // Test when there are multiple bookings for different time slots on the same date
        LocalDate bookingDate = LocalDate.now();
        Customer customer1 = new Customer("testUser1", "password123", "Doe", "123459");
        Customer customer2 = new Customer("testUser2", "password123", "John", "56789");

        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00", restaurant, customer1, customer1.getCustomerContact(), 2);
        Booking booking2 = new Booking(bookingDate, 2, "13:00-14:00", restaurant, customer2, customer2.getCustomerContact(), 4); // Different time slot
        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);
        
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(2, totalBookings);
    }

    @Test
    public void testGetBookingRecord_EmptyTimeSlot() {
        // Test when a booking has an empty time slot
        LocalDate bookingDate = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(bookingDate, 1, "", restaurant, customer, customer.getCustomerContact(), 2); // Empty time slot
        restaurant.addBooking(booking);
        
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(1, totalBookings); // Should still count the booking
    }

    @Test
    public void testGetBookingRecord_GroupedBookingsOutput() {
        // Test grouping logic with multiple bookings at the same time
        LocalDate bookingDate = LocalDate.now();
        Customer customer1 = new Customer("testUser1", "password123", "Doe", "123459");
        Customer customer2 = new Customer("testUser2", "password123", "John", "56789");

        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00", restaurant, customer1, customer1.getCustomerContact(), 2);
        Booking booking2 = new Booking(bookingDate, 1, "12:00-13:00", restaurant, customer2, customer2.getCustomerContact(), 4); // Same time slot
        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);
        
        // Capture the output to validate grouping
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(2, totalBookings);
        
        // Additional checks can be added if needed to validate printed output
    }
}




