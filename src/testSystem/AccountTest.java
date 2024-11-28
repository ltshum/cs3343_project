package testSystem;

import acm.Permission;
import acm.Privilege;
import acm.Resource;
import acm.Role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import system.Account;
import system.Customer;

public class AccountTest {

    private Account account;
    private Permission permission;

    @Before
    public void setUp() {
        // Set up roles and permissions
        Role role = Role.CUSTOMER;
        permission = new Permission(role, Resource.PROFILE, Collections.singleton(Privilege.READ));

        // Create an instance of Account (using a concrete subclass, e.g., Customer)
        account = new Customer("user", "password", "John Doe", "123456789");
        
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
        List<Role> roles = account.getRoles();
        assertEquals(1, roles.size());
        assertEquals(Role.CUSTOMER, roles.get(0));
    }

    @Test
    public void testGetUserName() {
        assertEquals("user", account.getAccountUserName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", account.getAccountPassword());
    }

    @Test
    public void testGetPermissions() {
        List<Permission> permissions = account.getAccountPermissions();
        assertEquals(3, permissions.size());
        Permission per=new Permission(Role.CUSTOMER, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE));
        assertEquals(per.getRole(), permissions.get(0).getRole());
        assertEquals(per.getResource(), permissions.get(0).getResource());
        assertEquals(per.getPrivileges(), permissions.get(0).getPrivileges());

    }

    @Test
    public void testGetId() {
        System.out.println("This is the new id: " + account.getId());
        assertEquals(5, account.getId());
    }

    @Test
    public void testSetUserName() {
        account.setUserName("newUser");
        assertEquals("newUser", account.getAccountUserName());
    }

    @Test
    public void testSetPassword() {
        account.setPassword("newPassword");
        assertEquals("newPassword", account.getAccountPassword());
    }

    @Test
    public void testSetRoles() {
        List<Role> newRoles = Arrays.asList(Role.CUSTOMER);
        account.setRoles(newRoles);
        assertEquals(newRoles, account.getRoles());
    }

  

    @Test
    public void testSetId() {
        account.setId(3);
        assertEquals(3, account.getId());
    }

    @Test
    public void testStaticGetIdCounter() {
        assertEquals(2, Account.getIdCounter());
    }

    @Test
    public void testStaticSetIdCounter() {
        Account.setIdCounter(10);
        assertEquals(10, Account.getIdCounter());
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
        account.edit(scanner);
        assertNotEquals("", account.getAccountUserName()); // Ensure the name isn't set to empty
    }

}
