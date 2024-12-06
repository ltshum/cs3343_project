package testSystem;


import system.Booking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import system.Customer;
import system.Restaurant;

import java.io.ByteArrayInputStream;


public class CustomerTest {

    private Customer customer;
    InputStream systemIn = System.in;
    Scanner scanner;
    @Before
    public void setUp() {
        customer = new Customer("testUser", "password123", "John Doe", "123456789");
    }
     @After
    public void tearDown() throws Exception {
        System.setIn(systemIn);
    }

   

    @Test
    public void testCustomerConstructor() {
        // Verify that the constructor initializes fields correctly
        assertEquals("John Doe", customer.getAccountName());
        assertEquals("123456789", customer.getAccountContact());
        assertNotNull(customer.getAllBookings());
        assertTrue(customer.getAllBookings().isEmpty());
        assertEquals("Username should be 'testUser'", "testUser", customer.getAccountUserName());
        assertEquals("Password should be 'password123'", "password123", customer.getAccountPassword());
    }

    @Test
    public void testGetCustomerName() {
        assertEquals("John Doe", customer.getAccountName());
    }

    @Test
    public void testSetCustomerName() {
        customer.setAccountName("Jane Doe");
        assertEquals("Jane Doe", customer.getAccountName());
    }

    @Test
    public void testGetCustomerContact() {
        assertEquals("123456789", customer.getAccountContact());
    }

    @Test
    public void testSetCustomerContact() {
        customer.setAccountContact("987654321");
        assertEquals("987654321", customer.getAccountContact());
    }

    // @Test
    // public void testSetAndGetAllWrittenComment() {
    //     ArrayList<String> comments = new ArrayList<>();
    //     comments.add("Great food!");
    //     customer.(comments);
    //     assertEquals(comments, customer.getAllWrittenComment());
    // }

    // @Test
    // public void testAddBooking() {
    //     System.out.println("This is the start of the test add booking");

    //     // Create a restaurant instance
    //     Restaurant restaurant = new Restaurant("username",
    //             "password",
    //             "name",
    //             "type",
    //             "district",
    //             "address",
    //             "12345678",
    //             LocalTime.parse("10:00"),
    //             LocalTime.parse("20:00"),
    //             Duration.ofMinutes(60),
    //             3);

    //     // Create a customer instance
    //     // Set up the server and Home page
    //     Server server = Server.getInstance();
    //     Home hm = new Home(restaurant);

    //     // Prepare input for the Scanner
    //     String[] input = {"3", "1", "1", "5", "1", "2", "2", "2", "5"}; // Adjust based on actual expected inputs
    //     Scanner scanner = testInput.input(input); // Use the testInput to create a Scanner
    //     hm.display(scanner); // Pass the scanner directly
    //     // Create a booking instance
    //     Booking booking = new Booking(LocalDate.now(), 1, "11:00-12:00", restaurant, customer, customer.getCustomerContact(), 2);
    //     Booking booking2 = new Booking(LocalDate.now(), 2, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
    //     // Add the booking to the customer
    //     customer.addBooking(booking);
    //     customer.addBooking(booking2);

    //     // Assert that the booking was added successfully
    //     assertEquals(2, customer.getAllBookings().size());
    //     assertEquals(booking, customer.getAllBookings().get(0));
    //     assertEquals(booking2, customer.getAllBookings().get(1));

    // }

    @Test
    public void testEditName() {
        String input = "1\nNew Name\nX\n"; // Edit name to "New Name" and exit
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        customer.edit(new Scanner(System.in));
        assertEquals("New Name", customer.getAccountName());
    }

    @Test
    public void testEditPhone() {
        String input = "2\n987654321\nX\n"; // Edit phone to "987654321" and exit
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        customer.edit(new Scanner(System.in));
//
        assertEquals("987654321", customer.getAccountContact());
    }
//

    @Test
    public void testEditInvalidOption() {
        String input = "3\nX\n"; // Invalid option and then exit
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
//        
        customer.edit(new Scanner(System.in));
//        
        assertEquals("John Doe", customer.getAccountName());
        assertEquals("123456789", customer.getAccountContact());
    }
//    

    @Test
    public void testEditInvalidFormat() {
        String input = "hi\nX\n"; // Invalid option and then exit
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        customer.edit(new Scanner(System.in));

        assertEquals("John Doe", customer.getAccountName());
        assertEquals("123456789", customer.getAccountContact());
    }
//

    @Test
    public void testEditExit() {
        String input = "X\n"; // Exit immediately
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
//        
        customer.edit(new Scanner(System.in));
//        
        assertEquals("John Doe", customer.getAccountName());
        assertEquals("123456789", customer.getAccountContact());
    }
    @Test
    public void testgetProfileDetail() {
    	String res= "Name: John Doe" 
        + "\nPhone: 123456789" ;
    	assertEquals(res,customer.getProfileDetail());
    }
    
