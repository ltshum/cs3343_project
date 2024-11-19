import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ViewBooking {
    Server server = Server.getInstance();
    private final Account account;
    private LocalDate date = LocalDate.now();
    private boolean isCustomer = false;
    private boolean isRestaurant = false;

    public ViewBooking(Account account) {
        this.account = account;
    }

    public void displayViewBooking(Scanner in) {

        System.out.println("\nHere is your booking record #");
        System.out.println("#you could leave by input X#");
        List<Role> roles = account.getRoles();
        for (Role role : roles) {
            if (role == Role.RESTAURANT) {
                System.out.println("#Take attendence input T#");
                isRestaurant = true;
            }
            else if (role == Role.CUSTOMER) {
                System.out.println("#If you want to make comment please input restaurant number#");
                isCustomer = true;
            }
        }

        System.out.println("\n[" + date + "]");

        int bookingRecordNumber = server.getViewBookingRecord(account, date);
        if (bookingRecordNumber == 0) {
            System.out.println("No booking record found.");
        }

        System.out.println("\nIf you want to view another date's booking record");
        System.err.println("please input date [YYYY-MM-DD]");

        boolean isValidOption = false;
        while (!isValidOption) {
            System.out.print("\nWhat action do you want to do: ");
            String op = in.next();

            outerLoop: switch (op) {
                case "T": 
                    if (isRestaurant) {
                        isValidOption = true;
                        in.nextLine();
                        String inputSession = "";
                        boolean isValidSession = false;
                        System.out.print("\n#Input 'b@ck' to exit the attendance taking session#");
                        while (!isValidSession) {
                            System.out.print("\nWhich session do you want to take attendance (HH:mm) - (HH:mm): ");
                            inputSession = in.nextLine(); 
                            if (inputSession.equalsIgnoreCase("b@ck")) {
                                System.out.println("\nExiting attendance taking session...");
                                isValidOption = false;
                                break outerLoop;
                            }
                            if (server.timeslotValidation((Restaurant) account, inputSession)) {
                                isValidSession = true;
                            } else {
                                System.out.print("\nNot valid. Please enter the session again.");
                            }
                        }
    
                        int tableID = -1;
                        boolean isValidTable = false;
                        while (!isValidTable) {
                            System.out.print("\nPlease input the table ID: ");
                            String tableInput = in.next();
                            
                            if (tableInput.equalsIgnoreCase("b@ck")) {
                                System.out.println("\nExiting attendance taking session...");
                                isValidOption = false;
                                break outerLoop;
                            }
                            
                            try {
                                tableID = Integer.parseInt(tableInput);
                            } catch (NumberFormatException e) {
                                System.out.println("\nInvalid input. Please enter a valid table ID.");
                                continue;
                            }
                        
                            if (server.tableValidation((Restaurant) account, tableID)) {
                                isValidTable = true;
                            } else {
                                System.out.print("\nNot valid. Please enter the table ID again.");
                            }
                        }
    
                        if (server.takeAttendance(account, date, inputSession, tableID)) {
                            System.out.println("Attendance taken successfully.");
                            displayViewBooking(in);
                        } else {
                            isValidOption = false;
                            System.out.print("\nThis booking is not exist. Please try again.");
                            break;
                        }
                    }
                    else {
                        System.out.print("\nInvalid option. Please try again.");
                        break;
                    }
                case "X":
                    return;
                default:
                    try {
                        int inputNumber = Integer.parseInt(op);
                        if (isCustomer) {
                            isValidOption = true;
                            if (inputNumber >= 1 && inputNumber <= bookingRecordNumber) {
                                System.out.print("\n#Input 'b@ck' to exit the commenting session#\n");
                                int rate = -1;
                                while (rate < 0 || rate > 5) {
                                    System.out.print("Please input your rate (0-5): ");
                                    if (in.hasNextInt()) {
                                        rate = in.nextInt();
                                        if (rate < 0 || rate > 5) {
                                            System.out.println("\nInvalid input. Please enter a number between 0 and 5.");
                                        }
                                    } else {
                                        String input = in.next();
                                        if (input.equalsIgnoreCase("b@ck")) {
                                            System.out.println("\nExiting commenting session...");
                                            isValidOption = false;
                                            break outerLoop;
                                        } else {
                                            System.out.println("\nInvalid input. Please enter a number between 0 and 5.");
                                        }
                                    }
                                }
                                System.out.print("Please input your comment: ");
                                in.nextLine();
                                String comment = in.nextLine();
                                if (comment.equalsIgnoreCase("b@ck")) {
                                    System.out.println("\nExiting commenting session...");
                                    isValidOption = false;
                                    break;
                                }
                                server.makeComment(account, inputNumber, date, rate, comment);
                                displayViewBooking(in);
                            } else {
                                isValidOption = false;
                                System.out.print("\nInvalid option. Please try again.");
                                break;
                            }
                        } else {
                            System.out.print("\nInvalid option. Please try again.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        try {
                            LocalDate parsedDate = LocalDate.parse(op);
                            isValidOption = true;
                            date = parsedDate;
                            displayViewBooking(in);
                    } catch (DateTimeParseException ex) {
                        System.out.print("\nInvalid option. Please try again.");
                        break;
                    }
                }
            }
        }
    }
}
