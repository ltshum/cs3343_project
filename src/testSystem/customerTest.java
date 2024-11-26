package testSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Scanner;

import system.Customer;

public class customerTest {

	// Preparation start
	InputStream systemIn = System.in;
	Customer customer;
	Scanner scanner;
	
	@BeforeEach
	public void setUp() throws Exception {
		customer = new Customer("username", "password", "name", "12345678");
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

		customer.edit(scanner);
		assertEquals("testInvalidSelections", customer.getCustomerName());
	}
	
	@Test
	public void test_edit_SetName() {
		String[] input = {"1", "testSetName", "X"};
		setInput(input);

		customer.edit(scanner);
		assertEquals("testSetName", customer.getCustomerName());
	}
	
	@Test
	public void test_edit_SetPhone() {
		String[] input = {"2", "735759", "X"};
		setInput(input);
		
		customer.edit(scanner);
		assertEquals("735759", customer.getCustomerContact());
	}
	
	@Test
	public void test_edit_InvalidPhone() {
		String[] input = {"2", "testInvalidPhone", "735719", "X"};
		setInput(input);

		customer.edit(scanner);
		assertEquals("735719", customer.getCustomerContact());
	}
	
}
