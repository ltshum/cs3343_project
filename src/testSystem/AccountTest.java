package testSystem;

import acm.Permission;
import acm.Privilege;
import acm.Resource;
import acm.Role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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

public class AccountTest {
    InputStream systemIn = System.in;
    private Account accountCustomer;
    private Permission permission;
    private Account accountRestaurant;
    Scanner scanner;

    @Before
    public void setUp() {
        // Set up roles and permissions
        Role role = Role.CUSTOMER;
        permission = new Permission(role, Resource.PROFILE, Collections.singleton(Privilege.READ));

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
        List<Permission> permissions = accountCustomer.getAccountPermissions();
        assertEquals(3, permissions.size());
        Permission per=new Permission(Role.CUSTOMER, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE));
        assertEquals(per.getRole(), permissions.get(0).getRole());
        assertEquals(per.getResource(), permissions.get(0).getResource());
        assertEquals(per.getPrivileges(), permissions.get(0).getPrivileges());

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

        assertEquals(3.75f, accountRestaurant.getRestaurantLastWeekRate(), 0.01); // Average (5 + 3) / 2
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

        assertEquals(3.75f, accountRestaurant.getRestaurantLastWeekRate(), 0.01); // Average (5 + 3) / 2
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
    public void testAddBooking() {
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingDate, 1, "12:00-13:00", 
                accountRestaurant.getAccountName(), accountRestaurant.getAccountUserName(), 
                accountCustomer.getAccountName(), accountCustomer.getAccountContact(), 2);
        accountCustomer.addBooking(booking);
        assertEquals(1, accountCustomer.getAllBookings().size());
    }

}
