package View;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import system.Server;

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
                int role = Integer.parseInt(in.nextLine());
                switch (role) {
                    case 1 -> {
                        //Input username
                        boolean isExistUserName = true;
                        String username = "";
                        while (isExistUserName) {
                            System.out.print("\nPlease input your username: ");
                            username = in.nextLine();
                            if (!server.isUsernameExist(username)) {
                                isExistUserName = false;
                            }
                        }
                        //Input password
                        System.out.print("\nPlease input your password: ");
                        String password = in.nextLine();
                        //Input customerName
                        System.out.print("\nPlease input your name: ");
                        String name = in.nextLine();
                        //Input customerContact
                        System.out.print("\nPlease input your phone number: ");
                        String contact = in.nextLine();
                        server.signUp("CUSTOMER", username, password, name, contact, null, null, null, null, null, null, 0);
                        isValidRole = true;
                    }
                    case 2 -> {
                        //Input username
                        boolean isExistUserName = true;
                        String username = "";
                        while (isExistUserName) {
                            System.out.print("\nPlease input your username: ");
                            username = in.nextLine();
                            if (!server.isUsernameExist(username)) {
                                isExistUserName = false;
                            }
                        }

                        //Input password
                        System.out.print("\nPlease input your password: ");
                        String password = in.nextLine();
                        //Input restaurantName
                        System.out.print("\nPlease input your name: ");
                        String name = in.nextLine();
                        //Input restaurantType
                        System.out.print("\nPlease input your restaurant type: ");
                        String type = in.nextLine();
                        //Input restaurantDistrict
                        System.out.print("\nPlease input your restaurant district: ");
                        String district = in.nextLine();
                        //Input restaurantAddress
                        System.out.print("\nPlease input your restaurant address: ");
                        String address = in.nextLine();
                        //Input restaurantContact
                        System.out.print("\nPlease input your restaurant phone number: ");
                        String contact = in.nextLine();
                        //Input openTime
                        boolean isValidOpenTime = false;
                        LocalTime openTime = null;
                        while (!isValidOpenTime) {
                            System.out.print("\nPlease input your restaurant open time (HH:mm): ");
                            try {
                                openTime = LocalTime.parse(in.nextLine());
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
                                closeTime = LocalTime.parse(in.nextLine());
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
                                sessionDuration = Duration.ofMinutes(Integer.parseInt(in.nextLine()));
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
                                tableCount = Integer.parseInt(in.nextLine());
                                isValidTableCount = true;
                            } catch (NumberFormatException e) {
                                System.out.print("\nInvalid input! Please try again.");
                            }
                        }

                        server.signUp("RESTAURANT", username, password, name, type, district, address, contact, openTime, closeTime, sessionDuration, tableCount);
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
