package View;

import java.util.Scanner;
import system.Server;

public class WeeklyReport {

    Server server = Server.getInstance();
    String accountUsername;

    public WeeklyReport(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public void displayWeeklyReport(Scanner in) {
        System.out.println("# Here is your weekly report #\n");
        server.generateAccountLog();
        System.out.println(server.generateAccountWeeklyReport(accountUsername));
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
