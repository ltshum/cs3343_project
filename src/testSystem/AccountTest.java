package testSystem;

import acm.Permission;
import acm.PermissionRegistry;
import acm.Privilege;
import acm.Resource;
import acm.Role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import system.Account;
import system.Booking;
import system.Comment;
import system.Customer;
import system.Restaurant;
import system.Table;

public class AccountTest {
    InputStream systemIn = System.in;
    private Account accountCustomer;
    private Account accountRestaurant;
    Scanner scanner;

    @Before
    public void setUp() {
        // Set up roles and permissions

        // Create an instance of Account (using a concrete subclass, e.g., Customer)
        accountCustomer = new Customer("user", "password", "John Doe", "123456789");
        accountRestaurant = new Restaurant("username",
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
        
    }

    @After
    public void tearDown() {
    	if(scanner!=null) {
        scanner.close();
    	}
        System.setIn(systemIn);
    }

    public void setInput(String[] in) {
        scanner = testInput.input(in);

    }

//    @Test
//    public void testHasPermissionGranted() {
//        assertTrue(account.hasPermission(Resource.PROFILE, Privilege.READ));
//    }

//    @Test
//    public void testHasPermissionDenied() {
//        assertFalse(account.hasPermission(Resource.PROFILE, Privilege.CREATE));
//    }

    @Test
    public void testGetRoles() {
        List<Role> roles = accountCustomer.getRoles();
        assertEquals(1, roles.size());
        assertEquals(Role.CUSTOMER, roles.get(0));
    }

    @Test
    public void testGetUserName() {
        assertEquals("user", accountCustomer.getAccountUserName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", accountCustomer.getAccountPassword());
    }

    @Test
    public void testGetPermissions() {
        String name=accountRestaurant.getAccountName();
        System.out.println("This is the role of the restaurant "+accountRestaurant.getRoles());
        List<Role> roles = new ArrayList<>();
        List<Permission> permissions = accountRestaurant.getAccountPermissions();
        System.out.println("This is the permission string "+accountRestaurant.getAccountPermissionsString(1));
        assertEquals(4, permissions.size());
    }
    @Test
    public void testgetAccountContact(){
        assertEquals("12345678",accountRestaurant.getAccountContact());
        assertEquals("123456789",accountCustomer.getAccountContact());
    }

    @Test
    public void testsetAccountContact(){
        accountCustomer.setAccountContact("NewContact");
        accountRestaurant.setAccountContact("NewContact2");
        assertEquals("NewContact",accountCustomer.getAccountContact());
        assertEquals("NewContact2",accountRestaurant.getAccountContact());
    }
    


    @Test
    public void testGetId() {
        System.out.println("This is the old id: " + accountCustomer.getId());
        accountCustomer.setId(5);
        assertEquals(5, accountCustomer.getId());
    }

    @Test
    public void testSetUserName() {
        accountCustomer.setUserName("newUser");
        assertEquals("newUser", accountCustomer.getAccountUserName());
    }

    @Test
    public void testSetPassword() {
        accountCustomer.setPassword("newPassword");
        assertEquals("newPassword", accountCustomer.getAccountPassword());
    }

    @Test
    public void testSetRoles() {
        List<Role> newRoles = Arrays.asList(Role.CUSTOMER);
        accountCustomer.setRoles(newRoles);
        assertEquals(newRoles, accountCustomer.getRoles());
    }

  

    @Test
    public void testSetId() {
        accountCustomer.setId(3);
        assertEquals(3, accountCustomer.getId());
    }

    @Test
    public void testStaticGetIdCounter() {
        System.out.println("This is the idcounter: "+Account.getIdCounter());
    }

    @Test
    public void testStaticSetIdCounter() {
        Account.setIdCounter(10);
        assertEquals(10, Account.getIdCounter());
    }

    @Test
    public void testGenerateLogWithoutRank_BothWeeksWithComments2() {
        LocalDate thisWeekStartDate = LocalDate.now().plusDays(7);
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(14);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        accountRestaurant.addCommentInAccount(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        accountRestaurant.addCommentInAccount(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));

        accountRestaurant.generateRestaurantLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(4.0f, accountRestaurant.getRestaurantLastWeekRate(), 0.01); // Average (5 + 3) / 2
        assertEquals(0.0f, accountRestaurant.getRestaurantThisWeekRate(), 0.01); // Only one comment with rate 4
    }

//    @Test
//    public void testHasNoPermissions() {
//        // Test with an account that has no permissions
//        Account emptyAccount = new Customer("user", "password", "John Doe", "123456789");
//        assertTrue(emptyAccount.hasPermission(Resource.PROFILE, Privilege.READ));
//    }
//
//    @Test
//    public void testHasPermissions() {
//        // Test with an account that has no permissions
//        Account emptyAccount = new Customer("user", "password", "John Doe", "123456789");
//        assertFalse(emptyAccount.hasPermission(Resource.PROFILE, Privilege.DELETE));
//    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testSetRoles_Null() {
//        account.setRoles(null); // Test setting roles to null
//    }

    

    @Test
    public void testEdit_InvalidInput() {
        // Test editing with invalid input (assuming edit can handle invalid input)
        Scanner scanner = new Scanner("1\n\nX"); // Provide empty name
        accountCustomer.edit(scanner);
        assertNotEquals("", accountCustomer.getAccountUserName()); // Ensure the name isn't set to empty
    }
    

    @Test
    public void testGetAccountContact() {
        assertEquals("12345678", accountRestaurant.getAccountContact());
        assertEquals("123456789", accountCustomer.getAccountContact());
    }

    @Test
    public void testSetAccountContact() {
        accountCustomer.setAccountContact("NewContact");
        accountRestaurant.setAccountContact("NewContact2");
        assertEquals("NewContact", accountCustomer.getAccountContact());
        assertEquals("NewContact2", accountRestaurant.getAccountContact());
    }

   

  

    @Test
    public void testGenerateLogWithoutRank_BothWeeksWithComments() {
        LocalDate thisWeekStartDate = LocalDate.now().plusDays(7);
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(6);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);

        // Add comments for both weeks
        accountRestaurant.addCommentInAccount(new Comment("User1", "Excellent!", 5, lastWeekStartDate.plusDays(1)));
        accountRestaurant.addCommentInAccount(new Comment("User2", "Not bad", 3, lastWeekStartDate.plusDays(2)));

        accountRestaurant.generateRestaurantLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);

        assertEquals(4.0f, accountRestaurant.getRestaurantLastWeekRate(), 0.01); // Average (5 + 3) / 2
        assertEquals(0.0f, accountRestaurant.getRestaurantThisWeekRate(), 0.01); // No comments for this week
    }

