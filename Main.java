
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Server server = Server.getInstance();
        Scanner in = new Scanner(System.in);

        //For Test
        server.signUpCustomer("Customer", "1", "1", "1", "1");
        Restaurant testres = server.signUpRestaurant("Restaurant", "2", "2", "1", "1", "d","1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        server.updateSeatNo(testres,1,6);
    //Test
        Restaurant r1 = new Restaurant("AC1", "2", "AC1", "Japan", "Kowloon Tong", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        Restaurant r2 = new Restaurant("AC2", "2", "AC2", "India", "Wong Tai Sin", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        Restaurant r3 = new Restaurant("AC3", "2", "AC3", "Thai", "Lok Fu", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        server.updateSeatNo(r1,1,6);
        server.updateSeatNo(r1,2,6);
        server.updateSeatNo(r2,1,6);
        server.updateSeatNo(r3,1,6);
        server.addRestaurant(r1);
        server.addRestaurant(r2);
        server.addRestaurant(r3);
        server.addRestaurantAccount(r1);
        server.addRestaurantAccount(r2);
        server.addRestaurantAccount(r3);
        //Pass to Sign-in or Sign-up
        while (true) {
            System.out.println("\n# Welcome to Restaurant Booking System #\n");
            System.out.println("1. Sign-in\n");
            System.out.println("2. Sign-up\n");
            System.out.println("3. Exit\n");

            boolean isValidChoice = false;
            while (!isValidChoice) {
                System.out.print("What action do you want to do?: ");
                try {
                    int choice = Integer.parseInt(in.nextLine());
                    switch (choice) {
                        case 1 -> {
                            (new SignIn()).SignIn(in);
                            isValidChoice = true;
                        }
                        case 2 -> {
                            (new SignUp()).SignUp(in);
                            isValidChoice = true;
                        }
                        case 3 -> {
                            return;
                        }
                        default ->
                            System.out.println("\nInvalid choice! Please try again.");
                    }
                } catch (NumberFormatException inputTypeException) {
                    System.out.println("\nInvalid choice! Please try again.");
                }
            }
            
        }
    }
}
