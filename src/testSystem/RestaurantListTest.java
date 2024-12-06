package testSystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.RestaurantList;
import system.Customer;
import system.Restaurant;
import system.Server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;

public class RestaurantListTest {

    private Server server;
    private RestaurantList restaurantList;
    private Scanner scanner;
    private Restaurant restaurant;
    InputStream systemIn = System.in;
    private Customer customer ;

    @Before
    public void setUp() {
        server = Server.getInstance();
            restaurant = new Restaurant("TestRestaurant", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
            server.addAccount(restaurant); 
            server.addRestaurantAccount((Restaurant)restaurant);
            customer = new Customer("TestUser", "password", "Test Name", "123456789");
            server.addAccount(customer);
        ArrayList<String> result = new ArrayList<>();
        result.add("TestRestaurant"); // Mock restaurant usernames
        restaurantList = new RestaurantList(result);
    }
    @After
    public void tearDown() {
        scanner.close();
        System.setIn(systemIn);
    }

    public void setInput(String[] in) {
        scanner = testInput.input(in);

    }
    @Test
    public void testDisplayRestaurantListValidOption() {
        // Prepare input
        String []input = {"1","3","X"}; // Valid option to view restaurant 1 and then exit
        setInput(input);

        restaurantList.displayRestaurantList(scanner, "TestUser");

        // Verify interactions
    }

    @Test
    public void testDisplayRestaurantListInvalidOption() {
        // Prepare input
    	String []input = {"10","1","3","X"}; // Valid option to view restaurant 1 and then exit
        setInput(input);


        restaurantList.displayRestaurantList(scanner, "TestUser");

        // Verify that the loop handles invalid input correctly
    }
    
    @Test
    public void testDisplayRestaurantListInvalidOption2() {
        // Prepare input
    	String []input = {"10","hello","3","X"}; // Valid option to view restaurant 1 and then exit
        setInput(input);


        restaurantList.displayRestaurantList(scanner, "TestUser");

        // Verify that the loop handles invalid input correctly
    }
    @Test
    public void testDisplayRestaurantListInvalidOption3() {
        // Prepare input
    	String []input = {"10","hello","3","X"}; // Valid option to view restaurant 1 and then exit
        setInput(input);


        restaurantList.displayRestaurantList(scanner, "Testing");

        // Verify that the loop handles invalid input correctly
    }


    @Test
    public void testDisplayRestaurantListExitOption() {
    	String []input = {"X"}; // Valid option to view restaurant 1 and then exit
        setInput(input);

        restaurantList.displayRestaurantList(scanner, "TestUser");

        // Verify that scanner was called once for the exit input
    }

//    @Test
//    public void testDisplayRestaurantListWithNoRestaurants() {
//        // Create an empty restaurant list
//        ArrayList<String> emptyResult = new ArrayList<>();
//        restaurantList = new RestaurantList(emptyResult);
//        
//        // Prepare input to exit
//        String input = "X\n"; 
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        // Call the display method
//        restaurantList.displayRestaurantList(scanner, "customerUser");
//
//        // Verify that no errors occurred
//        assertNotNull(restaurantList);
//    }
}