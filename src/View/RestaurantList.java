package View;

import java.util.ArrayList;
import java.util.Scanner;

import system.Account;
import system.Customer;
import system.Server;

public class RestaurantList {

    Server server = Server.getInstance();
    ArrayList<Account> result = new ArrayList<>();

    public RestaurantList(ArrayList<Account> result) {
        this.result = result;
    }

    public void displayRestaurantList(Scanner in, Account ac) {

        while (true) {
            int count = 1;
            System.out.println("\n# Here is the result list #");
            System.out.println("# If you want to leave please input X #\n");
            for (Account r : result) {
                System.out.println(count + ". " + server.getListInfo(r));
                count++;
            }

            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("\nWhich restaurant you want to view: ");
                String input = in.nextLine();
                if (input.equals("X")) {
                    return;
                } else {
                    try {
                        int op = Integer.parseInt(input);
                        try {
                            if (ac instanceof Customer customer) {
                                BookingProfile bookingProfile = new BookingProfile(result.get(op - 1), customer);
                                bookingProfile.displayBookingProfile(in);
                            } else {
                                BookingProfile bookingProfile = new BookingProfile(result.get(op - 1));
                                bookingProfile.displayBookingProfile(in);
                            }
                            isValidOption = true;
                        } catch (Exception e) {
                            System.out.print("\nInvalid input! Please input again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("\nInvalid input! Please input again.");
                    }
                }
            }

        }
    }
}
