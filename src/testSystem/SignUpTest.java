package testSystem;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import View.SignUp;
import system.Server;

public class SignUpTest {

    Server server = Server.getInstance();

    @Before
    public void setUp() {
        server.signUp("CUSTOMER", "1", "1", "1", "1", null, null, null, null, null, null, 0);
        server.signUp("RESTAURANT", "2", "2", "1", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
    }

    @Test
    public void TestSignUpCustomerSuccessful() {
        String[] in = {"1\nUsername\nPassword\nName\n12345678\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpRestaurantSuccessful() {
        String[] in = {"2\nUsername\nPassword\nName\ntype\ndistrict\naddress\n12345678\n09:00\n12:00\n60\n2\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpCustomerIsExisted() {
        String[] in = {"1\n1\nc\nPassword\nName\n12345678\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpRestaurantIsExisted() {
        String[] in = {"2\n2\nr\nPassword\nName\ntype\ndistrict\naddress\n12345678\n09:00\n12:00\n60\n2\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpRestaurantInvalidOpenTime() {
        String[] in = {"2\nr2\nPassword\nName\ntype\ndistrict\naddress\n12345678\n0t:00\n09:00\n12:00\n60\n2\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpRestaurantInvalidCloseTime() {
        String[] in = {"2\nr3\nPassword\nName\ntype\ndistrict\naddress\n12345678\n09:00\n0t:00\n12:00\n60\n2\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpRestaurantInvalidDuration() {
        String[] in = {"2\nr4\nPassword\nName\ntype\ndistrict\naddress\n12345678\n09:00\n12:00\ninvalid\n60\n2\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestSignUpRestaurantInvalidTableCount() {
        String[] in = {"2\nr5\nPassword\nName\ntype\ndistrict\naddress\n12345678\n09:00\n12:00\n60\ninvalid\n2\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestBack() {
        String[] in = {"3\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestInvalidInputNumber() {
        String[] in = {"4\n3\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }

    @Test
    public void TestInvalidInput() {
        String[] in = {"test\n3\n"};
        Scanner input = testInput.input(in);
        SignUp signUp = new SignUp();
        signUp.signUp(input);
    }
}
