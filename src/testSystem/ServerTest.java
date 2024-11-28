package testSystem;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import acm.Permission;
import acm.Privilege;
import acm.Resource;
import acm.Role;
import system.Account;
import system.Booking;
import system.Customer;
import system.Restaurant;
import system.RestaurantLog;
import system.Server;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.*;


public class ServerTest {

	 Server server;
     Customer customer;
    InputStream systemIn = System.in;
    Restaurant restaurant;
    Scanner scanner;

    @Before
    public void setUp() {
        server = Server.getInstance();
        server.reset();
        restaurant = new Restaurant("Test Restaurant", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
        customer = new Customer("TestUser", "password", "Test Name", "123456789");
        server.addAccount(restaurant); // Assuming there's a method to add restaurant
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

  
    @Test
    public void testSignUpCustomer() {
        Account result = server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        assertNotNull(result);
        // Verify that the customer was added correctly
    }

    @Test
    public void testSignUpRestaurant() {
        Account result = server.signUp("RESTAURANT", "testRest", "password", "Test Restaurant", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
        assertNotNull(result);
        // Verify that the restaurant was added correctly
    }

    @Test
    public void testSignInValid() {
        server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        Account account = server.signIn("testUser", "password");
        assertNotNull(account);
        assertEquals("testUser", server.getUserName(account));
    }@Test
    public void testSignInValid1() {
        server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        Account account = server.signIn("testUser", "password12");
        assertNull(account);
    }@Test
    public void testSignInValid2() {
        server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        Account account = server.signIn("testUser12", "password");
        assertNull(account);
    }

    @Test
    public void testSignInInvalid3() {
        Account account = server.signIn("invalidUser", "password123");
        assertNull(account);
    }

    @Test
    public void testAddRestaurant() {
    	server.addAccount(restaurant);
    	assertEquals(2,server.getAccountList().size());
//        // Check if the restaurant exists in the server
    }

//    @Test
//    public void testGetAllRestaurants() {
//        server.addAccount(restaurant);
//        assertFalse(server.getAllRestaurants().isEmpty());
//    }

    @Test
    public void testSearchRestaurantsIn() {
        server.addAccount(restaurant);
        var results = server.searchRestaurantsIn("Test Restaurant", "District", "null", "Cuisine", "null", "null", "null");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testUpdateTableInfo() {
        // Assuming a method exists to update table info
    	String[] input = { "10"};
		setInput(input);
        server.updateTableInfo(restaurant,scanner,1 );
        assertEquals(10,restaurant.getAllTables().get(0).getSeatNum());//        // Verify the update
    }
	
	@Test
	public void TestaddRestaurantAccount() {
		server.addRestaurantAccount(restaurant);
		assertEquals(restaurant.getRestaurantName(),server.getRestaurantAccounts().get(0).getRestaurantName());
		}
	
	@Test
    public void testSignUpCustomer1() {
        Account result = server.signUp("CUSTOMER", "custUser", "custPass", "Cust Name", "123456789", "", "", "",
        LocalTime.MIDNIGHT, LocalTime.MIDNIGHT, Duration.ZERO, 0);
        assertNotNull(result);
        assertTrue(result instanceof Customer);
    }

//    @Test
//    public void testSignUpRestaurant1() {
//        Account result = server.signUp("RESTAURANT", "restUser", "restPass", "Rest Name", "123456789", "Italian", "Downtown", "123 Main St",
//                LocalTime.of(10, 0), LocalTime.of(22, 0), Duration.ofHours(2), 10);
//        assertNotNull(result);
//        assertTrue(result instanceof Restaurant);
//    }
    
    @Test
    public void testSignUpRestaurantinvalid() {
        Account result = server.signUp("Null", "NullUser", "NullPass", "Null Name", "123456789", "", "", "",
                LocalTime.of(10, 0), LocalTime.of(22, 0), Duration.ofHours(2), 10);
        assertNull(result);
    }

    @Test
    public void testIsUsernameExist() {
        server.addRestaurantAccount(restaurant);
        assertTrue(server.isUsernameExist("Test Restaurant"));
    }
    
    @Test
    public void testIsUsernameExist2() {
        server.addRestaurantAccount(restaurant);
        server.addAccount(customer);
        assertFalse(server.isUsernameExist("TestUser1"));
    }
//
    
//
   @Test
    public void testGetUserDetail() {
        server.addRestaurantAccount(restaurant);
        String res="Rate: " + "0.0"
                + "\nRestaurant Name: " + "Test"
                + "\nType: " + "Cuisine"
                + "\nDistrict: " + "District"
                + "\nAddress: " + "Address"
                + "\nPhone: " + "Contact"
                + "\nOpen Time: " + "09:00"
                + "\nClose Time: " + "21:00"
                + "\nSession Duration: " +   "60mins"
                + "\nTable Amount: 5" + 
                "\n\nTimeslot: \n" + "09:00 - 10:00 \n" +
                "10:00 - 11:00 \n" +
                "11:00 - 12:00 \n" +
                "12:00 - 13:00 \n" +
                "13:00 - 14:00 \n" +
                "14:00 - 15:00 \n" +
                "15:00 - 16:00 \n" +
                "16:00 - 17:00 \n" +
                "17:00 - 18:00 \n" +
                "18:00 - 19:00 \n"+
                "19:00 - 20:00 \n"+
                "20:00 - 21:00 \n"
                + "\nComment: \n" + "User1: Great 3.0\nUser2: Good 4.0\n";
        String details = server.getUserDetail(restaurant);
        assertEquals(res,details);
    }
//
   @Test
    public void testUpdateRestaurantDetail() {
	   String[] input = { "5", "735759", "X"};
		setInput(input);
        server.addRestaurantAccount(restaurant);
        server.updateUserDetail(restaurant, scanner);
        assertEquals("735759", restaurant.getRestaurantContact());
    }
   
   @Test
   public void testUpdateCustomerDetail() {
	   String[] input = { "5", "735759", "X"};
		setInput(input);
       server.addRestaurantAccount(restaurant);
       server.updateUserDetail(restaurant, scanner);
       assertEquals("735759", restaurant.getRestaurantContact());
   }
   @Test
   public void testgetPermissionNumber1() {
	   assertEquals(4,server.getPermissionNumber(restaurant));
   }
   @Test
   public void testgetPermissionNumber2() {
	   assertEquals(3,server.getPermissionNumber(customer));
   }
   @Test
   public void testetPermissionSize1() {
	   assertEquals(4,server.getPermissionSize(restaurant));
   }
   @Test
   public void testetPermissionSize2() {
	   assertEquals(3,server.getPermissionSize(customer));
   }
   @Test
   public void testgetPermissionResource1() {
       Permission per=new Permission(Role.RESTAURANT, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE));
       Resource res=per.getResource();
	   assertEquals(res,server.getPermissionResource(restaurant,1));
   }
   @Test
   public void testgetPermissionResource2() {
       Permission per=new Permission(Role.CUSTOMER, Resource.SEARCH_RESTAURANT, Set.of(Privilege.READ));
       Resource res=per.getResource();
	   assertEquals(res,server.getPermissionResource(customer,3));
   }
   
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
	
