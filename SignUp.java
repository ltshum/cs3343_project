
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class SignUp {

    Server server = Server.getInstance();

    public void SignUp(Scanner in) {

        // Back to Main
        System.out.print("\n# Sign-up for a new account #\n");
        System.out.print("\n1. Sign-up as Customer\n");
        System.out.print("\n2. Sign-up as Restaurant\n");
        System.out.print("\n3. Back\n");
        System.out.print("\nWhat action do you want to do?: ");
        int role = in.nextInt();

        switch (role) {
            case 1 -> {
                //Input username
                System.out.print("\nPlease input your username: ");
                String username = in.next();
                //Input password
                System.out.print("\nPlease input your password: ");
                String password = in.next();
                //Input customerName
                System.out.print("\nPlease input your name: ");
                String name = in.next();
                //Input customerContact
                System.out.print("\nPlease input your phone number: ");
                String contact = in.next();
                server.signUpCustomer("Customer", username, password, name, contact);
            }
            case 2 -> {
                //Input username
                System.out.print("\nPlease input your username: ");
                String username = in.next();
                //Input password
                System.out.print("\nPlease input your password: ");
                String password = in.next();
                //Input restaurantName
                System.out.print("\nPlease input your name: ");
                String name = in.next();
                //Input restaurantType
                System.out.print("\nPlease input your restaurant type: ");
                String type = in.next();
                //Input restaurantDistrict
                System.out.print("\nPlease input your restaurant district: ");
                String district = in.next();
                //Input restaurantAddress
                System.out.print("\nPlease input your restaurant address: ");
                String address = in.next();
                //Input restaurantContact
                System.out.print("\nPlease input your restaurant phone number: ");
                String contact = in.next();
                //Input openTime
                System.out.print("\nPlease input your restaurant open time (HH:mm): ");
                LocalTime openTime = LocalTime.parse(in.next());
                //Input closeTime
                System.out.print("\nPlease input your restaurant close time (HH:mm): ");
                LocalTime closeTime = LocalTime.parse(in.next());
                //Input sessionDuration
                System.out.print("\nPlease input your session duration in minutes: ");
                Duration sessionDuration = Duration.ofMinutes(in.nextInt());
                //Input tableNum
                System.out.print("\nPlease input your table amount: ");
                int tableCount = in.nextInt();
                server.signUpRestaurant("Restaurant", username, password, name, type, district, address, contact, openTime, closeTime, sessionDuration, tableCount);
            }
            case 3 -> {
                return;
            }
            default ->
                System.out.println("\n! Invalid role! Please try again. !");
        }

        System.out.println("Done.\n");

    }

}
