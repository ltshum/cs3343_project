package testSystem;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import View.SearchRestaurant;
import system.Customer;
import system.Restaurant;
import system.Server;

public class SearchRestaurantTest {
    
    Server server = Server.getInstance();
    private Restaurant restaurant ;
    private Customer customer;
    @Before
    public void setUp() {
        server = Server.getInstance();
        restaurant = new Restaurant("TestRestaurant", "1", "Test", "Cuisine", "District", "Address", "Contact", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 5);
        customer = new Customer("TestUser", "password", "Test Name", "123456789");
        server.addAccount(restaurant); 
        server.addAccount(customer);
        server.addRestaurantAccount((Restaurant)restaurant);
   }

    @Test
    public void TestAllNonNullData(){
        String[] in = {"name\ndistrict\ntype\n3\n6\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }

    @Test
    public void TestAllNullData(){
        String[] in = {"null\null\null\nnull\nnull\nnull\nnull\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }

    @Test
public void TestIsValidRatingSingleInRange1() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("3");
    assertTrue(result);
}

@Test
public void TestIsValidRatingSingleInRange2() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("5");
    assertTrue(result);
}

@Test
public void TestIsValidRatingSingleOutOfRange1() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("6");
    assertFalse(result);
}

@Test
public void TestIsValidRatingSingleOutOfRange2() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("-1");
    assertFalse(result);
}

@Test
public void TestIsValidRatingSingleInvalidFormat() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("-");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeValid1() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0-0");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeValid2() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("1-3");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeValid3() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("5-5");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeValid4() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0-5");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeInvalid1() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("3-1");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalid2() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0-6");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalid3() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("6-0");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalid4() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("1-10");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalidFormat1() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("012");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalidFormat2() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0--");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalidFormat3() {
    String customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("--5");
    assertFalse(result);
}
    
    @Test
    public void TestInvalidppl(){
        String[] in = {"name\ndistrict\ntype\n2\ninvalid\n2\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }  

    @Test
    public void TestInvalidTime(){
        String[] in = {"name\ndistrict\ntype\n2\n6\ninvalid\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }

    @Test
    public void TestInvalidDuration(){
        String[] in = {"name\ndistrict\ntype\n2\n6\n09:00\ninvalid\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }
    
    @Test
    public void TestInvalidRating(){
        String[] in = {"name", "district", "type", "invalid", "2", "6", "09:00", "60", "X", "4", "3"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }
    
//    @Test
//    public void TestAllNonNullData(){
//        String[] in = {"name\ndistrict\ntype\n3\n6\n09:00\n60\nX\n4\n3\n"};
//        Scanner input = testInput.input(in);
//        String customer = server.signIn("1", "1");
//        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
//           searchrestaurant.displaySearchRestaurnt(input);
//    }
}
