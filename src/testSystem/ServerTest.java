package testSystem;


import static org.junit.Assert.*;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import acm.Permission;
import acm.PermissionRegistry;
import acm.Privilege;
import acm.Resource;
import acm.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import system.Account;
import system.Booking;
import system.Comment;
import system.Customer;
import system.Restaurant;
import system.RestaurantLog;
import system.Server;


public class ServerTest {

	 Server server;
     Account customer;
    InputStream systemIn = System.in;
    Account restaurant;
    Scanner scanner;

    @Before
    public void setUp() {
        server = Server.getInstance();
        restaurant = new Restaurant("TestRestaurant", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
        customer = new Customer("TestUser", "password", "Test Name", "123456789");
        server.addAccount(restaurant); 
        server.addAccount(customer);
        server.addRestaurantAccount((Restaurant)restaurant);
        restaurant.getAccountPermissions();
        customer.getAccountPermissions();
          PermissionRegistry.registerPermissions(Role.RESTAURANT, Arrays.asList(
                new Permission(Role.RESTAURANT, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.RESTAURANT, Resource.VIEW_BOOKING, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.TABLE_MANAGEMENT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.WEEKLY_REPORT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE))
            ));

            PermissionRegistry.registerPermissions(Role.CUSTOMER, Arrays.asList(
                new Permission(Role.CUSTOMER, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.CUSTOMER, Resource.VIEW_BOOKING, Set.of(Privilege.READ)),
                new Permission(Role.CUSTOMER, Resource.SEARCH_RESTAURANT, Set.of(Privilege.READ))
            ));

        // Assuming there's a method to add restaurant
    }
    @After
    public void tearDown() throws Exception {
    	if(scanner!=null) {
        scanner.close();
    	}
        System.setIn(systemIn);
        server.reset();
    }
    
    public void setInput(String[] in) {
        scanner = testInput.input(in);
    }

  @Test
  public void getetAccountByUserNamenull() {
	  assertNull(server.getAccountByUserName("Hello"));
  }
    @Test
    public void testSignUpCustomer() {
        Account result = server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        server.addAccount(result);
        List<Role> newRoles = Arrays.asList(Role.CUSTOMER);
        result.setRoles(newRoles);
        assertNotNull(result);
        assertTrue(result instanceof Customer);

    }

   
    @Test
    public void testgetuseranme() {
        assertEquals("TestUser",server.getUserName(customer));
    }

