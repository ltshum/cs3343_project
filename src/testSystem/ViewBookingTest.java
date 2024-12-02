package testSystem;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.Customer;
import system.Server;


public class ViewBookingTest {
	
	Customer customerAccount;
	Restaurant restaurantAccount;
	Server server = Server.getInstance();
	
	@Before
	public void setUp() {
		server.signUpCustomer("Customer", "customer", "password", "John Doe", "12345678");
		server.signUpRestaurant("Restaurant", "restaurant", 
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
		customerAccount = (Customer) server.signIn("customer", "password");
		restaurantAccount = (Restaurant) server.signIn("restaurant", "password");
		server.makeBooking(LocalDate.now(), 1, "12:00 - 13:00", restaurantAccount, customerAccount, "12345678", 3);
	}

	@After
	public void tearDown() {
		customerAccount.getAllBookings().clear();
	}
	
	//Take Attendance
	@Test
	public void testTakeAttendanceInCustomerAccount() {
		String[] in = { "T\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testTakeAttendanceWithoutBooking() {
		restaurantAccount.getAllBookings().clear();
		String[] in = { "T\n12:00 - 13:00\n1\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testExitWhenInputtingTimeSlot() {
		String[] in = { "T\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputInvalidTime() {
		String[] in = { "T\n12:00 - 14:00\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test 
	public void testExitWhenInputtingTableId() {
		String[] in = { "T\n12:00 - 13:00\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputWrongTypeTableId() {
		String[] in = { "T\n12:00 - 13:00\nString\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputInvalidTableId() {
		String[] in = { "T\n12:00 - 13:00\n4\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testTakeAttendance() {
		String[] in = { "T\n12:00 - 13:00\n1\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}


	//Make Comment
	@Test
	public void testMakeCommentInRestaurantAccount() {
		String[] in = { "1\n3\nGood\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(restaurantAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputRestauantIndexLargerThanNumberOfBooking() {
		String[] in = { "2\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputRestaurantIndexSmallerThan1() {
		String[] in = { "-1\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}


	@Test
	public void testExitWhenInputtingRate() {
		testTakeAttendance();
		String[] in = { "1\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputWrongTypeRate() {
		testTakeAttendance();
		String[] in = { "1\nfive\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputRateSmallerThan0() {
		testTakeAttendance();
		String[] in = { "1\n-1\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputRateLargerThan5() {
		testTakeAttendance();
		String[] in = { "1\n6\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testInputRateAndExitWhenInputtingComment() {
		testTakeAttendance();
		String[] in = { "1\n3\nX\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}


	@Test
	public void testMakeCommentWhenNotAttended() {
		for (Booking booking : customerAccount.getAllBookings()) {
			booking.setArrive(false);
		}
		String[] in = { "1\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}

	@Test
	public void testMakeCommentWithoutBooking() {
		customerAccount.getAllBookings().clear();
		String[] in = { "1\n3\nGood\nX" };
		Scanner input = testInput.input(in);
		ViewBooking viewBooking = new ViewBooking(customerAccount);
		viewBooking.displayViewBooking(input);
	}


}
