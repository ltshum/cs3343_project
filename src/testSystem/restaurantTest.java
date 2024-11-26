package testSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Scanner;

import system.Restaurant;
import java.time.Duration;
import java.time.LocalTime;

public class restaurantTest {
	
	// Preparation start
	InputStream systemIn = System.in;
	Restaurant restaurant;
	Scanner scanner;
	
	@BeforeEach
	public void setUp() throws Exception {
		restaurant = new Restaurant("username", 
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
	
	@AfterEach
	public void tearDown() throws Exception {
		scanner.close();
		System.setIn(systemIn);
	}
	
	public void setInput(String[] in) {
		scanner = testInput.input(in);
	}
	// Preparation end
	
	
	@Test
	public void test_edit_InvalidSelections() {
		String[] input = {"3", "J", "1", "testInvalidSelections", "X"};
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("testInvalidSelections", restaurant.getRestaurantName());
	}
	
	@Test
	public void test_edit_SetName() {
		String[] input = { "1", "testSetName", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("testSetName", restaurant.getRestaurantName());
	}

	@Test
	public void test_edit_SetType() {
		String[] input = { "2", "testSetType", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("testSetType", restaurant.getType());
	}
	
	@Test
	public void test_edit_SetDistrict() {
		String[] input = { "3", "testSetDistrict", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("testSetDistrict", restaurant.getDistrict());
	}
	
	@Test
	public void test_edit_SetAddress() {
		String[] input = { "4", "testSetAddress", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("testSetAddress", restaurant.getAddress());
	}
	
	@Test
	public void test_edit_SetPhone() {
		String[] input = { "5", "735759", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("735759", restaurant.getRestaurantContact());
	}
	
	@Test
	public void test_edit_InvalidPhone() {
		String[] input = { "5", "testInvalidPhone", "735719", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals("735719", restaurant.getRestaurantContact());
	}
	
	@Test
	public void test_edit_SetOpenTime() {
		String[] input = { "6", "11:00", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(LocalTime.parse("11:00"), restaurant.getOpenTime());
	}
	
	@Test
	public void test_edit_InvalidOpenTimeInput() {
		String[] input = { "6", "testInvalidOpenTime", "12:00", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(LocalTime.parse("12:00"), restaurant.getOpenTime());
	}
	
	@Test
	public void test_edit_InvalidOpenTimeLogic() {
		String[] input = { "6", "", "13:00", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(LocalTime.parse("13:00"), restaurant.getOpenTime());
	}
	
	@Test
	public void test_edit_SetCloseTime() {
		String[] input = { "7", "21:00", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(LocalTime.parse("21:00"), restaurant.getCloseTime());
	}
	
	@Test
	public void test_edit_InvalidCloseTimeInput() {
		String[] input = { "7", "testInvalidCloseTime", "22:00", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(LocalTime.parse("22:00"), restaurant.getCloseTime());
	}
	
	@Test
	public void test_edit_InvalidCloseTimeLogic() {
		String[] input = { "7", "09:00", "23:00", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(LocalTime.parse("23:00"), restaurant.getCloseTime());
	}
	
	@Test
	public void test_edit_SetDuration() {
		String[] input = { "8", "70", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(70, restaurant.getSessionDuration());
	}
	
	@Test
	public void test_edit_InvalidDurationInput() {
		String[] input = { "8", "testInvalidDuration", "80", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(80, restaurant.getSessionDuration());
	}
	
	@Test
	public void test_edit_InvalidDurationLogic() {
		String[] input = { "8", "-1", "80", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(80, restaurant.getSessionDuration());
	}
	
	@Test
	public void test_edit_SetTable() {
		String[] input = { "9", "4", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(4, restaurant.getAllTables().size());
	}
	
	@Test
	public void test_edit_InvalidTableInput() {
		String[] input = { "9", "testInvalidTable", "5", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(5, restaurant.getAllTables().size());
	}
	
	@Test
	public void test_edit_InvalidTableLogic() {
		String[] input = { "9", "-1", "6", "X" };
		setInput(input);

		restaurant.edit(scanner);
		assertEquals(6, restaurant.getAllTables().size());
	}
}
