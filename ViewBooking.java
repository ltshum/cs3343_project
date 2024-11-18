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

        System.out.print("\n\nWhat action do you want to do: ");
        String op = in.next();

        switch (op) {
            case "T": 
                if (isRestaurant) {
                    System.out.print("\nWhich session you want to take attendance (HH:mm) - (HH:mm): ");
                    in.nextLine();
                    String inputSession = in.nextLine(); 
                    System.out.print("Please input the table ID: ");
                    int tableID = in.nextInt();
                    in.nextLine();
                    if (server.takeAttendance(account, date, inputSession, tableID)) {
                        System.out.println("Attendance taken successfully.");
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    };
                    displayViewBooking(in);
                }
                else {
                    System.out.println("Invalid option. Please try again.");
                }
            case "X":
                return;
            default:
                try {
                    int inputNumber = Integer.parseInt(op);
                    if (inputNumber >= 1 && inputNumber <= bookingRecordNumber) {
                        if (isCustomer) {
                            System.out.print("\nPlease input your rate: ");
                            int rate = in.nextInt();
                            System.out.print("Please input your comment: ");
                            in.nextLine();
                            String comment = in.nextLine();
                            server.makeComment(account, inputNumber, date, rate, comment);
                            displayViewBooking(in);
                        } else {
                            System.out.println("Invalid option. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    try {
                        LocalDate parsedDate = LocalDate.parse(op);
                        date = parsedDate;
                        displayViewBooking(in);
                } catch (DateTimeParseException ex) {
                    System.out.println("Invalid option. Please try again.");
                }
                return;
            }
        }
    }
}
