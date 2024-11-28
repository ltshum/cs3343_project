package View;

import java.util.Scanner;
import system.Server;

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
        String accountUsername = server.signIn(username, password);

        //Pass to Home page or Back to Main
        while (true) {
            if (accountUsername != null) {
                System.out.println("\n# Sign in successful! Welcome, " + accountUsername + " #\n");
                Home homePage = new Home(accountUsername);
                homePage.display(in);
                return;
            } else {
                System.out.println("\n! Invalid credentials! Please try again. !");
                return;
            }
        }
    }
}
