import java.util.List;
import java.util.Scanner;

public class ViewBooking {
    Server server = Server.getInstance();
    private Account account;
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

        System.out.println("\n[today date]");

        System.out.println("\nIf you want to view another date's booking record");
        System.err.println("please input date [YY-MM-DD]");

        System.out.print("\n\nWhat action do you want to do: ");
        String op = in.next();

        switch (op) {
            case "T": 
                if (isRestaurant) {
                    System.out.print("\nWhich session you want to take attendance (HH:mm): ");
                    String input = in.next();
                }
                else {
                    System.out.println("Invalid option. Please try again.");
                }
            case "X":
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
