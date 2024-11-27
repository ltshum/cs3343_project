package system;

import java.time.LocalTime;
import java.util.Scanner;

public class TimeCalculation {

    public static void main(String[] args) {
        long min;
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Please input the minutes you want to add");
            min = in.nextLong();
        }
        LocalTime bookingTime = LocalTime.parse("12:00"); // Start time
        LocalTime newTime = bookingTime.plusMinutes(min); // Add 30 minutes

        System.out.println("Original Time: " + bookingTime);
        System.out.println("New Time: " + newTime);
    }
}
