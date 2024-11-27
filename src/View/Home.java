package View;

import java.util.Scanner;

import acm.Resource;
import system.Account;
import system.Server;

public class Home {

    Server server = Server.getInstance();
    private final Account account;

    public Home(Account account) {
        this.account = account;
    }

    public void display(Scanner in) {

        while (true) {
            System.out.println("\n# Welcome to Home Page #");

            int count = server.getPermissionNumber(account);

            System.out.println("\n" + count + ". " + "LOGOUT\n");
            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("What action do you want to do?: ");
                try {
                    int op = Integer.parseInt(in.nextLine());
                    if (op >= 1 && op <= server.getPermissionSize(account)) {
                        isValidOption = true;
                        Resource selectedResource = server.getPermissionResource(account, op);
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
                    } else if (op == server.getPermissionSize(account) + 1) {
                        System.out.println("Logging out...");
                        return;
                    } else {
                        System.out.println("\nInvalid input. Please input again.");
                    }
                } catch (NumberFormatException inputTypeException) {
                    System.out.println("\nInvalid input. Please input again.");
                }
            }

        }
    }
}
