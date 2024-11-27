package View;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import system.Account;
import system.Restaurant;
import system.Server;

public class SearchRestaurant {

    Server server = Server.getInstance();
    private final Account account;

    public SearchRestaurant(Account account) {
        this.account = account;
    }

    private boolean isValidRating(String rateRange) {
        if (rateRange.equals("null")) {
            return true;
        }
        if (rateRange.length() == 1 && rateRange.charAt(0) >= '0' && rateRange.charAt(0) <= '5') {
            return true;
        }

        return rateRange.length() == 4 && rateRange.charAt(1) == '-' && rateRange.charAt(2) == '-' && rateRange.charAt(0) >= '0' && rateRange.charAt(0) <= '5' && rateRange.charAt(3) >= '0' && rateRange.charAt(3) <= '5' && rateRange.charAt(0) <= rateRange.charAt(3);
    }

    public void displaySearchRestaurnt(Scanner in) {
        System.out.println("\n# If you  want to leave it empty just enter null #");
        System.out.println("# Rate could input a range #\n");

        //in.nextLine();
        System.out.print("Restaurant Name?: ");
        String restaurantName = in.nextLine();
        System.out.print("Restaurant District?: ");
        String district = in.nextLine();
        System.out.print("Restaurant Type?: ");
        String type = in.nextLine();

        String rateRange;
        do {
            System.out.print("Restaurant Rate(0--5)?: ");
            rateRange = in.nextLine();
            if (!isValidRating(rateRange)) {
                System.out.print("Invalid input\n");
            }
        } while (!isValidRating(rateRange));

        String ppl;
        while (true) {
            System.out.print("How many ppl?: ");
            ppl = in.nextLine();
            if (ppl.equals("null")) {
                break;
            }
            try {
                Integer.valueOf(ppl);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input\n");
            }
        }

        String startTime;
        while (true) {
            System.out.print("When you want to eat(HH:mm)?: ");
            startTime = in.nextLine();
            if (startTime.equals("null")) {
                break;
            }
            try {
                LocalTime.parse(startTime);
                break;
            } catch (DateTimeParseException e) {
                System.out.print("Invalid input\n");
            }
        }

        String session;
        while (true) {
            System.out.print("How long you prefer to eat(mins)?: ");
            session = in.nextLine();
            if (session.equals("null")) {
                break;
            }
            try {
                Integer.valueOf(session);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input\n");
            }
        }

        ArrayList<Restaurant> results = server.searchRestaurantsIn(restaurantName, district, rateRange, type, ppl, startTime, session);

        RestaurantList RestaurantList = new RestaurantList(results);
        RestaurantList.displayRestaurantList(in, account);

    }
}
