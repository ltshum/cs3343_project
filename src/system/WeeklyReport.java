package system;
import java.util.Scanner;

public class WeeklyReport {
    Server server = Server.getInstance();
    private final Restaurant restaurant;
    
    public WeeklyReport(Account account) {
        this.restaurant = (Restaurant) account;
    }

    public void displayWeeklyReport(Scanner in) {
        System.out.println("# Here is your weekly report #\n");
        server.generateRestaurantLog();
        server.generateWeeklyReport(restaurant);
        System.out.println("1. Exit\n");
       
        boolean isValidOption = false;
        while (!isValidOption) {
            System.out.print("What action do you want to do?: ");
            try {
                int op = Integer.parseInt(in.nextLine());
                if (op == 1) {
                    isValidOption = true;
                } else {
                    System.out.println("\nInvalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid option. Please try again.");
            }
        }        
    }
}