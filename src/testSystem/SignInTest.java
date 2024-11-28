package testSystem;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import View.SignIn;
import system.Server;

public class SignInTest {
    
    Server server = Server.getInstance();

    @Before
	public void setUp() {
		server.signUp("CUSTOMER", "1", "1", "1", "1", null, null, null, null, null, null, 0);
     server.signUp("RESTAURANT", "2", "2", "1", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
	}

    @Test
    public void TestSignInSuccess(){
        String[] in = {"1\n1\n4\n3\n"};
        Scanner input = testInput.input(in);
        SignIn signIn = new SignIn();
           signIn.signIn(input);
    }

     @Test
    public void testSignInFailure() {
        String input = "invalid_user\ninvalid_password\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        SignIn signIn = new SignIn();
        signIn.signIn(scanner);

        // You can add assertions here based on the expected behavior of the signIn method
        // For example, you can assert the output messages printed to the console
    }
}