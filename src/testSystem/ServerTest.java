package testSystem;


import org.junit.Before;
import org.junit.Test;

import system.Account;
import system.Booking;
import system.Customer;
import system.Restaurant;
import system.RestaurantLog;
import system.Server;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServerTest {

	private Server server;
	private Customer customer;
	private Restaurant restaurant;
	
	@Before
	public void setUp() {
		server =Server.getInstance();
		customer = new Customer("customerUser", "customerPass", "Customer Name", "123456789");
        restaurant = new Restaurant("restaurantUser", "restaurantPass", "Restaurant Name", "Italian", "Downtown", "123 Main St", "987654321",
                LocalTime.of(10, 0), LocalTime.of(22, 0), Duration.ofHours(2), 10);
		
	}
	
	@Test
	public void testaddRestaurant() {
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
	ArrayList<Account> AccountList = new ArrayList<>();
	AccountList.add(restaurant);
	server.addRestaurant(restaurant);
	assertEquals(1,server.getAccountList().size());
	assertEquals(restaurant,server.getAccountList().get(0));
	}
	
//	@Test
//	public void TestaddRestaurantAccount() {
//		
//	}
	
	@Test
    public void testSignUpCustomer() {
        Account result = server.signUp("CUSTOMER", "custUser", "custPass", "Cust Name", "123456789", "", "", "",
                LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, Duration.ZERO, 0);
        assertNotNull(result);
        assertTrue(result instanceof Customer);
    }

//    @Test
//    public void testSignUpRestaurant() {
//        Account result = server.signUp("RESTAURANT", "restUser", "restPass", "Rest Name", "123456789", "Italian", "Downtown", "123 Main St",
//                LocalTime.of(10, 0), LocalTime.of(22, 0), Duration.ofHours(2), 10);
//        assertNotNull(result);
//        assertTrue(result instanceof Restaurant);
//    }

//    @Test
//    public void testIsUsernameExist() {
//        server.addRestaurantAccount(restaurant);
//        assertTrue(server.isUsernameExist("restaurantUser"));
//    }
//
//    @Test
//    public void testSignIn() {
//        server.addRestaurantAccount(restaurant);
//        Account result = server.signIn("restaurantUser", "restaurantPass");
//        assertNotNull(result);
//        assertEquals("restaurantUser", result.getAccountUserName());
//    }
//
////    @Test
////    public void testGetUserDetail() {
////        server.addRestaurantAccount(restaurant);
////        String details = server.getUserDetail(restaurant);
////        assertTrue(details.contains("Username: restaurantUser"));
////    }
//
////    @Test
////    public void testUpdateUserDetail() {
////        server.addRestaurantAccount(restaurant);
////        // Assuming we have an edit method that modifies the account
////        // Mock or simulate the Scanner input here as needed
////        // server.updateUserDetail(restaurant, new Scanner("New Name\n")); // Example input
////    }
//
////    @Test
////    public void testMakeBooking() {
////        server.addRestaurantAccount(restaurant);
////        server.addRestaurantAccount(customer);
////        Booking booking = new Booking(LocalDate.now(), 1, "18:00", restaurant, customer, "123456789", 4);
////        String result = server.makeBooking(LocalDate.now(), 1, "18:00", restaurant, customer, "123456789", 4);
////        assertEquals("\nAlready booked a seat for you", result);
////    }
//
//    @Test
//    public void testGenerateWeeklyReport() {
//        server.addRestaurantAccount(restaurant);
//        // Assume bookings and comments have been added to restaurant for this test
//        server.generateWeeklyReport(restaurant); // Check output manually or capture output stream for assertions
//    }
//
//    @Test
//    public void testGetRestaurantLog() {
//        server.addRestaurantAccount(restaurant);
//        server.generateRestaurantLog(); // Generate log data
//        RestaurantLog log = server.getRestaurantLog(restaurant, "thisWeek");
//        assertNotNull(log);
//    }
//
//    @Test
//    public void testGetPeriodBookings() {
//        server.addRestaurantAccount(restaurant);
//        // Assume bookings are added here
//        LocalDate today = LocalDate.now();
//        assertFalse(server.getPeriodBookings(restaurant, today, today).isEmpty());
//    }
//
//    @Test
//    public void testTakeAttendance() {
//        server.addRestaurantAccount(restaurant);
//        Booking booking = new Booking(LocalDate.now(), 1, "18:00", restaurant, customer, "123456789", 4);
//        restaurant.addBooking(booking);
//        assertTrue(server.takeAttendance(restaurant, LocalDate.now(), "18:00", 1));
//    }
//
//    @Test
//    public void testAddRestaurantAccount() {
//        server.addRestaurantAccount(restaurant);
//        assertTrue(server.getRestaurantAccounts().contains(restaurant));
//    }
//
////    @Test
////    public void testGenerateRestaurantLogData() {
////        server.addRestaurantAccount(restaurant);
////        server.generateRestaurantLogData(); // Testing internal logic
////        assertNotNull(server.getRestaurantLog(restaurant, "thisWeek"));
////    }
//
//    // Add more tests as necessary for other methods in Server

}
	
