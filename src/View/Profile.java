package View;

import java.util.Scanner;
import system.Server;

public class Profile {

    Server server = Server.getInstance();
    private final String accountUsername;

    public Profile(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public void displayProfile(Scanner in) {
        while (true) {
            System.out.println("\n# Here is your information #");
            System.out.println("\n# Username is not allowed to change #");

            if (server.isRestaurantByUsername(accountUsername)) {
                System.out.println("# Comment is not allowed to change #");
            }
            System.out.println(server.getUserDetail(accountUsername));
            System.out.println("\n1. Edit");
            System.out.println("\n2. Back");

            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("\nWhat action do you want to do?: ");
                try {
                    int op = Integer.parseInt(in.nextLine());
                    switch (op) {
                        case 1 -> {
                            System.out.print("\n");
                            server.updateUserDetail(accountUsername, in);
                            isValidOption = true;
                        }
                        case 2 -> {
                            return;
                        }
                        default -> {
                            System.out.print("\nInvalid input! Please try again.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.print("\nInvalid input! Please try again.");
                }
            }

        }

    }
}
