
import java.util.List;
import java.util.Scanner;

public class Home {

    //Server server = Server.getInstance();
    private final Account account;

    public Home(Account account) {
        this.account = account;
    }

    public void display(Scanner in) {

        while (true) {
            System.out.println("\n# Welcome to Home Page #");
            int count = 1;

            List<Permission> list = account.getPermissions();
            for (Permission permission : list) {
                System.out.println("\n" + count + ". " + permission.getResource());
                count++;
            }
            System.out.println("\n" + count + ". " + "LOGOUT\n");
            System.out.print("What action do you want to do?: ");
            int op = in.nextInt();
            if (op >= 1 && op <= list.size()) {
                Resource selectedResource = list.get(op - 1).getResource();
                switch (selectedResource) {
                    case PROFILE -> {
                        Profile profilePage = new Profile(account);
                        profilePage.displayProfile(in);
                    }
                    case TABLE_MANAGEMENT -> {
                        TableManagement tableManagementPage = new TableManagement(account);
                        tableManagementPage.displayTableManagement(in);
                    }
                    case VIEW_BOOKING -> {
                        System.out.println("You selected Booking.");
                        // Add booking actions here
                    }
                    case SEARCH_RESTAURANT -> {
                        System.out.println("You selected Search Restaurant.");
                        // Add search restaurant actions here
                    }
                    default ->
                        System.out.println("Invalid selection.");
                }
            } else if (op == list.size() + 1) {
                System.out.println("Logging out...");
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
