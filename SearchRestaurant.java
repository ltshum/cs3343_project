
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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

    private boolean isValidRating(String rateRange){
        if (rateRange.equals("null")){
            return true;
        }
        if( rateRange.length() == 1 && rateRange.charAt(0)>='0' && rateRange.charAt(0)<='5' ){
            return true;
        }

        if (rateRange.length() == 4 && rateRange.charAt(1)=='-' && rateRange.charAt(2)=='-' && rateRange.charAt(0)>='0' && rateRange.charAt(0)<='5' && rateRange.charAt(3)>='0' && rateRange.charAt(3)<='5' && rateRange.charAt(0) <=rateRange.charAt(3)){
            return true;
        }
        return false;
    }

    public void displaySearchRestaurnt(Scanner in) {
        System.out.println("\n# If you  want to leave it empty just enter null #");
        System.out.println("# Rate could input a range #\n");

        //in.nextLine();
        System.out.print("Restaurant Name?: ");
        String restaurantName = in.nextLine();
        System.out.print("Restaurant District?: ");
        String district = in.nextLine();
        String rateRange;
        do {
            System.out.print("Restaurant Rate(0--5)?: ");
            rateRange = in.next();
            if (!isValidRating(rateRange)){
                System.out.print("Invalid input\n");
            }
        } while (!isValidRating(rateRange));
        
        System.out.print("Restaurant Type?: ");
        String type = in.next();
        
        String ppl;
        while (true){
            System.out.print("How many ppl?: ");
            ppl = in.next();
            if (ppl.equals("null")){
                break;
            }
            try{
                Integer.parseInt(ppl);
                break;
            } catch (NumberFormatException e){
                System.out.print("Invalid input\n");
            }
        }
        


        
        String startTime;
        while (true){
            System.out.print("When you want to eat(HH:mm)?: ");
            startTime = in.next();
            if (startTime.equals("null")){
                break;
            }
            try{
                LocalTime.parse(startTime);
                break;
            } catch (DateTimeParseException e){
                System.out.print("Invalid input\n");
            }
        }
        
        
        String session;
        while (true){
            System.out.print("How long you prefer to eat(mins)?: ");
            session = in.next();
            if (session.equals("null")){
                break;
            }
            try{
                Integer.parseInt(session);
                break;
            } catch (NumberFormatException e){
                System.out.print("Invalid input\n");
            }
        }


        
        SearchCriteria search = new SearchCriteria(restaurantName, district, rateRange, type, ppl, startTime, session);

        ArrayList<Restaurant> results = search.searchRestaurantsIn(server.getRestaurantAccounts());
        
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
        
        //RestaurantList testRestaurantList = new RestaurantList(server.getAllRestaurants());
        RestaurantList testRestaurantList = new RestaurantList(results);
        testRestaurantList.displayRestaurantList(in,account);

    }
}
