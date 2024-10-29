
import java.util.Scanner;

public class BookingProfile {

    Server server = Server.getInstance();
    private final Restaurant restaurant;

    public BookingProfile(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void displayBookingProfile(Scanner in) {

        System.out.println("\n# Here is restaurant information #\n");
        System.out.println(server.getRestaurantBookingDetail(restaurant));
        System.out.println("\n1. Book today");
        System.out.println("\n2. Book another day");
        System.out.println("\n3. Back");
        System.out.print("\nWhat action do you want to do?: ");

        int op = in.nextInt();

        switch (op) {
            case 1 -> {
                System.out.println("Book today");
            }
            case 2 -> {
                System.out.println("Book another day");
            }
            case 3 -> {
                return;
            }
        }

    }
}
