
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
            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("What action do you want to do?: ");
                try {
                    int op = Integer.parseInt(in.nextLine());
                    if (op >= 1 && op <= list.size()) {
                        isValidOption = true;
                        Resource selectedResource = list.get(op - 1).getResource();
                        //System.out.println("Selected resource: " + selectedResource);
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
                                ViewBooking viewBooking = new ViewBooking(account);
                                viewBooking.displayViewBooking(in);
                            }
                            case SEARCH_RESTAURANT -> {
                                SearchRestaurant searchRestaurantPage = new SearchRestaurant(account);
                                searchRestaurantPage.displaySearchRestaurnt(in);
                            }
                            case WEEKLY_REPORT -> {
                                WeeklyReport weeklyReport = new WeeklyReport(account);
                                weeklyReport.displayWeeklyReport(in);
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
                } catch (NumberFormatException inputTypeException) {
                    System.out.println("Invalid option. Please try again.");
                }                
            }
            
        }
    }
}
