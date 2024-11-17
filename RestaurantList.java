
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantList {

    Server server = Server.getInstance();
    ArrayList<Restaurant> result = new ArrayList<>();

    public RestaurantList(ArrayList<Restaurant> result) {
        this.result = result;
    }

    public void displayRestaurantList(Scanner in,Account ac) {

        while (true) {
            int count = 1;
            System.out.println("\n#Here is the result list#");
            System.out.println("#If you want to leave please input X#\n");
            for (Restaurant r : result) {
                System.out.println(count + ". " + server.getListInfo(r));
                count++;
            }
            System.out.print("\nWhich restaurant you want to view: ");
            String input = in.next();
            if (input.charAt(0) == 'X') {
                return;
            } else {
                try {
                    int op = Integer.parseInt(input);
                    if(ac instanceof Customer){
                        BookingProfile bookingProfile = new BookingProfile(result.get(op - 1),(Customer)ac);
                        bookingProfile.displayBookingProfile(in);
                    }else{
                        BookingProfile bookingProfile = new BookingProfile(result.get(op - 1));
                        bookingProfile.displayBookingProfile(in);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
            }
        }
    }
}
