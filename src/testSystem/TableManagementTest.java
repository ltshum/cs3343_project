package testSystem;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import system.Server;
import system.Restaurant;
import system.Table;
import View.TableManagement;

public class TableManagementTest {

    Server server = Server.getInstance();
    Restaurant restaurant;
    TableManagement tableManagement;
    Scanner scanner;
	
    @Before
	public void setUp() throws Exception {
        server.signUp("RESTAURANT", "2", "2", "1", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        restaurant = (Restaurant) server.getAccountByUserName("2");
        for (Table table : restaurant.getRestaurantAllTables()) {
        	table.setSeatNum(0);
        }
        tableManagement = new TableManagement(restaurant.getAccountUserName());
    }
    
    @After
    public void tearDown() throws Exception {
    	scanner.close();
    }

    public void setInput(String[] in) {
        scanner = testInput.input(in);
    }
    

    
    @Test
	public void TestUpdateTableInfo() {
		String[] in = { "1", "1", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
	}
    
    @Test
	public void TestUpdateTableInfo2() {
		String[] in = { "1", "2", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(0, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    	assertEquals(3, restaurant.getRestaurantAllTables().get(1).getSeatNum());
	}
    
    @Test
    public void TestBack() {
    	String[] in = { "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(0, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
    
    @Test
    public void TestOptionInvalidNumber() {
    	String[] in = { "3", "1", "1", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
    
    @Test
    public void TestOptionInvalidFormat() {
    	String[] in = { "invalid", "1", "1", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
    
    @Test
    public void TestTableNotExist() {
    	String[] in = { "1", "4", "1", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
    
    @Test
    public void TestTableInvalidFormat() {
    	String[] in = { "1", "invalid", "1", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
    
    @Test
    public void TestSeatInvalidNumber() {
    	String[] in = { "1", "1", "-1", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
    
    @Test
    public void TestSeatInvalidFormat() {
    	String[] in = { "1", "1", "invalid", "3", "2" };
    	setInput(in);
    	tableManagement.displayTableManagement(scanner);
    	assertEquals(3, restaurant.getRestaurantAllTables().get(0).getSeatNum());
    }
}