    @Test
    public void testSignUpRestaurant() {
        Account result = server.signUp("RESTAURANT", "testRest", "password", "Test Restaurant", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
        assertNotNull(result);
        // Verify that the restaurant was added correctly
    }
    @Test
    public void testgetAccountByUserName() {
        String temp=server.getAccountByUserName("TestUser").getAccountUserName();
    	assertEquals(customer.getAccountUserName(),temp);
    }

    @Test
    public void testSignInValid() {
        server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        String account = server.signIn("testUser", "password");
        String account = server.signIn("testUser", "password");
        assertNotNull(account);
        assertEquals("testUser", account);
    }@Test
    public void testSignInValid1() {
        server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        String account = server.signIn("testUser", "password12");
        String account = server.signIn("testUser", "password12");
        assertNull(account);

    }@Test
    public void testSignInValid2() {
        server.signUp("CUSTOMER", "testUser", "password", "Test Name", "123456789", null, null, null, null, null, null, 0);
        String account = server.signIn("testUser12", "password");
        String account = server.signIn("testUser12", "password");
        assertNull(account);
    }
    @Test
public void testSearchAccountById() {
    // Test valid account ID for restaurant
    Account foundRestaurant = server.searchAccountById(restaurant.getId());
    assertNotNull("Should find the restaurant account", foundRestaurant);
    assertEquals("Test", foundRestaurant.getAccountName());

    // Test valid account ID for customer
    Account foundCustomer = server.searchAccountById(customer.getId());
    assertNotNull("Should find the customer account", foundCustomer);
    assertEquals("TestUser", foundCustomer.getAccountUserName());

    // Test invalid account ID
    Account notFound = server.searchAccountById(-1); // Assuming -1 is an invalid ID
    assertNull("Should not find any account for invalid ID", notFound);
}

    @Test
    public void testSignInInvalid3() {
    	String account = server.signIn("invalidUser", "password123");
        String account = server.signIn("invalidUser", "password123");
        assertNull(account);
    }

    @Test
    public void testAddRestaurant() {
    	server.addAccount(restaurant);
    	assertEquals(3,server.getAccountList().size());
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
        assertFalse(results.isEmpty());
    }

    @Test
    public void testUpdateTableInfo() {
        // Assuming a method exists to update table info
    	String[] input = { "10"};
		setInput(input);
        server.updateTableInfo(restaurant.getAccountUserName(),scanner,1 );
        assertEquals(10,restaurant.getAccountAllTables().get(0).getSeatNum());//        // Verify the update
    }
	
	@Test
	public void TestaddRestaurantAccount() {
		server.addRestaurantAccount((Restaurant)restaurant);
		assertEquals(restaurant.getAccountName(),server.getRestaurantAccounts().get(0).getAccountName());
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
        server.addRestaurantAccount((Restaurant)restaurant);
        assertTrue(server.isUsernameExist("TestRestaurant"));
    }
    
    @Test
    public void testIsUsernameExist2() {
        server.addRestaurantAccount((Restaurant)restaurant);
        server.addAccount(customer);
        assertFalse(server.isUsernameExist("TestUser1"));
    }
//
    
//
   @Test
    public void testGetUserDetail() {
        server.addRestaurantAccount((Restaurant)restaurant);
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
        String details = server.getUserDetail(restaurant.getAccountUserName());
        assertEquals(res,details);
    }
//
//   @Test
//    public void testUpdateRestaurantDetail() {
//	   String[] input = { "5", "735759", "X","X"};
//		setInput(input);
//        server.addRestaurantAccount((Restaurant)restaurant);
//        server.updateUserDetail(restaurant.getAccountUserName(), scanner);
//        assertEquals("735759", restaurant.getAccountContact());
//    }
   
   @Test
   public void testUpdateCustomerDetail() {
	   String[] input = { "5", "735759", "X"};
		setInput(input);
       server.addRestaurantAccount((Restaurant)restaurant);
       server.updateUserDetail(restaurant.getAccountUserName(), scanner);
       assertEquals("735759", restaurant.getAccountContact());
   }

   @Test
   public void testgetpermission(){

    List<Permission> permissions= restaurant.getAccountPermissions();
    assertEquals(4,permissions.size());

   }
   @Test
   public void testgetPermissionNumber1() {
    assertEquals(5,server.getPermissionNumber(restaurant.getAccountUserName()));

   }
   
   @Test
   public void testgetRestaurantBookingDetail1() {
	   assertEquals("Restaurant not found.",server.getRestaurantBookingDetail("Hello"));
   }

  
   @Test
   public void testgetPermissionNumber2() {
	   assertEquals(4,server.getPermissionNumber(customer.getAccountUserName()));
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
       String res=per.getResource().toString();
	   assertEquals(res,server.getPermissionResource(restaurant.getAccountUserName(),1));
   }
   @Test
   public void testgetPermissionResource2() {
       Permission per=new Permission(Role.CUSTOMER, Resource.SEARCH_RESTAURANT, Set.of(Privilege.READ));
       String res=per.getResource().toString();
	   assertEquals(res,server.getPermissionResource(customer.getAccountUserName(),3));
   }
	
	@Test
	public void testgetPermissionNumber11() {
	assertEquals(5,server.getPermissionNumber(restaurant.getAccountUserName()));
	}
	@Test
	public void testgetPermissionNumber21() {
	assertEquals(4,server.getPermissionNumber(customer.getAccountUserName()));
	}
	
	
	
	@Test
	public void testgetPermissionResource11() {
	Permission per=new Permission(Role.RESTAURANT, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE));
	String res=per.getResource().toString();
	assertEquals(res,server.getPermissionResource(restaurant.getAccountUserName(),1));
	assertEquals("PROFILE",server.getPermissionResource(restaurant.getAccountUserName(),1));
	assertEquals("LOGOUT",server.getPermissionResource(restaurant.getAccountUserName(),5));
	assertEquals("invalid",server.getPermissionResource(restaurant.getAccountUserName(),6));
	assertEquals("PROFILE",server.getPermissionResource(restaurant.getAccountUserName(),1));
	assertEquals("invalid",server.getPermissionResource(restaurant.getAccountUserName(),0));
	
	}
	@Test
	public void testgetPermissionResource21() {
	Permission per=new Permission(Role.CUSTOMER, Resource.SEARCH_RESTAURANT, Set.of(Privilege.READ));
	String res=per.getResource().toString();
	assertEquals(res,server.getPermissionResource(customer.getAccountUserName(),3));
	   assertEquals("SEARCH_RESTAURANT",server.getPermissionResource(customer.getAccountUserName(),3));
	}
	
