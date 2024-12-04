package testSystem;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import View.Home;
import system.Account;
import system.Restaurant;
import system.Server;

public class HomeTest {
    
    Server server = Server.getInstance();
    
    String restaurant = server.signIn("2", "2");

    @Before
	public void setUp() {
		server.signUp("CUSTOMER", "1", "1", "1", "1", null, null, null, null, null, null, 0);
        server.signUp("RESTAURANT", "2", "2", "1", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
	}

    @Test
    public void TestCustomerHomeToProfile(){
        String[] in = {"1\n2\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestCustomerHomeToViewBooking(){
        String[] in = {"2\nX\n4\n3\n5\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestCustomerHomeToSearchRestaurant(){
        String[] in = {"3\nnull\nnull\nnull\nnull\nnull\n09:00\n60\nX\n4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestCustomerHomeToLogOut(){
        String[] in = {"4\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("1", "1");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestRestaurantToProfile(){
        String[] in = {"1\n2\n5\n3\n"};
        Scanner input = testInput.input(in);
        String restaurant = server.signIn("2", "2");
        Home home = new Home(restaurant);
        home.display(input);
    }

    @Test
    public void TestRestaurantToViewBooking(){
        String[] in = {"2\nX\n5\n3\n"};
        Scanner input = testInput.input(in);
        String restaurant = server.signIn("2", "2");
        Home home = new Home(restaurant);
        home.display(input);
    }

    @Test
    public void TestRestaurantToTableManagement(){
        String[] in = {"3\n2\n5\n3\n"};
        Scanner input = testInput.input(in);
        String restaurant = server.signIn("2", "2");
        Home home = new Home(restaurant);
        home.display(input);
    }

    @Test
    public void TestRestaurantToWeeklyReport(){
        String[] in = {"4\n1\n5\n3\n"};
         LocalDate thisWeekStartDate = LocalDate.now().plusDays(7);
        LocalDate thisWeekEndDate = thisWeekStartDate.plusDays(14);
        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(14);
        LocalDate lastWeekEndDate = thisWeekStartDate.minusDays(7);
        Scanner input = testInput.input(in);
        String restaurant = server.signIn("2", "2");
        Home home = new Home(restaurant);
        Account acc =server.getAccountByUserName("2");
        acc.generateRestaurantLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        home.display(input);
    }

    @Test
    public void TestRestaurantHomeToLogOut(){
        String[] in = {"5\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("2", "2");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestInvalidInput(){
        String[] in = {"1.1\n5\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("2", "2");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestInputNonNumber(){
        String[] in = {"n\n5\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("2", "2");
        Home home = new Home(customer);
        home.display(input);
    }

    @Test
    public void TestInputNonInteger(){
        String[] in = {"1.8\n5\n3\n"};
        Scanner input = testInput.input(in);
        String customer = server.signIn("2", "2");
        Home home = new Home(customer);
       
}
}