package testSystem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.Main;
import system.Server;

public class MainTest {
    InputStream systemIn = System.in;
    Scanner scanner;
    private Server server;
    private Main mainApp;
    @Before
    public void setUp() {
        server = Server.getInstance();
    }
    
     @After
    public void tearDown() throws Exception {
        scanner.close();
    System.setIn(systemIn);
    }

    public void setInput(String[] in) {
        scanner = testInput.input(in);
    }
        @Test
        public void testSignUpAndSignIn() {
            mainApp = new Main(server, scanner);

            String []input = {"2","2","newUser","password","New User","1234567890","Type","District","Address","09:00","21:00","60","3","1","newUser","password","5","3"};
            setInput(input);
            mainApp.start();
            assertNotNull(server.getAccountByUserName("newUser"));
        }

        @Test
        public void testInvalidChoice() {
            String []input = {"4","3"};
            setInput(input);
            mainApp = new Main(server, scanner);
            mainApp.start();

//
//            mainApp.start();
//
           assertNull(server.getAccountByUserName("newUser"));
        }
//
//        @Test
//        public void testExit() {
//            mainApp = new Main(server, scanner);
//
//            String input = "3\n"; // Exit choice
//            InputStream in = new ByteArrayInputStream(input.getBytes());
//            System.setIn(in);
//
//            mainApp.start();
//
//            // Check that the application exited without errors
//            assertTrue(true); // Just to confirm it didn’t throw any exceptions
//        }
        
        @Test
        public void testInvalidformat() {
            String []input = {"hello","3"};
            setInput(input);
            mainApp = new Main(server, scanner);
            mainApp.start();
            assertNotNull(server.getAccountByUserName("newUser"));

        }
    }

    