	@Test
	public void testgetViewBookingRecord1() {
	LocalDate differentDate = LocalDate.of(2023, 11, 26);
	LocalDate bookingDate=LocalDate.now();
	Booking booking = new Booking(bookingDate,1,"12:00 - 13:00",restaurant.getAccountName(),restaurant.getAccountUserName(),customer.getAccountName(),customer.getAccountContact(),2);
	restaurant.addBooking(booking);
	customer.addBooking(booking);
	int cusbooking =server.getViewBookingRecord(customer.getAccountUserName(), bookingDate);
	int restbooking = server.getViewBookingRecord(restaurant.getAccountUserName(), bookingDate);
	assertEquals( 1, cusbooking);
	assertEquals( cusbooking, restbooking);
	}
	@Test
	public void testgetViewBookingRecord2() {
	LocalDate bookingDate=LocalDate.now();
	Booking booking = new Booking(bookingDate,1,"12:00 - 13:00",restaurant.getAccountName(),restaurant.getAccountUserName(),customer.getAccountName(),customer.getAccountContact(),2);
	restaurant.addBooking(booking);
	customer.addBooking(booking);
	int cusbooking =server.getViewBookingRecord(customer.getAccountUserName(), bookingDate);
	int restbooking = server.getViewBookingRecord(restaurant.getAccountUserName(), bookingDate);
	assertEquals( 1, cusbooking);
	assertEquals( cusbooking, restbooking);
	}
	
	@Test
	public void testtimeslotValidation() {
	assertEquals(false,server.timeslotValidation(restaurant.getAccountUserName(), "12:30-13:00"));
	assertEquals(true,server.timeslotValidation(restaurant.getAccountUserName(), "12:00 - 13:00"));
	}
	
	@Test
	public void testtableValidation() {
	assertFalse(server.tableValidation(restaurant.getAccountUserName(), 0));
	assertTrue(server.tableValidation(restaurant.getAccountUserName(), 3));
	}
	@Test
	public void testtakeattendance() {
	LocalDate bookingDate=LocalDate.now();
	Booking booking = new Booking(bookingDate,1,"12:00 - 13:00",restaurant.getAccountName(),restaurant.getAccountUserName(),customer.getAccountName(),customer.getAccountContact(),2);
	restaurant.addBooking(booking);
	assertTrue(server.takeAttendance(restaurant.getAccountUserName(), bookingDate, "12:00 - 13:00", 1));
	assertFalse(server.takeAttendance(restaurant.getAccountUserName(), bookingDate, "13:00 - 14:00", 1));
	assertFalse(server.takeAttendance(restaurant.getAccountUserName(), bookingDate, "13:30 - 14:00", 1));
	assertFalse(server.takeAttendance(restaurant.getAccountUserName(), bookingDate.plusDays(1),"13:00 - 14:00", 1));
	assertFalse(server.takeAttendance(restaurant.getAccountUserName(), bookingDate, "13:00 - 14:00", 6));
	}
	@Test
	public void searchAccountById() {
		server.searchAccountById(2);
		server.searchAccountById(100);
	}
	@Test
	public void testisRestaurantByUsername() {
		assertFalse(server.isRestaurantByUsername("TestUser"));
		assertTrue(server.isRestaurantByUsername("TestRestaurant"));
	}
	@Test
	public void testisCustomerByUsername() {
		assertTrue(server.isCustomerByUsername("TestUser"));
		assertFalse(server.isCustomerByUsername("TestRestaurant"));
	}
	@Test
	public void testgetListInfo() {
	String res="Test"
	+ "\n   Rate: 0.0"
	+ "\n   District: District"
	+ "\n   Type: Cuisine" ;
	assertEquals(res,server.getListInfo(restaurant.getAccountUserName()));
	}
	
	@Test
    public void testGetRestaurantAccountByUserName() {
        // Test for an existing username
		server.addRestaurantAccount((Restaurant)restaurant);
        Account account = server.getRestaurantAccountByUserName("TestRestaurant");
        assertNotNull(account);
        assertEquals("TestRestaurant", account.getAccountUserName());

        // Test for a non-existing username
        Account account2 = server.getRestaurantAccountByUserName("NonExistentUser");
        assertNull("Account should be null for non-existing username.", account2);
        
        // Test for an empty username
        Account account3 = server.getRestaurantAccountByUserName("");
        assertNull("Account should be null for empty username.", account3);

        // Test for a null username
        Account account4 = server.getRestaurantAccountByUserName(null);
        assertNull("Account should be null for null username.", account4);
    }
	