    @Test
    public void testGetCommentRestaurantUserName() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00", 
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(), 
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        String restaurantUserName = accountCustomer.getCommentRestaurantUserName(1, bookingDate);
        assertEquals(accountRestaurant.getAccountUserName(), restaurantUserName);
    }

   

    @Test
    public void testGetAllBookings() {
        assertTrue(accountCustomer.getAllBookings().isEmpty());
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00", 
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(), 
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);
        assertFalse(accountCustomer.getAllBookings().isEmpty());
        assertEquals(1, accountCustomer.getAllBookings().size());
    }

    

    @Test
public void testGetCommentRestaurantUserNameWithNoBookings() {
    LocalDate bookingDate = LocalDate.now();
    assertNull(accountCustomer.getCommentRestaurantUserName(1, bookingDate)); // Should return null if no bookings exist
}

    @Test
    public void testGetCommentRestaurantUserNameWithInvalidInputNumber() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        String restaurantUserName = accountCustomer.getCommentRestaurantUserName(1, bookingDate);
        assertEquals("username",restaurantUserName);
    }

   

    @Test
    public void testAddBooking() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00", 
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(), 
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);
        assertEquals(1, accountCustomer.getAllBookings().size());
    }
    @Test
    public void setRestaurantLastWeekRank(){
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
        Restaurant restaurant = (Restaurant) accountRestaurant;

        // Add a booking for last week
        accountRestaurant.addBooking(new Booking(lastWeekStartDate.plusDays(1), 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        accountRestaurant.setRestaurantLastWeekRank(9);
        assertEquals(9,accountRestaurant.getRestaurantLastWeekRank());
    }

    @Test
    public void setRestaurantThisWeekRank(){

        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
        Restaurant restaurant = (Restaurant) accountRestaurant;

        // Add a booking for last week
        accountRestaurant.addBooking(new Booking(lastWeekStartDate.plusDays(1), 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        accountRestaurant.setRestaurantThisWeekRank(9);
        assertEquals(9,accountRestaurant.getRestaurantThisWeekRank());
    }

    @Test
    public void testgenerateRestaurantWeeklyReport(){
        LocalDate thisWeekStartDate = LocalDate.now();
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(7);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
        Restaurant restaurant = (Restaurant) accountRestaurant;

        // Add a booking for last week
        accountRestaurant.addBooking(new Booking(lastWeekStartDate.plusDays(1), 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));

        restaurant.generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        accountRestaurant.generateRestaurantWeeklyReport();
    }

    @Test
    public void testtakeAttenanceInAccount(){
    
    LocalDate date = LocalDate.now();
        // Add a booking
        accountRestaurant.addBooking(new Booking(date, 1, "12:00-13:00", 
                                           "Test Restaurant", "user", "customer1", "123456789", 4));
        accountCustomer.addBooking(new Booking(date, 1, "12:00-13:00", 
        "Test Restaurant", "user", "customer1", "123456789", 4));        // Mark attendance
        boolean attendanceMarked = accountRestaurant.takeAttenanceInAccount(date, "12:00-13:00", 1);        assertEquals(true,attendanceMarked);
        assertFalse(accountCustomer.checkHasAttendInAccount(1,date));

    }

    @Test
    public void testAvailableTableID_NoTablesAvailable() {
    	LocalDate bookingDate = LocalDate.now();
        int requiredPeople = 4;
        String timeslot = "10:00 - 11:00";
        // Given: No tables available
//        restaurant.setAllTables(new ArrayList<>());

        // When: Checking for available table ID
        int availableTableID = accountRestaurant.availableTableIDInAccount(requiredPeople, timeslot, bookingDate);

        // Then: Should return 0 (indicating no available table)
        assertEquals(0, availableTableID);
    }
    
    @Test
    public void testAvailableTableID_MultipleTables1() {
        // Given: Multiple tables, one suitable
        Table table1 = new Table(1);
        table1.setSeatNum(2); // Not enough seats
        table1.addTimeslot("10:00 - 11:00");
        accountRestaurant.getAccountAllTables().add(table1);
        
        Table table2 = new Table(2);
        table2.setSeatNum(4); // Enough seats
        table2.addTimeslot("10:00 - 11:00");
        accountRestaurant.getAccountAllTables().add(table2);

        // When: Checking for available table ID
        int availableTableID = accountRestaurant.availableTableIDInAccount(4, "10:00 - 11:00", LocalDate.now());

        // Then: Should return the ID of the suitable table
        assertEquals(2, availableTableID);
    }

    @Test
    public void testgetAccountPermissionsString(){
    	System.out.println("This is the string I want "+accountRestaurant.getAccountPermissionsString(1));
        assertEquals("PROFILE",
        accountRestaurant.getAccountPermissionsString(1));
        assertEquals( "PROFILE",
        accountCustomer.getAccountPermissionsString(1));
    }
    @Test
    public void testgetAccountDistrict(){
        assertEquals("district",accountRestaurant.getAccountDistrict());
    }

    @Test
    public void testgetAccountRate(){
        assertEquals(0.0f,accountRestaurant.getAccountRate(),0.01);
    }
    @Test
    public void testgetAccountType(){
        assertEquals("type",accountRestaurant.getAccountType());
    }
    @Test
    public void testtableValidation() {
    	assertTrue(accountRestaurant.tableValidationInAccount(1));
    	assertFalse(accountRestaurant.tableValidationInAccount(5));
    }
    
    @Test
    public void testGetRestaurantAllTableInfo() {
        // Expected output string
        StringBuilder expected = new StringBuilder();
        expected.append("                | Table ID: 1             | Table ID: 2             | Table ID: 3             \n");
        expected.append("                | Seat: 0                 | Seat: 0                 | Seat: 0                 \n");

        // Get actual output from the method
        StringBuilder actual = accountRestaurant.getAccountAllTableInfo();

        // Assert that the expected output matches the actual output
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testupdateRestaurantTableInfo() {
    	   String[] input = {"8", "X"};
           setInput(input);
           accountRestaurant.updateAccountTableInfo(scanner,1);
           assertEquals(8,accountRestaurant.getAccountAllTables().get(0).getSeatNum());
           String[] input2 = {"-1","10", "X"};
           setInput(input2);
           accountRestaurant.updateAccountTableInfo(scanner,1);
           assertEquals(10,accountRestaurant.getAccountAllTables().get(0).getSeatNum());
           String[] input3 = {"Hi","10", "X"};
           setInput(input3);
           accountRestaurant.updateAccountTableInfo(scanner,1);
           assertEquals(10,accountRestaurant.getAccountAllTables().get(0).getSeatNum());
    }


    @Test
    public void testGetCommentRestaurantUserName_ValidInput() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        String restaurantUserName = accountCustomer.getCommentRestaurantUserName(1, bookingDate);
        assertEquals(accountRestaurant.getAccountUserName(), restaurantUserName);
    }

    @Test
    public void testGetCommentRestaurantUserName_NoBookings() {
        LocalDate bookingDate = LocalDate.now();
        assertNull(accountCustomer.getCommentRestaurantUserName(1, bookingDate));
    }

    @Test
    public void testGetCommentRestaurantUserName_InvalidInputNumber_Zero() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        assertNull(accountCustomer.getCommentRestaurantUserName(0, bookingDate));
    }

    @Test
    public void testGetCommentRestaurantUserName_InvalidInputNumber_Negative() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        assertNull(accountCustomer.getCommentRestaurantUserName(-1, bookingDate));
    }

    @Test
    public void testGetCommentRestaurantUserName_InputNumberGreaterThanBookings() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        assertNull(accountCustomer.getCommentRestaurantUserName(2, bookingDate)); // Exceeds the number of bookings
    }

    @Test
    public void testGetCommentRestaurantUserName_InputNumberEqualToBookings() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);

        assertNull(accountCustomer.getCommentRestaurantUserName(2, bookingDate)); // Exceeds the number of bookings
    }

    @Test
    public void testGetCommentRestaurantUserName_MultipleBookings() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        Booking booking2 = new Booking(bookingDate, 2, "13:00-14:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 3);
        accountCustomer.addBooking(booking1);
        accountCustomer.addBooking(booking2);

        String restaurantUserName = accountCustomer.getCommentRestaurantUserName(2, bookingDate);
        assertEquals(accountRestaurant.getAccountUserName(), restaurantUserName);
    }
    @Test
    public void testGetCommentRestaurantUserNamenotequal() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking1 = new Booking(bookingDate, 1, "12:00-13:00",
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(),
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        
        accountCustomer.addBooking(booking1);
        LocalDate bookingDate2 = LocalDate.now().plusDays(2);


        String restaurantUserName = accountCustomer.getCommentRestaurantUserName(2, bookingDate2);
        assertNull(restaurantUserName);
    }
    
    @Test
    public void testgetgetAccountTimeslots() {
    	String res =
                "10:00 - 11:00 \n" +
                "11:00 - 12:00 \n" +
                "12:00 - 13:00 \n" +
                "13:00 - 14:00 \n" +
                "14:00 - 15:00 \n" +
                "15:00 - 16:00 \n" +
                "16:00 - 17:00 \n" +
                "17:00 - 18:00 \n" +
                "18:00 - 19:00 \n"+
                "19:00 - 20:00 \n";
    	assertEquals(res,accountRestaurant.getAccountTimeslots());
    }
    @Test
    public void testgetAccountPermissionNumber() {
    	assertEquals(5,accountRestaurant.getAccountPermissionNumber());
    	assertEquals(4,accountCustomer.getAccountPermissionNumber());

    }
    
}
