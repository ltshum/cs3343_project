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

public class RestaurantTest {

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
    public void testtableValidation() {
    	assertTrue(restaurant.tableValidationInRestaurant(1));
    	assertFalse(restaurant.tableValidationInRestaurant(5));
    }
    
    @Test
    public void test_edit_InvalidSelections() {
        String[] input = {"3","testInvalidSelections", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals("testInvalidSelections", restaurant.getRestaurantDistrict());
    }

    @Test
    public void test_edit_SetName() {
        String[] input = {"1", "testSetName", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetName", restaurant.getAccountName());
    }

    @Test
    public void test_edit_SetType() {
        String[] input = {"2", "testSetType", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetType", restaurant.getRestaurantType());
    }

    

    @Test
    public void test_edit_SetAddress() {
        String[] input = {"4", "testSetAddress", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("testSetAddress", restaurant.getRestaurantAddress());
    }

    @Test
    public void test_edit_SetPhone() {
        String[] input = {"5", "735759", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals("735759", restaurant.getAccountContact());
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
        assertEquals(LocalTime.parse("11:00"), restaurant.getRestaurantOpenTime());
    }

    @Test
    public void test_edit_InvalidOpenTimeInput() {
        String[] input = {"6", "testInvalidOpenTime", "12:00", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("12:00"), restaurant.getRestaurantOpenTime());
    }

    @Test
    public void test_edit_InvalidOpenTimeLogic() {
        String[] input = {"6", "23:00", "12:00", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("12:00"), restaurant.getRestaurantOpenTime());
    }

    @Test
    public void test_edit_SetCloseTime() {
        String[] input = {"7", "21:00", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("21:00"), restaurant.getRestaurantCloseTime());
    }

    @Test
    public void test_edit_InvalidCloseTimeInput() {
        String[] input = {"7", "testInvalidCloseTime", "22:00", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(LocalTime.parse("22:00"), restaurant.getRestaurantCloseTime());
    }

    @Test
    public void test_edit_InvalidCloseTimeLogic() {
        System.out.println("This is the error case");
        String[] input = {"7", "09:00", "22:00", "X"};
        setInput(input);
        restaurant.edit(scanner); // Call the edit method
        // After trying to set an invalid close time, it should remain unchanged
        System.out.println("This is the actual closing time of error case: " + restaurant.getRestaurantCloseTime());
        assertEquals(LocalTime.parse("22:00"), restaurant.getRestaurantCloseTime()); // Expecting the original close time
    }

    @Test
    public void test_edit_SetDuration() {
        String[] input = {"8", "70", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(70, restaurant.getRestaurantSessionDuration());
    }

    @Test
    public void test_edit_InvalidDurationInput() {
        String[] input = {"8", "testInvalidDuration", "80", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(80, restaurant.getRestaurantSessionDuration());
    }

    @Test
    public void test_edit_InvalidDurationLogic() {
        String[] input = {"8", "-1", "80", "X"};
        setInput(input);

        restaurant.edit(scanner);
        System.out.println("This is the error case duartion " + restaurant.getRestaurantSessionDuration());
        assertEquals(80, restaurant.getRestaurantSessionDuration());
    }

    @Test
    public void test_edit_SetTable() {
        String[] input = {"9", "4", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(4, restaurant.getRestaurantAllTables().size());
    }

    @Test
    public void test_edit_InvalidTableInput() {
        String[] input = {"9", "testInvalidTable", "5", "X"};
        setInput(input);

        restaurant.edit(scanner);
        assertEquals(5, restaurant.getRestaurantAllTables().size());
    }

    @Test
    public void test_edit_InvalidTableLogic() {
        String[] input = {"9", "-1", "6", "X"};
        setInput(input);
        restaurant.edit(scanner);
        assertEquals(6, restaurant.getRestaurantAllTables().size());
    }
    @Test
    public void test_edit_Invalidformat() {
        String[] input = {"hi", "X"};
        setInput(input);
        restaurant.edit(scanner);
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
        assertEquals(res, restaurant.getProfileDetail());
    }
    @Test
    public void test_edit_Invalid() {
        String[] input = {"10", "X"};
        setInput(input);
        restaurant.edit(scanner);
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
        assertEquals(res, restaurant.getProfileDetail());
    }

    @Test
    public void testgetRestaurantLogRate(){
        float temp=0.0f;
        assertEquals(temp,restaurant.getRestaurantLogRate(),0.01);;
    }

    @Test
    public void testgetRestaurantAllCommentsList(){
        ArrayList<Comment> temp=new  ArrayList<Comment>();
        Comment cm1 = new Comment("User1", "Great", 3, LocalDate.now());
        Comment cm2 = new Comment("User2", "Good", 4, LocalDate.now());
        temp.add(cm1);
        temp.add(cm2);
        assertEquals(temp.size(),restaurant.getRestaurantAllCommentsList().size());
        assertEquals(temp.get(0).getCommentCustomerName(),restaurant.getRestaurantAllCommentsList().get(0).getCommentCustomerName());
    }

    @Test
    public void testsetRestaurantSessionDuration(){
        Duration temp=Duration.ofMinutes(30);
        restaurant.setRestaurantSessionDuration(temp);
        assertEquals(30,restaurant.getRestaurantSessionDuration());
        temp=Duration.ofMinutes(0);
        restaurant.setRestaurantSessionDuration(temp);
        assertNotEquals(0,restaurant.getRestaurantSessionDuration());
        temp=Duration.ofMinutes(-1);
        restaurant.setRestaurantSessionDuration(temp);
        assertNotEquals(-1,restaurant.getRestaurantSessionDuration());
    }
    @Test
    public void testsetRestaurantAllTables(){}

//    @Test
//    public void test_edit_deafult() {
//        String[] input = {"10", "X"};
//        setInput(input);
//        restaurant.edit(scanner);
//        assertEquals("name", restaurant.getRestaurantName());
//        assertEquals("type", restaurant.getType());
//        assertEquals("address", restaurant.getAddress());
//        assertEquals("address", restaurant.getAddress());
//        assertEquals("12345678", restaurant.getRestaurantContact());
//    }

//    @Test
//    public void test_edit_Format() {
//        String[] input = {"HI", "X"};
//        setInput(input);
//        restaurant.edit(scanner);
//        assertEquals("name", restaurant.getRestaurantName());
//        assertEquals("type", restaurant.getType());
//        assertEquals("address", restaurant.getAddress());
//        assertEquals("address", restaurant.getAddress());
//        assertEquals("12345678", restaurant.getRestaurantContact());
//    }
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
        System.out.println("This is the original rate " + restaurant.getRestaurantRate());
        assertEquals(0.0, restaurant.getRestaurantRate(), 0.01);
        restaurant.setRestaurantRate(4.9f);
        assertEquals(4.9, restaurant.getRestaurantRate(), 0.01);

    }
    
    @Test
    public void testupdateRestaurantTableInfo() {
    	   String[] input = {"8", "X"};
           setInput(input);
           restaurant.updateRestaurantTableInfo(scanner,1);
           assertEquals(8,restaurant.getRestaurantAllTables().get(0).getSeatNum());
           String[] input2 = {"-1","10", "X"};
           setInput(input2);
           restaurant.updateRestaurantTableInfo(scanner,1);
           assertEquals(10,restaurant.getRestaurantAllTables().get(0).getSeatNum());
           String[] input3 = {"Hi","10", "X"};
           setInput(input3);
           restaurant.updateRestaurantTableInfo(scanner,1);
           assertEquals(10,restaurant.getRestaurantAllTables().get(0).getSeatNum());
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
        Booking booking = new Booking(LocalDate.now(), 1, "11:00-12:00", restaurant.getAccountName(),restaurant.getAccountUserName() ,customer.getAccountName(), customer.getAccountContact(), 2);
        Booking booking2 = new Booking(LocalDate.now(), 1, "12:00-13:00", restaurant.getAccountName(),restaurant.getAccountUserName() ,customer.getAccountName(), customer.getAccountContact(), 2);
        // Add the booking to the customer
        restaurant.addBooking(booking);
        restaurant.addBooking(booking2);

        // Assert that the booking was added successfully
        assertEquals(2, restaurant.getAllBookings().size());
        assertEquals(booking, restaurant.getAllBookings().get(0));
        assertEquals(booking2, restaurant.getAllBookings().get(1));
    }
    
    @Test
    public void testaddCommentInRestaurant() {
    	Comment cm=new Comment("User3","Awful",1,LocalDate.now());
    	restaurant.addCommentInRestaurant(cm);
    }
    
    @Test
    public void testGetRestaurantAllTableInfo() {
        // Expected output string
        StringBuilder expected = new StringBuilder();
        expected.append("                | Table ID: 1             | Table ID: 2             | Table ID: 3             \n");
        expected.append("                | Seat: 0                 | Seat: 0                 | Seat: 0                 \n");

        // Get actual output from the method
        StringBuilder actual = restaurant.getRestaurantAllTableInfo();

        // Assert that the expected output matches the actual output
        assertEquals(expected.toString(), actual.toString());
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
        restaurant.setRestaurantAllTables(new ArrayList<>());

        // When: Calling getAllTableInfo
        StringBuilder result = restaurant.getRestaurantAllTableInfo();

        // Then: Should return empty table info
        assertEquals( "                \n                \n", result.toString());
    }

    @Test
    public void testGetAllTableInfo_WithTables() {
        // Given: Adding multiple tables
        Table table1 = new Table(1);
        table1.setSeatNum(4);
//        table1.setStatus(true); 

        Table table2 = new Table(2);
        table2.setSeatNum(2);
//        table2.setStatus(false); // Not Available

        restaurant.setRestaurantAllTables(new ArrayList<>());
        restaurant.getRestaurantAllTables().add(table1);
        restaurant.getRestaurantAllTables().add(table2);

        // When: Calling getAllTableInfo
        StringBuilder result = restaurant.getRestaurantAllTableInfo();

        // Then: Should return correct table information
      
        
        assertTrue(result.toString().contains("| Table ID: 1          "));
        assertTrue(result.toString().contains("| Seat: 4              "));
//        assertTrue(result.toString().contains("| Status: Available     "));
        assertTrue(result.toString().contains("| Table ID: 2          "));
        assertTrue(result.toString().contains("| Seat: 2              "));
//        assertTrue(result.toString().contains("| Status: Not Available "));
    }
    
    @Test
    public void testAvailableTableID_NoTablesAvailable() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: No tables available
//        restaurant.setAllTables(new ArrayList<>());

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
    }
    
    @Test
    public void testAvailableTableID_MultipleTables1() {
        // Given: Multiple tables, one suitable
        Table table1 = new Table(1);
        table1.setSeatNum(2); // Not enough seats
        table1.addTimeslot("10:00 - 11:00");
        restaurant.getRestaurantAllTables().add(table1);
        
        Table table2 = new Table(2);
        table2.setSeatNum(4); // Enough seats
        table2.addTimeslot("10:00 - 11:00");
        restaurant.getRestaurantAllTables().add(table2);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(4, "10:00 - 11:00", LocalDate.now());

        // Then: Should return the ID of the suitable table
        assertEquals(2, availableTableID);
    }
    @Test
    public void testAvailableTableID_TableAvailableSufficientSeats() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: One table available with enough seats
        Table table = new Table(1);
        table.setSeatNum(5); 
        table.addTimeslot(timeslot); 
        restaurant.getRestaurantAllTables().add(table);

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(requiredPeople, timeslot, bookingDate);

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

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
    }

    @Test
    public void testAvailableTableID_TimeslotNotAvailable() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        Table table = new Table(1);
        table.setSeatNum(5); // Enough seats
        table.addTimeslot("11:00 - 12:00"); // Different timeslot

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
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

        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(requiredPeople, timeslot, bookingDate);

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
        restaurant.getRestaurantAllTables().add(table1);
        restaurant.getRestaurantAllTables().add(table2);
       table1.setTimeslotUnavailable(timeslot,bookingDate);
        // When: Checking for available table ID
        int availableTableID = restaurant.availableTableIDInRestaurant(requiredPeople, timeslot, bookingDate);

        // Then: Should return the ID of the suitable table
        assertEquals(2, availableTableID);
    }
    @Test
    public void test_getTimeslots_ZeroDuration() {
    	
        restaurant.setRestaurantSessionDuration(Duration.ZERO);
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
        assertEquals(expected, restaurant.getRestaurantTimeslots());
    }
    

    @Test
    public void test_getTimeslots_NegativeDuration() {
        restaurant.setRestaurantSessionDuration(Duration.ofMinutes(-30)); // Set duration to zero
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
        assertEquals(expected, restaurant.getRestaurantTimeslots());
    }
//
    @Test
    public void test_getTimeslots_OpeningEqualsClosing() {
        restaurant.setRestaurantOpenTime(LocalTime.parse("10:00"));
        restaurant.setRestaurantCloseTime(LocalTime.parse("10:00")); // Same opening and closing time
        String expected = ""; // No time slots because opening and closing times are the same
        assertEquals(expected, restaurant.getAccountTimeslots());
    }
//
    @Test
    public void test_getTimeslots_SingleSession() {
        restaurant.setRestaurantCloseTime(LocalTime.parse("11:00")); // Adjust closing time for a single session
        String expected = "10:00 - 11:00 \n"; // Only one valid time slot
        assertEquals(expected, restaurant.getAccountTimeslots());
    }
//
    @Test
    public void test_getTimeslots_MultipleSessions() {
        restaurant.setRestaurantOpenTime(LocalTime.parse("09:00")); // Change opening time
        restaurant.setRestaurantCloseTime(LocalTime.parse("12:00")); // Change closing time
        restaurant.setRestaurantSessionDuration(Duration.ofMinutes(30)); // Change session duration
        String expected = "09:00 - 09:30 \n" +
                          "09:30 - 10:00 \n" +
                          "10:00 - 10:30 \n" +
                          "10:30 - 11:00 \n" +
                          "11:00 - 11:30 \n" +
                          "11:30 - 12:00 \n"; // Multiple 30-minute slots
        assertEquals(expected, restaurant.getRestaurantTimeslots());
    }
//
    @Test
    public void test_getTimeslots_EndsExactlyAtClosing() {
        restaurant.getRestaurantSessionDuration(); // One slot that ends at closing
        String expected = "10:00 - 11:00 \n"+
       "11:00 - 12:00 \n"+
         "12:00 - 13:00 \n"+
         "13:00 - 14:00 \n"+
         "14:00 - 15:00 \n"+
        "15:00 - 16:00 \n"+
        "16:00 - 17:00 \n"+
        "17:00 - 18:00 \n"+
         "18:00 - 19:00 \n"+
        "19:00 - 20:00 \n";// One slot that ends exactly at closing time
        assertEquals(expected, restaurant.getRestaurantTimeslots());
    }
//
    @Test
    public void test_getTimeslots_NoValidSlots() {
        restaurant.setRestaurantSessionDuration(Duration.ofMinutes(90)); // Too long for the available time
        String expected = "10:00 - 11:30 \n" +
                "11:30 - 13:00 \n" +
                "13:00 - 14:30 \n" +
                "14:30 - 16:00 \n" +
                "16:00 - 17:30 \n" +
                "17:30 - 19:00 \n";// No valid slots because a single session exceeds closing time
        assertEquals(expected, restaurant.getAccountTimeslots());
    }
//
    @Test
    public void test_getTimeslots_SessionExceedsTime() {
        restaurant.setRestaurantOpenTime(LocalTime.parse("09:00")); // Opening time
        restaurant.setRestaurantCloseTime(LocalTime.parse("09:30")); // Closing time
        restaurant.setRestaurantSessionDuration(Duration.ofMinutes(60)); // Session too long
        String expected = ""; // No valid slots because session exceeds available time
        assertEquals(expected, restaurant.getRestaurantTimeslots());
    }
//
    @Test
    public void test_getTimeslots_SessionStartsAtCloseTime() {
        restaurant.setRestaurantOpenTime(LocalTime.parse("10:00")); // Opening time
        restaurant.setRestaurantCloseTime(LocalTime.parse("11:00")); // Closing time
        restaurant.setRestaurantSessionDuration(Duration.ofMinutes(60)); // One session that starts at opening
        String expected = "10:00 - 11:00 \n"; // Only one valid time slot
        assertEquals(expected, restaurant.getRestaurantTimeslots());
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
        assertEquals(expected, restaurant.getRestaurantTimeslots());
    }
    
    @Test
    public void testTableValidation_TableExists() {
        // Given: Adding a table with ID 1
        Table table = new Table(1);
        restaurant.setRestaurantAllTables(new ArrayList<>());
        restaurant.getAccountAllTables().add(table);

        // When: Validating table ID 1
        boolean result = restaurant.tableValidationInRestaurant(1);

        // Then: Should return true
        assertTrue(result);
    }

    @Test
    public void testTableValidation_TableDoesNotExist() {
        // Given: Adding a table with ID 1
        Table table = new Table(1);
        restaurant.setRestaurantAllTables(new ArrayList<>());
        restaurant.getAccountAllTables().add(table);

        // When: Validating a non-existent table ID 2
        boolean result = restaurant.tableValidationInRestaurant(2);

        // Then: Should return false
        assertFalse(result);
    }

    @Test
    public void testTableValidation_NoTables() {
        // Given: No tables in the restaurant
        restaurant.setRestaurantAllTables(new ArrayList<>());

        // When: Validating any table ID
        boolean result = restaurant.tableValidationInRestaurant(1);

        // Then: Should return false
        assertFalse(result);
    }
    @Test 
    public void TestValidupdateTableInfo() {
    	String[] input = { "10"};
		setInput(input);
		restaurant.updateRestaurantTableInfo(scanner,1);
		assertEquals(10, restaurant.getAccountAllTables().get(0).getSeatNum());
		assertEquals(0, restaurant.getAccountAllTables().get(1).getSeatNum());

    }
    
    @Test 
    public void TestInvalidupdateTableInfo1() {
    	String[] input = { "-1","23","X"};
		setInput(input);
		System.out.println("THis is the part for the updating setaNum and this the old setNUm "+restaurant.getRestaurantAllTables().get(0).getSeatNum());
		restaurant.updateRestaurantTableInfo(scanner,1);
		assertEquals(23, restaurant.getAccountAllTables().get(0).getSeatNum());
		assertEquals(0, restaurant.getRestaurantAllTables().get(1).getSeatNum());

    }
    
    @Test 
    public void TestInvalidupdateTableInfo2() {
    	String[] input = { "HI","10"};
		setInput(input);
		restaurant.updateRestaurantTableInfo(scanner,1);
		assertEquals(10, restaurant.getRestaurantAllTables().get(0).getSeatNum());
		assertEquals(0, restaurant.getRestaurantAllTables().get(1).getSeatNum());

    }
    @Test
    public void test_getAllCommentList() {
	    String customerName = "User3";
	    String content = "This is a great restaurant!";
	    int rate = 5;
	    Comment cm1 = new Comment("User1", "Great", 3, LocalDate.now());
	    Comment cm2 = new Comment("User2", "Good", 4, LocalDate.now());
	    Comment cm3=new Comment(customerName, content, rate, LocalDate.now().plusDays(2));	
	    restaurant.getRestaurantAllCommentsList().add(cm3);
	    ArrayList<Comment> cmList=new ArrayList<Comment>();
	    cmList.add(cm1);
	    cmList.add(cm2);
	    cmList.add(cm3);
	    assertEquals(3, restaurant.getRestaurantAllCommentsList().size());
	    for(int i=0;i<restaurant.getRestaurantAllCommentsList().size();i++) {
		    assertEquals(cmList.get(i).getCommentCustomerName(),restaurant.getRestaurantAllCommentsList().get(i).getCommentCustomerName());
		    assertEquals(cmList.get(i).getCommentContent(),restaurant.getRestaurantAllCommentsList().get(i).getCommentContent());
		    assertEquals(cmList.get(i).getCommentRate(),restaurant.getRestaurantAllCommentsList().get(i).getCommentRate(),0.01);
		    assertEquals(cmList.get(i).getCommentDate(),restaurant.getRestaurantAllCommentsList().get(i).getCommentDate());

		    }
    }
    @Test
    public void testGetAllComments_Empty() {
        // Test when there are no comments
        String result = restaurant.getRestaurantAllComments();
        assertEquals("User1: Great 3.0\nUser2: Good 4.0\n", result);
    }

    @Test
    public void testGetAllComments_SingleComment() {
        // Test when there is a single comment
        Comment comment = new Comment("John", "Great food!", 5,LocalDate.now());
        restaurant.getRestaurantAllCommentsList().add(comment);

        String result = restaurant.getRestaurantAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nJohn: Great food! 5.0\n";
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllComments_MultipleComments() {
        // Test when there are multiple comments
        Comment comment1 = new Comment("Alice", "Loved it!", 4,LocalDate.now());
        Comment comment2 = new Comment("Bob", "Not bad.", 3,LocalDate.now());
        restaurant.getRestaurantAllCommentsList().add(comment1);
        restaurant.getRestaurantAllCommentsList().add(comment2);
        String result = restaurant.getRestaurantAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nAlice: Loved it! 4.0\nBob: Not bad. 3.0\n";
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllComments_CommentWithEmptyContent() {
        // Test when there is a comment with empty content
        Comment comment = new Comment("Charlie", "", 2,LocalDate.now());
        restaurant.getRestaurantAllCommentsList().add(comment);
        String result = restaurant.getRestaurantAllComments();
        String expected = "User1: Great 3.0\nUser2: Good 4.0\nCharlie:  2.0\n"; // Expecting the string to handle empty content
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllComments_CommentWithZeroRate() {
        // Test when there is a comment with a rate of zero
        Comment comment = new Comment("Diana", "Okay.", 0,LocalDate.now());
        restaurant.getRestaurantAllCommentsList().add(comment);

        String result = restaurant.getRestaurantAllComments();
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
        restaurant.getRestaurantAllCommentsList().add(comment);
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
        Booking booking = new Booking(differentDate,1,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer.getAccountUserName(),customer.getAccountContact(),2);
        restaurant.addBooking(booking); // Assuming this method exists
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals( 0, totalBookings);
    }

    @Test
    public void testGetBookingRecord_SingleBooking() {
        // Test when there is a single booking for the given date
    	LocalDate bookingDate=LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(bookingDate,1,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer.getAccountUserName(),customer.getAccountContact(),2);
        restaurant.addBooking(booking);

        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals( 1, totalBookings);
    }

    @Test
    public void testGetBookingRecord_MultipleBookingsSameTimeSlot() {
    	LocalDate bookingDate=LocalDate.now();
        Customer customer1 = new Customer("testUser1", "password123", "Doe", "123459");
        Customer customer2 = new Customer("testUser2", "password123", "John", "56789");

        Booking booking1 = new Booking(bookingDate,1,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer1.getAccountUserName(),customer1.getAccountContact(),2);

        Booking booking2 = new Booking(bookingDate,1,"13:00-14:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer2.getAccountUserName(),customer2.getAccountContact(),2);
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
        Booking booking1 = new Booking(bookingDate,1,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer1.getAccountUserName(),customer1.getAccountContact(),2);
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

        
        Booking booking1 = new Booking(bookingDate,1,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer1.getAccountUserName(),customer1.getAccountContact(),2);
        
        Booking booking2 = new Booking(bookingDate,2,"13:00-14:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer2.getAccountUserName(),customer2.getAccountContact(),4);
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
        Booking booking = new Booking(bookingDate,1,"",restaurant.getAccountUserName(),restaurant.getAccountName(),customer.getAccountUserName(),customer.getAccountContact(),4);

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
        Booking booking1 = new Booking(bookingDate,2,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer1.getAccountUserName(),customer1.getAccountContact(),2);
        Booking booking2 = new Booking(bookingDate,2,"12:00-13:00",restaurant.getAccountUserName(),restaurant.getAccountName(),customer2.getAccountUserName(),customer2.getAccountContact(),4);
        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);
        
        // Capture the output to validate grouping
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(2, totalBookings);
        
        // Additional checks can be added if needed to validate printed output
    }
    @Test
    public void testGetBookingRecord_BookingWithNullTimeSlot() {
        // Test when a booking has a null time slot
        LocalDate bookingDate = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(bookingDate, 1, null, restaurant.getAccountUserName(), restaurant.getAccountName(), customer.getAccountUserName(), customer.getAccountContact(), 4);
        
        restaurant.addBooking(booking);
        
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(1, totalBookings); // Should still count the booking
    }

    @Test
    public void testGetBookingRecord_BookingWithFutureDate() {
        // Test when a booking is made for a future date
        LocalDate futureDate = LocalDate.now().plusDays(5);
        LocalDate bookingDate = LocalDate.now();
        Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
        Booking booking = new Booking(futureDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), customer.getAccountUserName(), customer.getAccountContact(), 2);
        restaurant.addBooking(booking);
        int totalBookings = restaurant.getBookingRecord(bookingDate);
        assertEquals(0, totalBookings); // Should not count bookings for future dates
    }
    @Test
    public void testGetPeriodBookings_NoBookings() {
        // Test when there are no bookings in the restaurant
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        ArrayList<Booking> bookings = restaurant.getPeriodBookings(startDate, endDate);
        
        // Expecting an empty list
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testGetPeriodBookings_BookingsOutsideRange() {
        // Test when bookings exist but are outside the specified date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate bookingDate1 = startDate.minusDays(1); // Before range
        LocalDate bookingDate2 = endDate.plusDays(1); // After range

        Booking booking1 = new Booking(bookingDate1, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
        Booking booking2 = new Booking(bookingDate2, 2, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer2", "987654321", 4);

        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);

        ArrayList<Booking> bookings = restaurant.getPeriodBookings(startDate, endDate);
        
        // Expecting an empty list
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testGetPeriodBookings_SingleBookingInRange() {
        // Test when there is a single booking within the specified date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate bookingDate = startDate.plusDays(2); // Within range

        Booking booking = new Booking(bookingDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
        restaurant.addBooking(booking);

        ArrayList<Booking> bookings = restaurant.getPeriodBookings(startDate, endDate);
        
        // Expecting one booking in the result
        assertEquals(1, bookings.size());
        assertEquals(booking, bookings.get(0));
    }

    @Test
    public void testGetPeriodBookings_MultipleBookingsInRange() {
        // Test when there are multiple bookings within the specified date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate bookingDate1 = startDate.plusDays(2); // Within range
        LocalDate bookingDate2 = startDate.plusDays(4); // Within range

        Booking booking1 = new Booking(bookingDate1, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
        Booking booking2 = new Booking(bookingDate2, 2, "13:00-14:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer2", "987654321", 4);

        restaurant.addBooking(booking1);
        restaurant.addBooking(booking2);

        ArrayList<Booking> bookings = restaurant.getPeriodBookings(startDate, endDate);
        
        // Expecting two bookings in the result
        assertEquals(2, bookings.size());
        assertTrue(bookings.contains(booking1));
        assertTrue(bookings.contains(booking2));
    }

    @Test
    public void testGetPeriodBookings_BookingsOnStartAndEndDate() {
        // Test when bookings are on the start and end date
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate bookingDateStart = startDate; // On start date
        LocalDate bookingDateEnd = endDate; // On end date

        Booking bookingStart = new Booking(bookingDateStart, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customerStart", "123456789", 2);
        Booking bookingEnd = new Booking(bookingDateEnd, 2, "13:00-14:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customerEnd", "987654321", 4);

        restaurant.addBooking(bookingStart);
        restaurant.addBooking(bookingEnd);

        ArrayList<Booking> bookings = restaurant.getPeriodBookings(startDate, endDate);
        
        // Expecting two bookings in the result
        assertEquals(2, bookings.size());
        assertTrue(bookings.contains(bookingStart));
        assertTrue(bookings.contains(bookingEnd));
    }

    @Test
    public void testGetPeriodBookings_BookingsWithMixedDates() {
        // Test when there are mixed bookings, some in range and some out of range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate bookingDateInRange = startDate.plusDays(2); // Within range
        LocalDate bookingDateOutOfRange = startDate.minusDays(1); // Out of range
        Booking bookingInRange = new Booking(bookingDateInRange, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customerInRange", "123456789", 2);
        Booking bookingOutOfRange = new Booking(bookingDateOutOfRange, 2, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customerOutOfRange", "987654321", 4);

        restaurant.addBooking(bookingInRange);
        restaurant.addBooking(bookingOutOfRange);

        ArrayList<Booking> bookings = restaurant.getPeriodBookings(startDate, endDate);
        
        // Expecting one booking in the result
        assertEquals(1, bookings.size());
        assertTrue(bookings.contains(bookingInRange));
    }



    @Test
    public void testGetPeriodComments_NoComments() {
        // Test when there are no comments
        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = startDate.plusDays(5);
        ArrayList<Comment> comments = restaurant.getPeriodComments(startDate, endDate);
        
        // Expecting an empty list
        assertTrue(comments.isEmpty());
    }

    @Test
    public void testGetPeriodComments_CommentsOutsideRange() {
        // Test when comments exist but are outside the specified date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate commentDate1 = startDate.minusDays(1); // Before range
        LocalDate commentDate2 = endDate.plusDays(1); // After range

        Comment comment1 = new Comment("User1", "Great food!", 5, commentDate1);
        Comment comment2 = new Comment("User2", "Not bad!", 3, commentDate2);

        restaurant.addCommentInRestaurant(comment1);
        restaurant.addCommentInRestaurant(comment2);

        ArrayList<Comment> comments = restaurant.getPeriodComments(startDate, endDate);
        
        // Expecting an empty list
        assertFalse(comments.isEmpty());
    }

    @Test
    public void testGetPeriodComments_SingleCommentInRange() {
        // Test when there is a single comment within the specified date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate commentDate = startDate.plusDays(2); // Within range

        Comment comment = new Comment("User1", "Great food!", 5, commentDate);
        restaurant.addCommentInRestaurant(comment);

        ArrayList<Comment> comments = restaurant.getPeriodComments(startDate, endDate);
        
        // Expecting one comment in the result
        assertEquals(3, comments.size());
        assertEquals(comment.getCommentContent(), comments.get(2).getCommentContent());
        assertEquals(comment.getCommentCustomerName(), comments.get(2).getCommentCustomerName());
        assertEquals(comment.getCommentDate(), comments.get(2).getCommentDate());
        assertEquals(comment.getCommentRate(), comments.get(2).getCommentRate(),0.01);

    }

    @Test
    public void testGetPeriodComments_MultipleCommentsInRange() {
        // Test when there are multiple comments within the specified date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate commentDate1 = startDate.plusDays(2); // Within range
        LocalDate commentDate2 = startDate.plusDays(4); // Within range

        Comment comment1 = new Comment("User1", "Great food!", 5, commentDate1);
        Comment comment2 = new Comment("User2", "Not bad!", 3, commentDate2);

        restaurant.addCommentInRestaurant(comment1);
        restaurant.addCommentInRestaurant(comment2);

        ArrayList<Comment> comments = restaurant.getPeriodComments(startDate, endDate);
        
        // Expecting two comments in the result
        assertEquals(4, comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }

    @Test
    public void testGetPeriodComments_CommentsOnStartAndEndDate() {
        // Test when comments are on the start and end date
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate commentDateStart = startDate; // On start date
        LocalDate commentDateEnd = endDate; // On end date

        Comment commentStart = new Comment("User1", "Great food!", 5, commentDateStart);
        Comment commentEnd = new Comment("User2", "Not bad!", 3, commentDateEnd);

        restaurant.addCommentInRestaurant(commentStart);
        restaurant.addCommentInRestaurant(commentEnd);

        ArrayList<Comment> comments = restaurant.getPeriodComments(startDate, endDate);
        
        // Expecting two comments in the result
        assertEquals(4, comments.size());
        assertTrue(comments.contains(commentStart));
        assertTrue(comments.contains(commentEnd));
    }

    @Test
    public void testGetPeriodComments_CommentsWithMixedDates() {
        // Test when there are mixed comments, some in range and some out of range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        LocalDate commentDateInRange = startDate.plusDays(2); // Within range
        LocalDate commentDateOutOfRange = startDate.minusDays(1); // Out of range

        Comment commentInRange = new Comment("User1", "Great food!", 5, commentDateInRange);
        Comment commentOutOfRange = new Comment("User2", "Not bad!", 3, commentDateOutOfRange);

        restaurant.addCommentInRestaurant(commentInRange);
        restaurant.addCommentInRestaurant(commentOutOfRange);

        ArrayList<Comment> comments = restaurant.getPeriodComments(startDate, endDate);
        
        // Expecting one comment in the result
        assertEquals(3, comments.size());
        assertTrue(comments.contains(commentInRange));
    }

    @Test
    public void testgenerateLogWithoutRank(){

        LocalDate thisWeekStartDate =LocalDate.now();
         LocalDate thisWeekEndDate=thisWeekStartDate.plusDays(7); 
         LocalDate lastWeekStartDate=thisWeekStartDate.minusDays(14);
         LocalDate lastWeekEndDate=thisWeekStartDate.minusDays(7);
         restaurant.generateLogWithoutRank(thisWeekStartDate,thisWeekEndDate,lastWeekStartDate,lastWeekEndDate);
         Comment commentInRange = new Comment("User1", "Great food!", 5, thisWeekStartDate);
         Comment commentOutOfRange = new Comment("User2", "Not bad!", 3, lastWeekEndDate.minusDays(1));
         Comment comment3 = new Comment("User3", "No!",0, lastWeekStartDate.plusDays(1));
         Comment cm1 = new Comment("User1", "Great", 3, LocalDate.now());
        Comment cm2 = new Comment("User2", "Good", 4, LocalDate.now());
        Comment cm3 = new Comment("User1", "Great", 3, LocalDate.now());
        Comment cm4 = new Comment("User2", "Good", 4, LocalDate.now());
        restaurant.addCommentInRestaurant(commentInRange);
        restaurant.addCommentInRestaurant(commentOutOfRange);
        restaurant.addCommentInRestaurant(comment3);
        restaurant.addCommentInRestaurant(cm1);
        restaurant.addCommentInRestaurant(cm2);
        restaurant.addCommentInRestaurant(cm3);
        restaurant.addCommentInRestaurant(cm4);
        assertEquals(0.0f,restaurant.getRestaurantLastWeekRate(),0.01);
        assertEquals(3.5f,restaurant.getThisWeekRate(),0.01);

    }
 

    @Test
    public void testGenerateLogWithoutRank_NoBookings_NoComments() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(0.0f, restaurant.getLastWeekRate(), 0.01);
        assertEquals(3.5f, restaurant.getThisWeekRate(), 0.01);
    }

    @Test
    public void testGenerateLogWithoutRank_BookingsOnly_LastWeek() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add a booking for last week
        restaurant.addBooking(new Booking(lastWeekStartDate.plusDays(1), 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(0.0f, restaurant.getLastWeekRate(), 0.01);
        assertEquals(3.5f, restaurant.getThisWeekRate(), 0.01);
    }

    @Test
    public void testGenerateLogWithoutRank_CommentsOnly_LastWeek() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add a comment for last week
        restaurant.addCommentInRestaurant(new Comment("User1", "Great food!", 5, lastWeekStartDate.plusDays(1)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(5.0f, restaurant.getLastWeekRate(), 0.01);
        assertEquals(3.5f, restaurant.getThisWeekRate(), 0.01);
    }

    @Test
    public void testGenerateLogWithoutRank_MixedData_LastWeek() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add a booking and a comment for last week
        restaurant.addBooking(new Booking(lastWeekStartDate.plusDays(1), 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));
        restaurant.addCommentInRestaurant(new Comment("User1", "Great food!", 5, lastWeekStartDate.plusDays(1)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(5.0f, restaurant.getLastWeekRate(), 0.01); // Average of 5 for last week
        assertEquals(3.5f, restaurant.getThisWeekRate(), 0.01);
    }

    @Test
    public void testGenerateLogWithoutRank_MixedData_ThisWeek() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add a booking and comments for this week
        restaurant.addBooking(new Booking(thisWeekStartDate.plusDays(1), 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));
        restaurant.addCommentInRestaurant(new Comment("User1", "Great food!", 5, thisWeekStartDate.plusDays(2)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Good", 4, thisWeekStartDate.plusDays(3)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(0.0f, restaurant.getLastWeekRate(), 0.01);
        assertEquals(4.0f, restaurant.getThisWeekRate(), 0.01); // Average of 5 and 4
    }

    @Test
    public void testGenerateLogWithoutRank_EdgeCaseOnDates() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add a booking for this week and comment for last week
        restaurant.addBooking(new Booking(thisWeekStartDate, 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));
        restaurant.addCommentInRestaurant(new Comment("User1", "Great food!", 5, lastWeekEndDate));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(5.0f, restaurant.getLastWeekRate(), 0.01);
        assertEquals(3.5f, restaurant.getThisWeekRate(), 0.01);
    }

    @Test
    public void testGenerateLogWithoutRank_BothWeeksWithComments() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        restaurant.addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));
        restaurant.addCommentInRestaurant(new Comment("User3", "Amazing!", 4, thisWeekStartDate.plusDays(1)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(4.0f, restaurant.getLastWeekRate(), 0.01); // Average (5 + 3) / 2
        assertEquals(3.67f, restaurant.getThisWeekRate(), 0.01); // Only one comment with rate 4
    }

    @Test
    public void testGenerateLogWithoutRank_BothWeeksWithComments2() {
        LocalDate thisWeekStartDate = LocalDate.now().plusDays(7);
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(14);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        restaurant.addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(3.75f, restaurant.getLastWeekRate(), 0.01); // Average (5 + 3) / 2
        assertEquals(0.0f, restaurant.getThisWeekRate(), 0.01); // Only one comment with rate 4
    }
    @Test
    public void testsetLastWeekRank(){
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        restaurant.addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));
        restaurant.addCommentInRestaurant(new Comment("User3", "Amazing!", 4, thisWeekStartDate.plusDays(1)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        assertEquals(0.0f, restaurant.getLastWeekRank(), 0.01); 
        restaurant.setLastWeekRank(1);
        assertEquals(1.0f, restaurant.getLastWeekRank(), 0.01); 
    }
    @Test
    public void testSetAndGetLastWeekRank() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        restaurant.addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));
        restaurant.addCommentInRestaurant(new Comment("User3", "Amazing!", 4, thisWeekStartDate.plusDays(1)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        // Set the last week rank
        restaurant.setLastWeekRank(3);
        // Verify the rank is set correctly
        assertEquals(3, restaurant.getLastWeekRank());
    }

    @Test
    public void testSetAndGetThisWeekRank() {
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        restaurant.addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));
        restaurant.addCommentInRestaurant(new Comment("User3", "Amazing!", 4, thisWeekStartDate.plusDays(1)));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        // Set the this week rank
        restaurant.setThisWeekRank(1);
        // Verify the rank is set correctly
        assertEquals(1, restaurant.getThisWeekRank());
    }

    @Test
    public void testGenerateWeeklyReport() {
      
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
    
        // Create a new instance of Restaurant
        Restaurant restaurant = new Restaurant("user", "password", "Test Restaurant", "Italian",
                                                "Downtown", "123 Main St", "555-1234",
                                                LocalTime.parse("10:00"), LocalTime.parse("22:00"),
                                                Duration.ofMinutes(60), 5);
    
        // Add comments for last week
        restaurant.addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        restaurant.addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));
    
        // Add comments for this week
        restaurant.addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
        restaurant.addCommentInRestaurant(new Comment("User4", "Good", 4, thisWeekEndDate));
    
        // Generate logs without rank
        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
    
        // Set the ranks
        restaurant.setLastWeekRank(2);
        restaurant.setThisWeekRank(5);
    
        // Generate the report
        StringBuilder expectedReport = new StringBuilder();
        expectedReport.append("Last week:\n");
        expectedReport.append("Rank: ").append(0).append("\n");
        expectedReport.append("Rate: ").append(4.0f).append("\n"); // Average of (5 + 3) / 2
        expectedReport.append("Total ppl: ").append(2).append("\n"); // Total comments for last week
        expectedReport.append("Comment:\n");
        expectedReport.append("User1: Excellent! 5.0\n");
        expectedReport.append("User2: Not bad 3.0\n");
        
        expectedReport.append("\nThis week:\n");
        expectedReport.append("Rank: ").append(5).append("\n");
        expectedReport.append("Rate: ").append(3.75f).append("\n"); // Average of (4 + 4) / 2
        expectedReport.append("Total ppl: ").append(0).append("\n"); // Total comments for this week
        expectedReport.append("Comment:\n");
        expectedReport.append("User3: Great 4\n");
        expectedReport.append("User4: Good 4\n");
        
        expectedReport.append("\nRank decrease/increase ").append(~(5 - 2) + 1).append("\n");
        expectedReport.append("Rate decrease/increase ").append(4.0 - 4.0).append("\n");
        expectedReport.append("Total ppl decrease/increase ").append(2 - 2).append("\n");
    
        // Capture the output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
    
        // Call the method to generate the report
        restaurant.generateWeeklyReport();
    
        // Verify the output
        assertEquals(expectedReport.toString(), outContent.toString());
    
        // Reset the output
        System.setOut(System.out);
    }

    @Test
    public void testTakeAttendanceInRestaurant() {
        LocalDate date = LocalDate.now();
        // Add a booking
        restaurant.addBooking(new Booking(date, 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));

        // Mark attendance
        boolean attendanceMarked = restaurant.takeAttendanceInRestaurant(date, "12:00-13:00", 1);

        // Verify attendance was marked correctly
        assertTrue(attendanceMarked);
        assertTrue(restaurant.getAllBookings().get(0).hasArrived());
    }

    @Test
    public void testTakeAttendanceInRestaurant_BookingNotFound() {
        LocalDate date = LocalDate.now();
        // Try to mark attendance for a booking that does not exist
        boolean attendanceMarked = restaurant.takeAttendanceInRestaurant(date, "12:00-13:00", 1);

        // Verify that attendance was not marked
        assertFalse(attendanceMarked);
    }
}