	@Test 
	public void testgetRestaurantBookingDetail2() {
        Restaurant temp = new Restaurant("Test ", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
		server.addRestaurantAccount((Restaurant)restaurant);
		server.getRestaurantBookingDetail(restaurant.getAccountUserName());
	}
	@Test
	public void testgenerateAccountWeeklyReport() {
         LocalDate thisWeekStartDate = LocalDate.now();
    LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
    LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
    LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
    Booking booking = new Booking(thisWeekStartDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
    restaurant.addBooking(booking);
    restaurant.addBooking(new Booking(thisWeekStartDate, 1, "12:00-13:00", 
    "Test Restaurant", "user", "customer1", "123456789", 4));
    restaurant.addBooking(new Booking(thisWeekEndDate, 1, "12:00-13:00", 
    "Test Restaurant", "user", "customer1", "123456789", 4));
    restaurant.addBooking(new Booking(lastWeekEndDate, 1, "12:00-13:00", 
    "Test Restaurant", "user", "customer1", "123456789", 3));

    // Create a new instance of Restaurant (if needed)
    

    // Add comments for last week
    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate));

    // Add comments for this week
    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate)); // Use start date if today is not the end date
    ((Restaurant) restaurant).generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
		server.generateAccountWeeklyReport(restaurant.getAccountUserName());
	}
		@Test 
	public void testmakeBooking() {
        server.makeBooking(LocalDate.now(),1,"12:00 - 13:00 ",restaurant.getAccountUserName(),customer.getAccountUserName(),customer.getAccountContact(),1);		
	}
		


	@Test
	public void testgetalltableinfo() {
		server.addRestaurantAccount((Restaurant)restaurant);
		server.getAllTableInfo(restaurant.getAccountUserName());
	}
	
//	@Test
//	public void testGetBookingToBeCommentSuccess() {
//	LocalDate differentDate = LocalDate.of(2023, 11, 26);
//	LocalDate bookingDate=LocalDate.now();
//	Booking booking1 = new Booking(differentDate,1,"12:00-13:00",restaurant,customer,customer.getCustomerContact(),2);
//	Booking booking = server.getBookingToBeComment(customer, 1, differentDate);
////	assertEquals(booking1, booking); // Check if the correct booking is returned
//	}

    @Test
    public void testgetRestaurantBookingDetail(){
        
    }
    
//    @Test
//    public void testgenerateAccountLo() {
//    	server.generateAccountLog(restaurant);
//    	
//    }
    @Test 
    public void testcheckHasAttend(){
    	LocalDate bookingDate=LocalDate.now();
    	Booking booking = new Booking(bookingDate,1,"12:00 - 13:00",restaurant.getAccountName(),restaurant.getAccountUserName(),customer.getAccountName(),customer.getAccountContact(),2);
    	customer.addBooking(booking);
    	booking.hasArrived();
    	server.checkHasAttend(customer.getAccountUserName(), 1, bookingDate);
    }	
    @Test
    public void testmakecomment() {
    	LocalDate bookingDate=LocalDate.now();
    	Booking booking = new Booking(bookingDate,1,"12:00 - 13:00",restaurant.getAccountName(),restaurant.getAccountUserName(),customer.getAccountName(),customer.getAccountContact(),2);
    	customer.addBooking(booking);
    	server.makeComment(customer.getAccountUserName(), 1,bookingDate, 4, "Good");
    }
	// @Test(expected = IndexOutOfBoundsException.class)
	// public void testGetBookingToBeCommentInvalidIndex() {
	// server.getBookingToBeComment(customer, 3, LocalDate.now()); // Out of bounds
	// }
	
	
