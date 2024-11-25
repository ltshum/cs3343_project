
import java.util.List;
import java.util.Scanner;

public class Profile {

    Server server = Server.getInstance();
    private final Account account;

    public Profile(Account account) {
        this.account = account;
    }

    public void displayProfile(Scanner in) {
        while (true) { 
            System.out.println("\n# Here is your information #");
            System.out.println("\n# Username is not allowed to change #");
            List<Role> roles = account.getRoles();
            for (Role role : roles) {
                if (role == Role.RESTAURANT) {
                    System.out.println("# Comment is not allowed to change #");
                }
            }
            System.out.println(server.getUserDetail(account));
            System.out.println("\n1. Edit");
            System.out.println("\n2. Back");

            boolean isValidOption = false; 
            while (!isValidOption) {
                System.out.print("\nWhat action do you want to do?: ");
                try {
                    int op = Integer.parseInt(in.nextLine());
                    switch (op) {
                        case 1 -> {
                            System.out.print("\n");
                            server.updateUserDetail(account, in);
                            isValidOption = true;
                        }
                        case 2 -> {
                            return;
                        }
                        default -> {
                            System.out.print("\nInvalid input! Please try again.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.print("\nInvalid input! Please try again.");
                }
            }
            
        }
        
    }
}
