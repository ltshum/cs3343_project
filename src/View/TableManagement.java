package View;

import java.util.Scanner;

import system.Account;
import system.Restaurant;
import system.Server;

public class TableManagement {

    Server server = Server.getInstance();
    private final Account account;

    public TableManagement(Account account) {
        this.account = account;
    }

    public void displayTableManagement(Scanner in) {
        while (true) {
            System.out.println("\n# Here is your table information #\n");
            server.getAllTableInfo(account);
            System.out.println("\n1. Edit");
            System.out.println("\n2. Back");

            boolean isValidOption = false;
            while (!isValidOption) {
                System.out.print("\nWhat action do you want to do?: ");
                try {
                    int op = Integer.parseInt(in.nextLine());
                    switch (op) {
                        case 1 -> {
                            boolean isValidTable = false;
                            while (!isValidTable) {
                                System.out.print("\nEnter the table id of the table you want to edit: ");
                                try {
                                    int tableID = Integer.parseInt(in.nextLine());
                                    if (server.tableValidation((Restaurant) account, tableID)) {
                                        isValidTable = true;
                                        server.updateTableInfo(account, in, tableID);
                                        isValidOption = true;
                                    } else {
                                        System.out.print("\nTable does not exist! Please try again.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.print("\nInvalid input! Please try again.");
                                }
                            }
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