//	@Test
//	public void testCheckHasAttendBookingArrived() {
//	LocalDate differentDate = LocalDate.of(2023, 11, 26);
//	LocalDate bookingDate=LocalDate.now();
//	Booking booking1 = new Booking(differentDate,1,"12:00-13:00",restaurant,customer,customer.getCustomerContact(),2);
//	booking1.takeAttendance(); // Assuming there’s a method to set the booking as arrived
//	   boolean result = server.checkHasAttend(customer, 1, LocalDate.now());
//	   assertTrue(result); // Should return true since the booking has arrived
//	}
	
	@Test 
	public void testavailableTableID() {
		String[] input = {"9", "4", "X"};
        setInput(input);
        restaurant.edit(scanner);
        server.addRestaurantAccount((Restaurant)restaurant);
		server.availableTableID(restaurant.getAccountUserName(), 1,"12:00 - 13:00",LocalDate.now());
	}
	
	@Test
	public void testgetviewbookingrecord() {
		LocalDate differentDate = LocalDate.of(2023, 11, 26);
		LocalDate bookingDate=LocalDate.now();
		Booking booking = new Booking(differentDate,1,"12:00-13:00",restaurant.getAccountName(),restaurant.getAccountUserName(),customer.getAccountName(),customer.getAccountContact(),2);
		restaurant.addBooking(booking);
		customer.addBooking(booking);
		int cusbooking =server.getViewBookingRecord(customer.getAccountUserName(), bookingDate);
		int restbooking = server.getViewBookingRecord(restaurant.getAccountUserName(), bookingDate);
		assertEquals( 0, cusbooking);
		assertEquals( cusbooking, restbooking);
	}
    @Test
    public void generateAccountLog(){
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
        Booking booking = new Booking(thisWeekStartDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
        restaurant.addBooking(booking);
        restaurant.addBooking(new Booking(thisWeekStartDate, 1, "12:00-13:00", 
        "Test Restaurant", "user", "customer1", "123456789", 4));
        restaurant.addBooking(new Booking(thisWeekEndDate, 1, "12:00-13:00", 
        "Test Restaurant", "user", "customer1", "123456789", 4));
        restaurant.addBooking(new Booking(lastWeekEndDate, 1, "12:00-13:00", 
        "Test Restaurant", "user", "customer1", "123456789", 3));
    

        ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
        ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate));
    
        // Add comments for this week
        ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
        ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate)); // Use start date if today is not the end date
        ((Restaurant) restaurant).generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        server.generateAccountLog();
    }
   
        @Test
