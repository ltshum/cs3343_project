package testSystem;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.WeeklyReport;
import java.time.LocalDate;
import java.util.ArrayList;
import system.Comment;
import system.Restaurant;
import system.RestaurantLog;
import system.Server;

public class WeeklyReportTest {
	Server server = Server.getInstance();
	ArrayList<Comment> comments = new ArrayList<>();
	
	@Before
	public void setup() {
		comments.add(new Comment("User1", "Great", 3, LocalDate.now()));
		comments.add(new Comment("User2", "Good", 4, LocalDate.now()));
		server.signUp("RESTAURANT", "2", "2", "restaurant", "1", "d", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
		((Restaurant)server.getAccountByUserName("2")).setLastWeekLog(new RestaurantLog(0, 3.5f, 6, comments));
		((Restaurant)server.getAccountByUserName("2")).setThisWeekLog(new RestaurantLog(0, 3.5f, 6, comments));
    }
	
	@After
	public void tearDown() {
	    server.reset();
	}
    @Test
    public void testExit() {
        String[] in = { "1" };
        Scanner input = testInput.input(in);
        WeeklyReport report = new WeeklyReport("2");
        report.displayWeeklyReport(input);
    }

    @Test 
	public void testWrongType() {
    	String[] in = { "exit", "1" };
        Scanner input = testInput.input(in);
        WeeklyReport report = new WeeklyReport("2");
        report.displayWeeklyReport(input);
	}
    
	@Test 
	public void testOutOfRange() {
    	String[] in = { "3", "1" };
        Scanner input = testInput.input(in);
        WeeklyReport report = new WeeklyReport("2");
        report.displayWeeklyReport(input);
	}
}