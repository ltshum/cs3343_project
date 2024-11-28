package testSystem;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import View.SearchRestaurant;
import system.Account;
import system.Server;

public class SearchRestaurantTest {
    
    Server server = Server.getInstance();

    @Test
    public void TestAllNonNullData(){
        String[] in = {"name\ndistrict\ntype\n3\n6\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        Account customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }

    @Test
    public void TestAllNullData(){
        String[] in = {"null\null\null\nnull\nnull\nnull\nnull\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        Account customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }

    @Test
public void TestIsValidRatingSingleInRange1() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("3");
    assertTrue(result);
}

@Test
public void TestIsValidRatingSingleInRange2() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("5");
    assertTrue(result);
}

@Test
public void TestIsValidRatingSingleOutOfRange1() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("6");
    assertFalse(result);
}

@Test
public void TestIsValidRatingSingleOutOfRange2() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("-1");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeValid1() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0-0");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeValid2() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("1-3");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeValid3() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("5-5");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeValid4() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0-5");
    assertTrue(result);
}

@Test
public void TestIsValidRatingRangeInvalid1() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("3-1");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalid2() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("0-6");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalid3() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("6-0");
    assertFalse(result);
}

@Test
public void TestIsValidRatingRangeInvalid4() {
    Account customer = server.signIn("1", "1");
    SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
    boolean result = searchrestaurant.isValidRating("1-10");
    assertFalse(result);
}
    
    @Test
    public void TestInvalidppl(){
        String[] in = {"name\ndistrict\ntype\n2\ninvalid\n2\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        Account customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }  

    @Test
    public void TestInvalidTime(){
        String[] in = {"name\ndistrict\ntype\n2\n6\ninvalid\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        Account customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }

    @Test
    public void TestInvalidDuration(){
        String[] in = {"name\ndistrict\ntype\n2\n6\n09:00\ninvalid\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        Account customer = server.signIn("1", "1");
        SearchRestaurant searchrestaurant = new SearchRestaurant(customer);
           searchrestaurant.displaySearchRestaurnt(input);
    }
}
