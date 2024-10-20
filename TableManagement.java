
import java.util.Scanner;

public class TableManagement {

    Server server = Server.getInstance();
    private Account account;

    public TableManagement(Account account) {
        this.account = account;
    }

    public void displayTableManagement(Scanner in) {

        System.out.println("\n# Here is your table information #\n");
        server.getAllTableInfo(account);
        System.out.println("\n1. Edit");
        System.out.println("\n2. Back");
        System.out.print("\nWhat action do you want to do?: ");

        int op = in.nextInt();

        switch (op) {
            case 1 -> {
                System.out.print("\nWhich table you want to edit?: ");
                int tableID = in.nextInt();
                server.updateTableInfo(account, in, tableID);
            }
            case 2 -> {
                return;
            }
        }
    }
}