    @Test
    public void testGetBookingRecord_GroupedBookingsOutput() {
        // Test grouping logic with multiple bookings at the same time
        LocalDate bookingDate = LocalDate.now();
        Restaurant restaurant1 = new Restaurant("username",
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
        Restaurant restaurant2 = new Restaurant("username",
                "password",
                "name2",
                "type",
                "district",
                "address",
                "987654321",
                LocalTime.parse("11:00"),
                LocalTime.parse("23:00"),
                Duration.ofMinutes(30),
                3);


        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00", restaurant1.getAccountName(), restaurant1.getAccountUserName(), customer.getAccountName(),customer.getAccountContact(), 2);
        Booking booking2 = new Booking(bookingDate, 1, "12:00-13:00", restaurant1.getAccountName(), restaurant1.getAccountUserName(), customer.getAccountName(),customer.getAccountContact(), 4); // Same time slot
        customer.addBooking(booking1);
        customer.addBooking(booking2);
        
        // Capture the output to validate grouping     
            int totalBookings = customer.getBookingRecord(bookingDate);
            assertEquals(2, totalBookings);
        }
    @Test
    public void testGetBookingRecord_BookingsOnDifferentDate() {
        LocalDate differentDate = LocalDate.of(2023, 11, 26);
        Restaurant restaurant1 = new Restaurant("username",
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
        Booking booking1 = new Booking(differentDate, 1, "12:00-13:00", restaurant1.getAccountName(), restaurant1.getAccountUserName(), customer.getAccountName(),customer.getAccountContact(), 2);

        customer.addBooking(booking1);
       
        LocalDate bookingDate = LocalDate.now();
        int totalBookings = customer.getBookingRecord(bookingDate);
        assertEquals(0, totalBookings);
    }

    @Test
    public void testGetBookingRecord_SingleBooking() {
        // Test when there is a single booking for the given date LocalDate differentDate = LocalDate.of(2023, 11, 26);
        Restaurant restaurant1 = new Restaurant("username",
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
        LocalDate bookingDate = LocalDate.now();
        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00",restaurant1.getAccountName(), restaurant1.getAccountUserName(), customer.getAccountName(),customer.getAccountContact(), 2);
        customer.addBooking(booking1);

        int totalBookings = customer.getBookingRecord(bookingDate);
        assertEquals(1, totalBookings);
    }

  
    @Test
    public void testGetBookingRecord_EmptyBookingString() {
        // Test when there are no bookings and ensure the output is an empty string
        LocalDate bookingDate = LocalDate.now();
        int output = customer.getBookingRecord(bookingDate); // Capture printed output
        assertEquals("0", Integer.toString(output)); // Adjust as necessary for your implementation
    }

    @Test
public void testGetBookingToBeCommentInCustomer() {
    // Setup the restaurant and customer
    Restaurant restaurant = new Restaurant("username",
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
    
    Customer customer = new Customer("testUser", "password123", "John Doe", "123456789");
    LocalDate bookingDate = LocalDate.now();
    
    // Create bookings
    Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00", restaurant.getAccountName(), restaurant.getAccountUserName(), customer.getAccountName(), customer.getAccountContact(), 2);
    Booking booking2 = new Booking(bookingDate, 2, "13:00-14:00", restaurant.getAccountName(), restaurant.getAccountUserName(), customer.getAccountName(), customer.getAccountContact(), 3);
    
    // Add bookings to customer
    customer.addBooking(booking1);
    customer.addBooking(booking2);
    
    // Test valid input
    Booking result = customer.getBookingToBeCommentInCustomer(1, bookingDate);
    assertEquals("12:00-13:00", result.getBookingTimeslot());
    
    // Test for the second booking
    result = customer.getBookingToBeCommentInCustomer(2, bookingDate);
    assertEquals("13:00-14:00", result.getBookingTimeslot());
    
    // Test for an invalid booking index (out of bounds)
   

    
}
    @Test
    public void testcheckHasAttendInCustomer(){
        Restaurant restaurant1 = new Restaurant("username",
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
        LocalDate bookingDate = LocalDate.now();
        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00",restaurant1.getAccountName(), restaurant1.getAccountUserName(), customer.getAccountName(),customer.getAccountContact(), 2);
        Booking booking2 = new Booking(bookingDate, 2, "13:00-14:00",restaurant1.getAccountName(), restaurant1.getAccountUserName(), customer.getAccountName(),customer.getAccountContact(), 3);
        customer.addBooking(booking1);
        customer.addBooking(booking2);
        customer.getBookingToBeCommentInCustomer(1,bookingDate).setArrive(true);;
        assertTrue(customer.checkHasAttendInCustomer(1,bookingDate));
        customer.getBookingToBeCommentInCustomer(1,bookingDate).setArrive(false);
        assertFalse(customer.checkHasAttendInCustomer(1,bookingDate));



    }
        // Additional checks can be added if needed to validate printed output
    }

