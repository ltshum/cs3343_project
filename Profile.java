
import java.util.List;
import java.util.Scanner;

public class Profile {

    Server server = Server.getInstance();
    private final Account account;

    public Profile(Account account) {
        this.account = account;
    }

    public void displayProfile(Scanner in) {

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
        System.out.print("\nWhat action do you want to do?: ");

        int op = in.nextInt();

        switch (op) {
            case 1 -> {
                server.updateUserDetail(account, in);
            }
            case 2 -> {
                return;
            }
        }

    }
}
