package View;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import system.Server;

public class ViewBooking {

    Server server = Server.getInstance();
    private final String accountUsername;
    private LocalDate date = LocalDate.now();
    private boolean isCustomer = false;
    private boolean isRestaurant = false;

    public ViewBooking(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public void displayViewBooking(Scanner in) {
        boolean exitLoop = false;
        while (!exitLoop) {
            System.out.println("\n# Here is your booking record on " + date + " #");
            System.out.println("# you could leave by input X #");
            if (server.isRestaurantByUsername(accountUsername)) {
                System.out.println("# Take attendence input T #");
                isRestaurant = true;
                }
                if (server.isCustomerByUsername(accountUsername)) {
                System.out.println("# If you want to make comment please input restaurant number #");
                isCustomer = true;

                }

            System.out.println("\n[" + date + "]");

            int bookingRecordNumber = server.getViewBookingRecord(accountUsername, date);
            if (bookingRecordNumber == 0) {
                System.out.println("No booking record found.");
            }

            System.out.println("\nIf you want to view another date's booking record");
            System.out.println("please input date [YYYY-MM-DD]");

            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("\nWhat action do you want to do: ");
                String op = in.nextLine();
                outerLoop:
                switch (op) {
                    case "T" -> {
                        if (isRestaurant) {
                            String inputSession = "";
                            boolean isValidSession = false;
                            System.out.print("\n# Input X to exit the attendance taking session #");
                            while (!isValidSession) {
                                System.out.print("\nWhich session do you want to take attendance (HH:mm) - (HH:mm): ");
                                inputSession = in.nextLine();
                                if (inputSession.equals("X")) {
                                    System.out.println("\nExiting attendance taking session...");
                                    break outerLoop;
                                }
                                if (server.timeslotValidation(accountUsername, inputSession)) {
                                    isValidSession = true;
                                } else {
                                    System.out.print("\nNot valid. Please enter the session again.");
                                }
                            }
                            int tableID = -1;
                            boolean isValidTable = false;
                            while (!isValidTable) {
                                System.out.print("\nPlease input the table ID: ");
                                String tableInput = in.nextLine();
                                if (tableInput.equals("X")) {
                                    System.out.println("\nExiting attendance taking session...");
                                    break outerLoop;
                                }

                                try {
                                    tableID = Integer.parseInt(tableInput);
                                } catch (NumberFormatException e) {
                                    System.out.println("\nInvalid input. Please enter a valid table ID.");
                                }

                                if (server.tableValidation(accountUsername, tableID)) {
                                    isValidTable = true;
                                } else {
                                    System.out.print("\nNot valid. Please enter the table ID again.");
                                }
                            }

                            if (server.takeAttendance(accountUsername, date, inputSession, tableID)) {
                                System.out.println("\nAttendance taken successfully.");
                                isValidOption = true;
                            } else {
                                System.out.print("\nThis booking is not exist. Please try again.");
                            }
                        } else {
                            System.out.print("\nInvalid option. Please try again.");
                        }
                    }
                    case "X" -> {
                        exitLoop = true;
                        isValidOption = true;
                    }
                    default -> {
                        try {
                            int inputNumber = Integer.parseInt(op);
                            if (isCustomer) {
                                isValidOption = true;
                                if (inputNumber >= 1 && inputNumber <= bookingRecordNumber) {
                                    if (!server.checkHasAttend(accountUsername, inputNumber, date)) {
                                        isValidOption = false;
                                    } else {
                                        System.out.print("\n# Input X to exit the commenting session #\n");
                                        int rate = -1;
                                        while (rate < 0 || rate > 5) {
                                            System.out.print("Please input your rate (0-5): ");
                                            if (in.hasNextInt()) {
                                                rate = in.nextInt();
                                                in.nextLine();
                                                if (rate < 0 || rate > 5) {
                                                    System.out.println("\nInvalid input. Please enter a number between 0 and 5.");
                                                }
                                            } else {
                                                String input = in.nextLine();
                                                if (input.equals("X")) {
                                                    System.out.println("\nExiting commenting session...");
                                                    isValidOption = false;
                                                    break outerLoop;
                                                } else {
                                                    System.out.println("\nInvalid input. Please enter a number between 0 and 5.");
                                                }
                                            }
                                        }
                                        System.out.print("Please input your comment: ");
                                        String comment = in.nextLine();
                                        if (comment.equals("X")) {
                                            System.out.println("\nExiting commenting session...");
                                            isValidOption = false;
                                            break;
                                        }
                                        server.makeComment(accountUsername, inputNumber, date, rate, comment);
                                    }
                                } else {
                                    isValidOption = false;
                                    System.out.print("\nInvalid option. Please try again.");
                                }
                            } else {
                                System.out.print("\nInvalid option. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            try {
                                LocalDate parsedDate = LocalDate.parse(op);
                                isValidOption = true;
                                date = parsedDate;
                            } catch (DateTimeParseException ex) {
                                System.out.print("\nInvalid option. Please try again.");
                            }
                        }
                    }
                }
            }
        }
    }
}
