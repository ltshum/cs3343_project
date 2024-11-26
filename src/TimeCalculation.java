package src;
import java.time.LocalTime;
import java.util.Scanner;


public class TimeCalculation {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please input the minutes you want to add");
        long min =in.nextLong();
        in.close();
        LocalTime bookingTime = LocalTime.parse("12:00"); // Start time
        LocalTime newTime = bookingTime.plusMinutes(min); // Add 30 minutes

        System.out.println("Original Time: " + bookingTime);
        System.out.println("New Time: " + newTime);
    }
}
