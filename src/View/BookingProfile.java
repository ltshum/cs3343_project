package View;

import java.time.LocalDate;
import java.util.Scanner;

import system.Server;

public class BookingProfile {

    Server server = Server.getInstance();
    private final String restaurantUsername;
    private final String customerUsername;
    
    public BookingProfile(String restaurantUsername, String customerUsername) {
        this.restaurantUsername = restaurantUsername;
        this.customerUsername = customerUsername;
    }

    public void displayBookingProfile(Scanner in) {

        System.out.println("\n# Here is restaurant information #\n");
        System.out.println(server.getRestaurantBookingDetail(restaurantUsername));
        System.out.println("\n1. Book today");
        System.out.println("\n2. Book another day");
        System.out.println("\n3. Back");

        boolean isValidOption = false;
        while (!isValidOption) {
            System.out.print("\nWhat action do you want to do?: ");
            try {
                int op = Integer.parseInt(in.nextLine());
                    outerloop:switch (op) {
                        case 1 -> {
                            boolean isValidSession = false;
                            String bookSession = "";
                            while (!isValidSession) {
                                System.out.print("Which timeslot do you want to book (HH:mm) - (HH:mm): ");
                                bookSession = in.nextLine();
                                if (!server.timeslotValidation(restaurantUsername, bookSession)) {
                                    System.out.println("\nNot validate! Please input again");
                                } else {
                                    isValidSession = true;
                                }
                            }

                            boolean isValidPpl = false;
                            int ppl = 0;
                            int tableID = 0;
                            while (!isValidPpl) {
                                System.out.print("How many seats would you like to book? ");
                                try {
                                    ppl = Integer.parseInt(in.nextLine());
                                    tableID = server.availableTableID(restaurantUsername, ppl, bookSession, LocalDate.now());
                                    if (tableID == 0) {
                                        System.out.println("\nSorry. The restaurant is full at this timeslot or have not enough seats for you.");
                                        break outerloop;
                                    }
                                    isValidPpl = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("\nInvalid input! Please try again.");
                                }

                            }

                            // System.out.print("What is your contact number: ");
                            // String contact = in.nextLine();
                            String contact = "";
                            //makeBooking
                            System.out.println(server.makeBooking(LocalDate.now(), tableID, bookSession, restaurantUsername, customerUsername, contact, ppl));
                            isValidOption = true;
                            System.out.println("Booking had been make ");
                        }
                        case 2 -> {
                            System.out.println("Book another day");
                            boolean isValidDate = false;
                            String dateInput;
                            while (!isValidDate) {
                                System.out.print("Which date do you want to book (yyyy-MM-dd): ");
                                dateInput = in.nextLine();
                                try {
                                    LocalDate bookingDate = LocalDate.parse(dateInput);
                                    isValidDate = true;
                                    boolean isValidSession = false;
                                    String bookSession = "";
                                    while (!isValidSession) {
                                        System.out.print("Which timeslot do you want to book (HH:mm) - (HH:mm): ");
                                        bookSession = in.nextLine();
                                        if (!server.timeslotValidation(restaurantUsername, bookSession)) {
                                            System.out.println("\nNot validate! Please input again");
                                        } else {
                                            isValidSession = true;
                                        }
                                    }

                                    boolean isValidPpl = false;
                                    int ppl = 0;
                                    int tableID = 0;
                                    while (!isValidPpl) {
                                        System.out.print("How many seats would you like to book? ");
                                        try {
                                            ppl = Integer.parseInt(in.nextLine());
                                            tableID = server.availableTableID(restaurantUsername, ppl, bookSession, bookingDate);
                                            if (tableID == 0) {
                                                System.out.println("\nThe restaurant is full at this timeslot or have not enough seats for you.");
                                                break outerloop;
                                            } else {
                                                isValidPpl = true;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("\nInvalid input! Please try again.");
                                        }

                                    }

                                    // System.out.print("What is your contact number: ");
                                    // String contact = in.nextLine();
                                    String contact = "";
                                    //makeBooking
                                    System.out.println(server.makeBooking(bookingDate, tableID, bookSession, restaurantUsername, customerUsername, contact, ppl));
                                } catch (Exception e) {
                                    System.out.println("\nNot validate! Please input again");
                                }
                            }
                            isValidOption = true;
                        }
                        case 3 -> {
                            return;
                        }
                        default ->
                            System.out.print("\nInvalid option. Please input again.");
                    }
                } catch (NumberFormatException e) {
                System.out.print("\nInvalid input! Please input again.");
            }
        }
    }
}