public void testCalAndSetRestaurantRank() {
            LocalDate thisWeekStartDate = LocalDate.now();
            LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
            LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
            LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
            Booking booking = new Booking(thisWeekStartDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
            restaurant.addBooking(booking);
            Restaurant temp = new Restaurant("Test ", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
    		server.addRestaurantAccount((Restaurant)temp);
    		((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
        
            // Add comments for this week
            ((Restaurant) temp).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
            ((Restaurant) temp).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));
//    		Booking bk1 =new Booking(thisWeekEndDate, 1, "12:00-13:00", 
//    	            "Test Restaurant", "user", "customer1", "123456789", 4);
//    		restaurant.addBooking(bk1);
//            restaurant.addBooking(new Booking(lastWeekEndDate, 1, "12:00-13:00", 
//            "Test Restaurant", "user", "customer1", "123456789", 3));
            server.generateAccountLog();
            server.calAndSetRestaurantRank(server.getRestaurantAccounts(),"lastWeekRate");
        }
        @Test
        public void testCalAndSetRestaurantRank2() {
                    LocalDate thisWeekStartDate = LocalDate.now();
                    LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
                    LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
                    LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
                    Booking booking = new Booking(thisWeekStartDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
                    restaurant.addBooking(booking);
                    Restaurant temp = new Restaurant("Test ", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
            		server.addRestaurantAccount((Restaurant)temp);
            		((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User2", "bad", 0, lastWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User2", "bad", 0, lastWeekStartDate));
                    // Add comments for this week
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));

                    server.generateAccountLog();
                    server.calAndSetRestaurantRank(server.getRestaurantAccounts(),"lastWeekRate");
        }
        
        @Test
        public void testCalAndSetRestaurantRank3() {
                    LocalDate thisWeekStartDate = LocalDate.now();
                    LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
                    LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
                    LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
                    Booking booking = new Booking(thisWeekStartDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
                    restaurant.addBooking(booking);
                    Restaurant temp = new Restaurant("Test ", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
            		server.addRestaurantAccount((Restaurant)temp);
            		((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User2", "bad", 0, lastWeekStartDate));
                    // Add comments for this week
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));

                    server.generateAccountLog();
                    server.calAndSetRestaurantRank(server.getRestaurantAccounts(),"lastWeekRate");
        }
        
        @Test
        public void testCalAndSetRestaurantRank4() {
                    LocalDate thisWeekStartDate = LocalDate.now();
                    LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
                    LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
                    LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
                    Booking booking = new Booking(thisWeekStartDate, 1, "12:00-13:00", restaurant.getAccountUserName(), restaurant.getAccountName(), "customer1", "123456789", 2);
                    restaurant.addBooking(booking);
                    Restaurant temp = new Restaurant("Test ", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
            		server.addRestaurantAccount((Restaurant)temp);
            		((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User2", "bad", 0, lastWeekStartDate));
                    // Add comments for this week
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
                    ((Restaurant) temp).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
                    ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate));

                    server.generateAccountLog();
                    server.calAndSetRestaurantRank(server.getRestaurantAccounts(),"lastWeekRate");
        }
   
   

       
            
            @Test
            public void testmergesort() {
            	server.mergeSort(null,"lastweekrate");
            }

            @Test
            public void testCalAndSetRestaurantRankWithTies() {
                // Setup: Create restaurants with tied last week rates via comments
                LocalDate lastWeekDate = LocalDate.now().minusDays(7);
                LocalDate lastweekstart =LocalDate.now().minusDays(14);
                LocalDate thisweekend =LocalDate.now().plusDays(14);

                Restaurant restaurantA = new Restaurant("Restaurant A", "1", "A", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
                restaurantA.addCommentInRestaurant(new Comment("User1", "Good", 4, lastWeekDate));

                Restaurant restaurantB = new Restaurant("Restaurant B", "2", "B", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
                restaurantB.addCommentInRestaurant(new Comment("User2", "Good", 4, lastWeekDate)); // Tie with restaurant A

                Restaurant restaurantC = new Restaurant("Restaurant C", "3", "C", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
                restaurantC.addCommentInRestaurant(new Comment("User3", "Average", 3, lastWeekDate));

                // Add restaurants to the server
                server.addRestaurantAccount(restaurantA);
                server.addRestaurantAccount(restaurantB);
                server.addRestaurantAccount(restaurantC);
                restaurant.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                restaurantA.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                restaurantB.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                restaurantC.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);

                // Calculate and set ranks based on last week rates
                server.calAndSetRestaurantRank(server.getRestaurantAccounts(), "lastWeekRate");

         
            }

            @Test
            public void testCalAndSetRestaurantRankEmptyList() {
                // Test with an empty restaurant list
                server.calAndSetRestaurantRank(new ArrayList<>(), "lastWeekRate");
                // No exceptions should be thrown, and nothing should happen
            }

            @Test
            public void testCalAndSetRestaurantRankSingleRestaurant() {
                // Create a single restaurant and add a rating via comment
                LocalDate lastWeekDate = LocalDate.now().minusDays(7);
                LocalDate lastweekstart =LocalDate.now().minusDays(14);
                LocalDate thisweekend =LocalDate.now().plusDays(14);
                
                Restaurant restaurantA = new Restaurant("Restaurant A", "1", "A", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
                restaurantA.addCommentInRestaurant(new Comment("User1", "Good", 4, lastWeekDate));
               
                // Add the single restaurant to the server
                server.addRestaurantAccount(restaurantA);
                restaurant.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                restaurantA.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                server.calAndSetRestaurantRank(server.getRestaurantAccounts(), "lastWeekRate");

                // Assertion to verify ranking
            }

            @Test
            public void testCalAndSetRestaurantRankWithNoRankedRates() {
                // Create restaurants with no last week rates set via comments
                LocalDate lastWeekDate = LocalDate.now().minusDays(7);
                LocalDate lastweekstart =LocalDate.now().minusDays(14);
                LocalDate thisweekend =LocalDate.now().plusDays(14);
                
                Restaurant restaurantA = new Restaurant("Restaurant A", "1", "A", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
                restaurantA.addCommentInRestaurant(new Comment("User1", "Bad", 0, lastWeekDate));

                Restaurant restaurantB = new Restaurant("Restaurant B", "2", "B", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
                restaurantB.addCommentInRestaurant(new Comment("User2", "Terrible", 0, lastWeekDate)); // Both should be considered tied

                // Add restaurants to the server
                server.addRestaurantAccount(restaurantA);
                server.addRestaurantAccount(restaurantB);
                restaurant.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                restaurantA.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);
                restaurantB.generateRestaurantLogWithoutRank(lastweekstart, lastWeekDate, LocalDate.now(), thisweekend);

                // Calculate and set ranks
                server.calAndSetRestaurantRank(server.getRestaurantAccounts(), "lastWeekRate");

                // Assertions to verify that both are tied
            }

        
        
            // Add comments for last week
//            ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User1", "Excellent!", 5, lastWeekStartDate));
//            ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User2", "Not bad", 3, lastWeekStartDate));
//        
//            // Add comments for this week
//            ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User3", "Great", 4, thisWeekStartDate));
//            ((Restaurant) restaurant).addCommentInRestaurant(new Comment("User4", "Good",0, thisWeekEndDate)); // Use start date if today is not the end date
//            ((Restaurant) restaurant).generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
//    // Setup: Create restaurants with different rates for last week and this week
//    Restaurant restaurantA = new Restaurant("A", "1", "A", "Cuisine", "District", "Address", "Contact", null, null, null, 5);
//     thisWeekLog = new RestaurantLog(0, thisWeekRate, thisWeekTotalPpl, thisWeekComments);
//        lastWeekLog = new RestaurantLog(0, lastWeekRate, lastWeekTotalPpl, lastWeekComments);
//    restaurantA.setRestaurantLastWeekRate(4.5);
//    restaurantA.setRestaurantThisWeekRate(4.0);
////    
//   Restaurant restaurantB = new Restaurant("B", "2", "B", "Cuisine", "District", "Address", "Contact", null, null, null, 5);
//    restaurantB.setRestaurantLastWeekRate(3.5);
//    restaurantB.setRestaurantThisWeekRate(5.0);
///    
//    Restaurant restaurantC = new Restaurant("C", "3", "C", "Cuisine", "District", "Address", "Contact", null, null, null, 5);
//    restaurantC.setRestaurantLastWeekRate(4.5); // Tie with restaurantA
//    restaurantC.setRestaurantThisWeekRate(4.0); // Tie with restaurantA
//                 ArrayList<Account> accounts;
////
////    // Add restaurants to the accounts list
//    accounts.add(restaurantA);
//    accounts.add(restaurantB);
//    accounts.add(restaurantC);
////
////    // Test ranking by last week rate
//    server.calAndSetRestaurantRank(accounts, "lastWeekRate");
//    assertEquals(1, restaurantA.getRestaurantLastWeekRank());
//    assertEquals(2, restaurantB.getRestaurantLastWeekRank());
//    assertEquals(1, restaurantC.getRestaurantLastWeekRank()); // Tied with A
////
////    // Clear previous rankings
//    accounts.forEach(account -> account.setRestaurantLastWeekRank(0));
////
////    // Test ranking by this week rate
//    server.calAndSetRestaurantRank(accounts, "thisWeekRate");
//    assertEquals(2, restaurantA.getRestaurantThisWeekRank()); // Lower rate than B
//    assertEquals(1, restaurantB.getRestaurantThisWeekRank()); // Highest rate
//    assertEquals(2, restaurantC.getRestaurantThisWeekRank()); // Tied with A
////
////    // Test with all equal rates
//    Restaurant restaurantD = new Restaurant("D", "4", "D", "Cuisine", "District", "Address", "Contact", null, null, null, 5);
//    restaurantD.setRestaurantLastWeekRate(4.0);
//    restaurantD.setRestaurantThisWeekRate(4.0);
////    
//    accounts.add(restaurantD);
////
////    // Test ranking when all rates are equal
//    server.calAndSetRestaurantRank(accounts, "lastWeekRate");
//    assertEquals(1, restaurantA.getRestaurantLastWeekRank());
//    assertEquals(1, restaurantB.getRestaurantLastWeekRank());
//    assertEquals(1, restaurantC.getRestaurantLastWeekRank());
//    assertEquals(1, restaurantD.getRestaurantLastWeekRank()); // All tied
////
////    // Test with empty account list
//    server.calAndSetRestaurantRank(new ArrayList<>(), "lastWeekRate"); // Should not throw any exceptions
////
////    // Test with a single account
//    ArrayList<Account> singleAccountList = new ArrayList<>(); 
//    singleAccountList.add(restaurantA);
//    server.calAndSetRestaurantRank(singleAccountList, "lastWeekRate");
//    assertEquals(1, restaurantA.getRestaurantLastWeekRank()); // Should rank as 1

    }
    
//}

//	@Test
//	public void testCheckHasAttendBookingArrived_NoBooking() {
//	    LocalDate bookingDate = LocalDate.now();
//	    boolean result = server.checkHasAttend(customer, 1, bookingDate);
//	    assertFalse(result); // Should return false since there are no bookings
//	}
//
//	@Test
//	public void testCheckHasAttendBookingArrived_BookingNotArrived() {
//	    LocalDate bookingDate = LocalDate.now();
//	    Booking booking = new Booking(bookingDate, 1, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//	    restaurant.addBooking(booking); // Add booking without marking attendance
//	    
//	    boolean result = server.checkHasAttend(customer, 1, bookingDate);
//	    assertFalse(result); // Should return false since the booking has not been attended
//	}
//
//	@Test
//	public void testCheckHasAttendBookingArrived_InvalidCustomer() {
//	    LocalDate bookingDate = LocalDate.now();
//	    Booking booking = new Booking(bookingDate, 1, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//	    booking.takeAttendance();
//	    restaurant.addBooking(booking);
//	    
//	    // Check attendance for a different customer
//	    Customer differentCustomer = new Customer("DifferentUser", "password", "Different Name", "987654321");
//	    boolean result = server.checkHasAttend(differentCustomer, 1, bookingDate);
//	    assertFalse(result); // Should return false since the booking is not for this customer
//	}
//
//	@Test
//	public void testCheckHasAttendBookingArrived_BookingOnDifferentDate() {
//	    LocalDate bookingDate = LocalDate.now();
//	    LocalDate differentDate = bookingDate.plusDays(1);
//	    Booking booking = new Booking(bookingDate, 1, "12:00-13:00", restaurant, customer, customer.getCustomerContact(), 2);
//	    booking.takeAttendance();
//	    restaurant.addBooking(booking);
//	    
//	    // Check attendance for the same booking but on a different date
//	    boolean result = server.checkHasAttend(customer, 1, differentDate);
//	    assertFalse(result); // Should return false since the date is different
//	}
////	
////	@Test
////	public void testCheckHasAttendBookingNotArrived() {
////	boolean result = server.checkHasAttend(customer, 1, LocalDate.now());
////	assertFalse(result); // Should return false since the booking has not arrived
////	}
//   
////
//////    @Test
//////    public void testMakeBooking() {
//////        server.addRestaurantAccount(restaurant);
//////        server.addRestaurantAccount(customer);
//////        Booking booking = new Booking(LocalDate.now(), 1, "18:00", restaurant, customer, "123456789", 4);
//////        String result = server.makeBooking(LocalDate.now(), 1, "18:00", restaurant, customer, "123456789", 4);
//////        assertEquals("\nAlready booked a seat for you", result);
//////    }
////
////    @Test
////    public void testGenerateWeeklyReport() {
////        server.addRestaurantAccount(restaurant);
////        // Assume bookings and comments have been added to restaurant for this test
////        server.generateWeeklyReport(restaurant); // Check output manually or capture output stream for assertions
////    }
////
////    @Test
////    public void testGetRestaurantLog() {
////        server.addRestaurantAccount(restaurant);
////        server.generateRestaurantLog(); // Generate log data
////        RestaurantLog log = server.getRestaurantLog(restaurant, "thisWeek");
////        assertNotNull(log);
////    }
////
////    @Test
////    public void testGetPeriodBookings() {
////        server.addRestaurantAccount(restaurant);
////        // Assume bookings are added here
////        LocalDate today = LocalDate.now();
////        assertFalse(server.getPeriodBookings(restaurant, today, today).isEmpty());
////    }
////
////    @Test
////    public void testTakeAttendance() {
////        server.addRestaurantAccount(restaurant);
////        Booking booking = new Booking(LocalDate.now(), 1, "18:00", restaurant, customer, "123456789", 4);
////        restaurant.addBooking(booking);
////        assertTrue(server.takeAttendance(restaurant, LocalDate.now(), "18:00", 1));
////    }
////
////    @Test
////    public void testAddRestaurantAccount() {
////        server.addRestaurantAccount(restaurant);
////        assertTrue(server.getRestaurantAccounts().contains(restaurant));
////    }
////
//////    @Test
//////    public void testGenerateRestaurantLogData() {
//////        server.addRestaurantAccount(restaurant);
//////        server.generateRestaurantLogData(); // Testing internal logic
//////        assertNotNull(server.getRestaurantLog(restaurant, "thisWeek"));
//////    }
////
////    // Add more tests as necessary for other methods in Server
//
//}

//	
