package testSystem;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import View.BookingProfile;
import system.Server;

public class BookingProfileTest {
    Server server = Server.getInstance();

    @Before
	public void setUp() {
		server.signUp("CUSTOMER", "1", "1", "1", "1", null, null, null, null, null, null, 0);
        server.signUp("RESTAURANT", "2", "2", "1", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        String[] in = {"1\n2\n2\n5\n3\n"};
        Scanner input = testInput.input(in);
        server.updateTableInfo("2", input, 1);
	}
    
    @Test
    public void TestBookToday(){
        String[] in = {"1\n09:00 - 10:00\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestBook(){
        String[] in = {"1\n19:00 - 20:00\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }
    
    @Test 
    public void TestBookAnotherDay(){
        String[] in = {"2\n2024-12-21\n09:00 - 10:00\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestNoCustomerAccount(){
        String[] in = {"2\n2024-12-21\n09:00 - 10:00\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "3");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestInvalidSeat(){
        String[] in = {"1\n2024-12-21\n11:00 - 12:00\ninvalid\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestInvalidTimeslot(){
        String[] in = {"2\n2024-12-21\n09:y0 - 10:00\n12:00 - 13:00\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestInvalidNumber(){
        String[] in = {"4\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestInvalid(){
        String[] in = {"y\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    @Test
    public void TestInvalidDate(){
        String[] in = {"2\ny\n2024-12-21\n15:00 - 16:00\n1\n12345678\n3\n5\n3\n"};
        Scanner input = testInput.input(in);
        BookingProfile bookingProfile = new BookingProfile("2", "1");
        bookingProfile.displayBookingProfile(input);
    }

    // @Test
    // public void TestInvalidPpl(){
    //     String[] in = {"1\n2024-12-21\n11:00 - 12:00\ninvalid\n1\n12345678\n3\n5\n3\n"};
    //     Scanner input = testInput.input(in);
    //     BookingProfile bookingProfile = new BookingProfile("2", "1");
    //     bookingProfile.displayBookingProfile(input);
    // }


}
