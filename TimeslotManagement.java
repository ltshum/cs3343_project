
import java.util.Scanner;

public class TimeslotManagement {

    Server server = Server.getInstance();
    private Account account;

    public TimeslotManagement(Account account) {
        this.account = account;
    }

    public void displayTimeslotManagement(Scanner in) {

        System.out.println("\n# Here is your time-slot information #\n");
        server.getAllTimeslotInfo(account);
        System.out.println("\n1. Edit");
        System.out.println("\n2. Back");
        System.out.print("\nWhat action do you want to do?: ");

        int op = in.nextInt();

        switch (op) {
            case 1 -> {
                server.updateTimeslotInfo(account, in);
            }
            case 2 -> {
                return;
            }
        }
    }
}
