
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchRestaurant {

    Server server = Server.getInstance();
    private final Account account;
    private List<String> searchCriteria;

    public SearchRestaurant(Account account) {
        this.account = account;
    }

    public void displaySearchRestaurnt(Scanner in) {
        System.out.println("\n# If you  want to leave it empty just enter null #");
        System.out.println("# Rate could input a range #\n");

        System.out.print("Restaurant Name?: ");
        String restaurantName = in.nextLine();
        System.out.print("Restaurant District?: ");
        String district = in.nextLine();
        System.out.print("Restaurant Rate(0--5)?: ");
        String rateRange = in.nextLine();
        System.out.print("Restaurant Type?: ");
        String type = in.nextLine();
        boolean isValidPpl = false;
        String ppl = null;
        while (!isValidPpl) {
            System.out.print("How many ppl?: ");
            ppl = in.nextLine();
            try {
                Integer.valueOf(ppl);
                isValidPpl = true;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please input again.");
            }
        }
        
        boolean isValidStartTime = false;
        String startTime = null;
        while (!isValidStartTime) {
            System.out.print("When you want to eat(HH:mm)?: ");
            startTime = in.nextLine();
            try {
                LocalTime.parse(startTime);
                isValidStartTime = true;
            } catch (Exception e) {
                System.out.println("\nInvalid input! Please input again.");
            }
        }

        boolean isValidSessionDuration = false;
        String sessionDuration = null;
        while (!isValidSessionDuration) {
            System.out.print("How long you prefer to eat(mins)?: ");
            sessionDuration = in.nextLine();
            try {
                Integer.valueOf(sessionDuration);
                isValidSessionDuration = true;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Please input again.");
            }
        }
        SearchCriteria search = new SearchCriteria(restaurantName, district, rateRange, type, ppl, startTime, sessionDuration);
        
        //Test
        Restaurant r1 = new Restaurant("AC1", "2", "AC1", "Japan", "Kowloon Tong", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        Restaurant r2 = new Restaurant("AC2", "2", "AC2", "India", "Wong Tai Sin", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        Restaurant r3 = new Restaurant("AC3", "2", "AC3", "Thai", "Lok Fu", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        ArrayList<Restaurant> testList = new ArrayList<>();
        server.updateSeatNo(r1,1,6);
        testList.add(r1);
        testList.add(r2);
        testList.add(r3);
        testList.addAll(server.getRestaurantAccounts());
        
        RestaurantList testRestaurantList = new RestaurantList(testList);
        testRestaurantList.displayRestaurantList(in,account);

    }
}
