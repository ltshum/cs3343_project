package View;

import java.util.ArrayList;
import java.util.Scanner;

import system.Server;

public class RestaurantList {

    Server server = Server.getInstance();
    ArrayList<String> result = new ArrayList<>();

    public RestaurantList(ArrayList<String> result) {
        this.result = result;
    }

    public void displayRestaurantList(Scanner in, String accountUsername) {

        while (true) {
            int count = 1;
            System.out.println("\n# Here is the result list #");
            System.out.println("# If you want to leave please input X #\n");
            for (String r : result) {
                System.out.println(count + ". " + server.getListInfo(accountUsername));
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
                            if (server.isCustomerByUsername(accountUsername)) {
                                BookingProfile bookingProfile = new BookingProfile(result.get(op - 1), accountUsername);
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
