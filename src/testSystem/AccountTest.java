package testSystem;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import system.Account;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import system.Role;
import system.Permission;
import system.Customer;
import system.Resource;
import system.Privilege;

import java.util.Scanner;


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
        account.setPermissions(Arrays.asList(permission)); // Ensure permissions are set
    }

    @Test
    public void testHasPermissionGranted() {
        assertTrue(account.hasPermission(Resource.PROFILE, Privilege.READ));
    }

    @Test
    public void testHasPermissionDenied() {
        assertFalse(account.hasPermission(Resource.PROFILE, Privilege.CREATE));
    }

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
        assertEquals(1, permissions.size());
        assertEquals(permission, permissions.get(0));
    }

    @Test
    public void testGetId() {
    	System.out.println("This is the new id: " + account.getId());
        assertEquals(7, account.getId());
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
    public void testSetPermissions() {
        Permission newPermission = new Permission(Role.CUSTOMER, Resource.PROFILE, Collections.singleton(Privilege.CREATE));
        account.setPermissions(Arrays.asList(newPermission));
        assertEquals(1, account.getAccountPermissions().size());
        assertEquals(newPermission, account.getAccountPermissions().get(0));
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


    @Test
    public void testHasNoPermissions() {
        // Test with an account that has no permissions
        Account emptyAccount = new Customer("user", "password", "John Doe", "123456789");
        assertTrue(emptyAccount.hasPermission(Resource.PROFILE, Privilege.READ));
    }
    
    @Test
    public void testHasPermissions() {
        // Test with an account that has no permissions
        Account emptyAccount = new Customer("user", "password", "John Doe", "123456789");
        assertFalse(emptyAccount.hasPermission(Resource.PROFILE, Privilege.DELETE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRoles_Null() {
        account.setRoles(null); // Test setting roles to null
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPermissions_Null() {
        account.setPermissions(null); // Test setting permissions to null
    }

    @Test
    public void testEdit_InvalidInput() {
        // Test editing with invalid input (assuming edit can handle invalid input)
        Scanner scanner = new Scanner("1\n\nX"); // Provide empty name
        account.edit(scanner);
        assertNotEquals("", account.getAccountUserName()); // Ensure the name isn't set to empty
    }
    
    
    
}