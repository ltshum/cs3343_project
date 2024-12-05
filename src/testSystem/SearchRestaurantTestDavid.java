package testSystem;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.Home;
import View.RestaurantList;
import View.SearchRestaurant;
import system.Booking;
import system.Customer;
import system.Restaurant;
import system.Server;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import system.Account;


public class SearchRestaurantTestDavid {
    Restaurant restaurant1;
    Restaurant restaurant2;
    Restaurant restaurant3;
    Restaurant eat;
    Restaurant eateat;
    Restaurant ea;
    Customer customer;
    Server server = Server.getInstance();


    @Before
    public void setUp(){
        restaurant1 = new Restaurant("timeat",
            "password",
            "Tim Eat",
            "type",
            "district",
            "address",
            "12345678",
            (LocalTime.parse("10:00")),
            (LocalTime.parse("20:00")),
            Duration.ofMinutes(60),
            3);

        restaurant2 = new Restaurant("ericeat",
            "password",
            "Eric Eats",
            "type",
            "district",
            "address",
            "12345678",
            (LocalTime.parse("09:00")),
            (LocalTime.parse("21:00")),
            Duration.ofMinutes(120),
            3);

        restaurant3 = new Restaurant("dinadine",
            "password",
            "Dina Dine",
            "type",
            "district",
            "address",
            "12345678",
            (LocalTime.parse("10:30")),
            (LocalTime.parse("20:30")),
            Duration.ofMinutes(30),
            3);

        eat = new Restaurant("Eat",
            "password",
            "Eat",
            "type",
            "district",
            "address",
            "12345678",
            (LocalTime.parse("10:00")),
            (LocalTime.parse("20:00")),
            Duration.ofMinutes(60),
            3);
            
        eateat = new Restaurant("EatEat",
            "password",
            "EatEat",
            "type",
            "district",
            "address",
            "12345678",
            (LocalTime.parse("10:00")),
            (LocalTime.parse("20:00")),
            Duration.ofMinutes(60),
            3);
        ea = new Restaurant("ea",
            "password",
            "Ea",
            "type",
            "district",
            "address",
            "12345678",
            (LocalTime.parse("10:00")),
            (LocalTime.parse("20:00")),
            Duration.ofMinutes(60),
            3);
        customer = new Customer("username", "pass", "tim", "123456789");
    }


    @Test
    public void test_search_exact_name() {
        server.addRestaurantAccount(restaurant1);
        server.addRestaurantAccount(restaurant2);
        server.addRestaurantAccount(restaurant3);
        ArrayList<String> result = server.searchRestaurantsIn("Dina Dine", "null", "null", "null", "null", "null", "null");
        assertEquals(restaurant3.getAccountUserName(), result.get(0));
    }

    @Test
    public void test_search_name_keyword() {
        server.addRestaurantAccount(eat);
        server.addRestaurantAccount(eateat);
        server.addRestaurantAccount(ea);
        ArrayList<String> expected = new ArrayList<>();
        expected.add(eat.getAccountUserName());
        expected.add(eateat.getAccountUserName());
        expected.add(ea.getAccountUserName());
        
        ArrayList<String> result = server.searchRestaurantsIn("eat", "null", "null", "null", "null", "null", "null");
        assertEquals(expected, result);
    }

    @Test
    public void test_search_district_keyword() {
        server.addRestaurantAccount(eat);
        server.addRestaurantAccount(eateat);
        server.addRestaurantAccount(ea);
        ArrayList<String> expected = new ArrayList<>();
        expected.add(eat.getAccountUserName());
        expected.add(eateat.getAccountUserName());
        expected.add(ea.getAccountUserName());
        
        ArrayList<String> result = server.searchRestaurantsIn("eat", "null", "null", "null", "null", "null", "null");
        assertEquals(expected, result);
    }


    @Test
    public void test_rating_range() {
        restaurant1.setRestaurantRate((float)1.2);
        restaurant2.setRestaurantRate((float)4.5);
        restaurant3.setRestaurantRate((float)3.9);

        server.addRestaurantAccount(restaurant1);
        server.addRestaurantAccount(restaurant2);
        server.addRestaurantAccount(restaurant3);
        ArrayList<String> expected = new ArrayList<>();
        expected.add(restaurant2.getAccountUserName());
        expected.add(restaurant3.getAccountUserName());
                
        ArrayList<String> result = server.searchRestaurantsIn("null", "null", "2--5", "null", "null", "null", "null");

        assertEquals(expected, result);
    }

    // @Test
    // public void test_valid_rating_null() {


    //     restaurant1.setRate((float)1.2);
    //     server.addRestaurantAccount(restaurant1);

        
                
    //     SearchRestaurant search = new SearchRestaurant(customer);
    //     String user_input = "null\nnull\nnull\n987\n2-5\nnull\nnull\nnull\nbull\nnull\nX";
    //     Scanner in = new Scanner(user_input);
    //     ArrayList<Restaurant> result = search.displaySearchRestaurnt(in);

        
    // }

    // @Test
    // public void test_valid_rating_singleDigit() {

    //     restaurant1.setRate((float)1.2);

    //     Customer customer = new Customer("username", "pass", "tim", "123456789");
    //     Server server = Server.getInstance();
    //     server.addRestaurantAccount(restaurant1);

        
                
    //     SearchRestaurant search = new SearchRestaurant(customer);
    //     String user_input = "null\nnull\nnull\n987\n2-5\n3\nnull\nnull\nbull\nnull\nX";
    //     Scanner in = new Scanner(user_input);
    //     ArrayList<Restaurant> result = search.displaySearchRestaurnt(in);

        
    // }
}
