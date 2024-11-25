
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class SignUp {

    Server server = Server.getInstance();

    public void signUp(Scanner in) {

        // Back to Main
        System.out.print("\n# Sign-up for a new account #\n");
        System.out.print("\n1. Sign-up as Customer\n");
        System.out.print("\n2. Sign-up as Restaurant\n");
        System.out.print("\n3. Back\n");
       

        boolean isValidRole = false;
        while (!isValidRole) {
            System.out.print("\nWhat action do you want to do?: ");
            try {
                int role = Integer.parseInt(in.next());
                in.nextLine();
                switch (role) {
                    case 1 -> {
                        //Input username
                        boolean isExistUserName = true;
                        String username = "";
                        while (isExistUserName) {
                            System.out.print("\nPlease input your username: ");
                            username = in.next();
                            if (!server.isUsernameExist(username)) {
                                isExistUserName = false;
                            }
                        }
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
                        in.nextLine();
                        isValidRole = true;
                    }
                    case 2 -> {
                        //Input username
                        boolean isExistUserName = true;
                        String username = "";
                        while (isExistUserName) {
                            System.out.print("\nPlease input your username: ");
                            username = in.next();
                            if (!server.isUsernameExist(username)) {
                                isExistUserName = false;
                            }
                        }

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
                        boolean isValidOpenTime = false;
                        LocalTime openTime = null;
                        while (!isValidOpenTime) {
                            System.out.print("\nPlease input your restaurant open time (HH:mm): ");
                            try {
                                openTime = LocalTime.parse(in.next());
                                isValidOpenTime = true;
                            } catch (DateTimeParseException e) {
                                System.out.print("\nInvalid time format! Please try again.");
                            }
                        }
                        //Input closeTime
                        boolean isValidCloseTime = false;
                        LocalTime closeTime = null;
                        while (!isValidCloseTime) {
                            System.out.print("\nPlease input your restaurant close time (HH:mm): ");
                            try {
                                closeTime = LocalTime.parse(in.next());
                                isValidCloseTime = true;
                            } catch (DateTimeParseException e) {
                                System.out.print("\nInvalid time format! Please try again.");
                            }
                        }
                        //Input sessionDuration
                        boolean isValidSessionDuration = false;
                        Duration sessionDuration = null;
                        while (!isValidSessionDuration) {
                            System.out.print("\nPlease input your session duration in minutes: ");
                            try {
                                sessionDuration = Duration.ofMinutes(Integer.parseInt(in.next()));
                                isValidSessionDuration = true;
                            } catch (NumberFormatException e) {
                                System.out.print("\nInvalid input! Please try again.");
                            }
                        }

                        //Input tableNum
                        boolean isValidTableCount = false;
                        int tableCount = 0;
                        while (!isValidTableCount) {
                            System.out.print("\nPlease input your table amount: ");
                            try {
                                tableCount = Integer.parseInt(in.next());
                                isValidTableCount = true;
                            } catch (NumberFormatException e) {
                                System.out.print("\nInvalid input! Please try again.");
                            }
                        }


                        server.signUpRestaurant("Restaurant", username, password, name, type, district, address, contact, openTime, closeTime, sessionDuration, tableCount);
                        in.nextLine();
                        isValidRole = true;
                    }
                    case 3 -> {
                        return;
                    }
                    default ->
                        System.out.print("\nInvalid input! Please input again.");
                }
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input! Please input again.");
            }
            
        }

    }


}
