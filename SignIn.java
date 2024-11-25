
import java.util.Scanner;

public class SignIn {

    Server server = Server.getInstance();

    public void signIn(Scanner in) {

        //Input username
        System.out.print("\nPlease input your username: ");
        String username = in.next();
        in.nextLine();

        //Input password
        System.out.print("\nPlease input your password: ");
        String password = in.next();
        in.nextLine();
        Account account = server.signIn(username, password);

        //Pass to Home page or Back to Main
        while (true) {
            if (account != null) {
                System.out.println("\n# Sign in successful! Welcome, " + account.getUserName() + " #\n");
                Home homePage = new Home(account);
                homePage.display(in);
                return;
            } else {
                System.out.println("\n! Invalid credentials! Please try again. !");
                return;
            }
        }
    }
}
