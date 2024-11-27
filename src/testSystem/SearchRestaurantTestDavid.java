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


public class SearchRestaurantTestDavid {
    @Test
    public void test_search_exact_name1() {
        Restaurant restaurant1 = new Restaurant("timeat",
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

        Restaurant restaurant2 = new Restaurant("ericeat",
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

        Restaurant restaurant3 = new Restaurant("dinadine",
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

        // ArrayList<Restaurant> restList = new ArrayList<>();
        // restList.add(restaurant1);
        // restList.add(restaurant2);
        // restList.add(restaurant3);

        Customer customer = new Customer("username", "pass", "tim", "123456789");
        Server server = Server.getInstance();
        server.addRestaurantAccount(restaurant1);
        server.addRestaurantAccount(restaurant2);
        server.addRestaurantAccount(restaurant3);
                
        SearchRestaurant search = new SearchRestaurant(customer);
        String user_input = "Dina Dine\nnull\nnull\nnull\nnull\nnull\nbull\nnull\nX";
        Scanner in = new Scanner(user_input);
        ArrayList<Restaurant> result = search.displaySearchRestaurnt(in);
        assertEquals(restaurant3, result.get(0));
        //assertEquals(restList.get(1), result.get(1));
        //assertEquals(restList.get(2), result.get(2));
    }

    @Test
    public void test_rating_range() {
        Restaurant restaurant1 = new Restaurant("timeat",
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

        Restaurant restaurant2 = new Restaurant("ericeat",
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

        Restaurant restaurant3 = new Restaurant("dinadine",
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

        // ArrayList<Restaurant> restList = new ArrayList<>();
        // restList.add(restaurant1);
        // restList.add(restaurant2);
        // restList.add(restaurant3);

        Customer customer = new Customer("username", "pass", "tim", "123456789");
        Server server = Server.getInstance();
        server.addRestaurantAccount(restaurant1);
        server.addRestaurantAccount(restaurant2);
        server.addRestaurantAccount(restaurant3);
                
        SearchRestaurant search = new SearchRestaurant(customer);
        String user_input = "Dina Dine\nnull\nnull\nnull\nnull\nnull\nbull\nnull\nX";
        Scanner in = new Scanner(user_input);
        ArrayList<Restaurant> result = search.displaySearchRestaurnt(in);
        assertEquals(restaurant3, result.get(0));
        //assertEquals(restList.get(1), result.get(1));
        //assertEquals(restList.get(2), result.get(2));
    }
}
