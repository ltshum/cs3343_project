package View;

import java.util.Scanner;
import system.Server;

public class Home {

    Server server = Server.getInstance();
    private final String accountUsername;

    public Home(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public void display(Scanner in) {

        while (true) {
            System.out.println("\n# Welcome to Home Page #");

            int count = server.getPermissionNumber(accountUsername);

            System.out.println("\n" + count + ". " + "LOGOUT\n");
            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("What action do you want to do?: ");
                try {
                    int op = Integer.parseInt(in.nextLine());
                        isValidOption = true;
                        String selectedResource = server.getPermissionResource(accountUsername, op);
                        //System.out.println("Selected resource: " + selectedResource);
                        switch (selectedResource) {
                            case "PROFILE" -> {
                                Profile profilePage = new Profile(accountUsername);
                                profilePage.displayProfile(in);
                            }
                            case "TABLE_MANAGEMENT" -> {
                                TableManagement tableManagementPage = new TableManagement(accountUsername);
                                tableManagementPage.displayTableManagement(in);
                            }
                            case "VIEW_BOOKING" -> {
                                ViewBooking viewBooking = new ViewBooking(accountUsername);
                                viewBooking.displayViewBooking(in);
                            }
                            case "SEARCH_RESTAURANT" -> {
                                SearchRestaurant searchRestaurantPage = new SearchRestaurant(accountUsername);
                                searchRestaurantPage.displaySearchRestaurnt(in);
                            }
                            case "WEEKLY_REPORT" -> {
                                WeeklyReport weeklyReport = new WeeklyReport(accountUsername);
                                weeklyReport.displayWeeklyReport(in);
                            }
                            case "LOGOUT" -> {
                                System.out.println("Logging out...");
                                return;
                            }
                            default ->
                                System.out.println("Invalid selection.");
                        }
                } catch (NumberFormatException inputTypeException) {
                    System.out.println("\nInvalid input. Please input again.");
                }
            }

        }
    }
}