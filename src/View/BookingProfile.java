package View;

import java.time.LocalDate;
import java.util.Scanner;

import system.Customer;
import system.Restaurant;
import system.Server;

public class BookingProfile {

    Server server = Server.getInstance();
    private final Restaurant restaurant;
    private final Customer ac;

    public BookingProfile(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.ac = null;
    }

    public BookingProfile(Restaurant restaurant, Customer ac) {
        this.restaurant = restaurant;
        this.ac = ac;
    }

    public void displayBookingProfile(Scanner in) {

        System.out.println("\n# Here is restaurant information #\n");
        System.out.println(server.getRestaurantBookingDetail(restaurant));
        System.out.println("\n1. Book today");
        System.out.println("\n2. Book another day");
        System.out.println("\n3. Back");

        boolean isValidOption = false;
        while (!isValidOption) {
            System.out.print("\nWhat action do you want to do?: ");
            try {
                int op = Integer.parseInt(in.nextLine());
                if (ac instanceof Customer) {
                    switch (op) {
                        case 1 -> {
                            boolean isValidSession = false;
                            String bookSession = "";
                            while (!isValidSession) {
                                System.out.print("Which timeslot do you want to book (HH:mm) - (HH:mm): ");
                                bookSession = in.nextLine();
                                if (!server.timeslotValidation(restaurant, bookSession)) {
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
                                    tableID = server.availableTableID(restaurant, ppl, bookSession, LocalDate.now());
                                    if (tableID == 0) {
                                        System.out.println("\nSorry. The restaurant is full at this timeslot or have not enough seats for you.");
                                    } else {
                                        isValidPpl = true;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("\nInvalid input! Please try again.");
                                }

                            }

                            System.out.print("What is your contact number: ");
                            String contact = in.nextLine();
                            //makeBooking
                            System.out.println(server.makeBooking(LocalDate.now(), tableID, bookSession, restaurant, ac, contact, ppl));
                            isValidOption = true;
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
                                        if (!server.timeslotValidation(restaurant, bookSession)) {
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
                                            tableID = server.availableTableID(restaurant, ppl, bookSession, bookingDate);
                                            if (tableID == 0) {
                                                System.out.println("\nThe restaurant is full at this timeslot or have not enough seats for you.");
                                            } else {
                                                isValidPpl = true;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("\nInvalid input! Please try again.");
                                        }

                                    }

                                    System.out.print("What is your contact number: ");
                                    String contact = in.nextLine();
                                    //makeBooking
                                    System.out.println(server.makeBooking(bookingDate, tableID, bookSession, restaurant, ac, contact, ppl));
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
                }
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input! Please input again.");
            }

        }

    }
}
