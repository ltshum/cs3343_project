package testSystem;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import View.Profile;
import system.Server;

public class ProfileTest {

    Server server = Server.getInstance();

    @Before
	public void setUp() {
		server.signUp("CUSTOMER", "1", "1", "1", "1", null, null, null, null, null, null, 0);
        server.signUp("RESTAURANT", "2", "2", "1", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
	}

    @Test
    public void TestUpdateProfile(){
        String[] in = {"1\nX\n2\n4\n3\n"};
        Scanner input = testInput.input(in);
        Profile profile = new Profile("1");
        profile.displayProfile(input);
    }

    @Test
    public void TestBack(){
        String[] in = {"2\n4\n3\n"};
        Scanner input = testInput.input(in);
        Profile profile = new Profile("1");
        profile.displayProfile(input);
    }

    @Test
    public void TestRestaurant(){
        String[] in = {"2\n4\n3\n"};
        Scanner input = testInput.input(in);
        Profile profile = new Profile("2");
        profile.displayProfile(input);
    }

    @Test
    public void TestInvalidNumber(){
        String[] in = {"8\n2\n4\n3\n"};
        Scanner input = testInput.input(in);
        Profile profile = new Profile("1");
        profile.displayProfile(input);
    }

    @Test
    public void TestInvalidInput(){
        String[] in = {"test\n2\n4\n3\n"};
        Scanner input = testInput.input(in);
        Profile profile = new Profile("1");
        profile.displayProfile(input);
    }
}
