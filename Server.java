
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private static final Server instance = new Server();
    private static ArrayList<Account> AccountList = new ArrayList<>();

    public static Server getInstance() {
        return instance;
    }

    public void signUpCustomer(String role, String username, String password, String name, String contact) {

        if (AccountList.stream().anyMatch(user -> user.getUserName().equals(username))) {
            System.out.println("Error: Username already exists!");

        } else {
            Customer customer = new Customer(username, password, name, contact);
            AccountList.add(customer);
            System.out.println("\nHere is your user info\n\nUsername: " + username + "\nPassword: " + password + "\nName: " + name + "\nPhone: " + contact);
            System.out.println("\n#Customer signed up successfully!#\n");
        }
    }

    public void signUpRestaurant(String role, String username, String password, String name, String type, String address, String contact, LocalTime openTime, LocalTime closeTime, Duration sessiDuration, int tableNum) {

        if (AccountList.stream().anyMatch(user -> user.getUserName().equals(username))) {
            System.out.println("Error: Username already exists!");
        } else {
            Restaurant restaurant = new Restaurant(username, password, name, type, address, contact, openTime, closeTime, sessiDuration, tableNum);
            AccountList.add(restaurant);
            System.out.println("\nHere is your user info\n\nUsername: " + username + "\nPassword: " + password + "\nRestaurant Name: " + name + "\nType: " + type + "\nAddress: " + address + "\nPhone: " + contact + "\nOpen Time: " + openTime + "\nClose Time: " + closeTime + "\nSession Duration: " + sessiDuration + "\nTable Amount: " + tableNum);
            System.out.println("\n#Restaurant signed up successfully!#\n");
        }
    }

    public boolean userNameVerify(String username) {
        if (AccountList.stream().anyMatch(user -> user.getUserName().equals(username))) {
            System.out.println("Error: Username already exists!");
            System.out.println("Please input another");
            return false;
        } else {
            return true;
        }
    }

    public Account signIn(String username, String password) {
        for (Account account : AccountList) {
            if (account.getUserName().equals(username) && account.getPassword().equals(password)) {
                return account; // Return the account if credentials match
            }
        }
        return null; // Return null if no match found
    }

    public String getUserDetail(Account ac) {
        System.out.println("\nUsername: " + ac.getUserName() + "\nPassword: " + ac.getPassword());
        List<Role> roles = ac.getRoles();
        for (Role role : roles) {
            if (role == Role.CUSTOMER) {
                Customer customer = (Customer) ac;
                return "Name: " + customer.getCustomerName()
                        + "\nPhone: " + customer.getCustomerContact();

            }
            if (role == Role.RESTAURANT) {
                Restaurant restaurant = (Restaurant) ac;
                return "Rate: " + restaurant.getRate()
                        + "\nRestaurant Name: " + restaurant.getRestaurantName()
                        + "\nType: " + restaurant.getType()
                        + "\nAddress: " + restaurant.getAddress()
                        + "\nPhone: " + restaurant.getRestaurantContact()
                        + "\nOpen Time: " + restaurant.getOpenTime()
                        + "\nClose Time: " + restaurant.getCloseTime()
                        + "\nSession Duration: " + restaurant.getSessionDuration() + "mins"
                        + "\nTable Amount: " + restaurant.getAllTables().size()
                        + "\n\nTimeslot: \n" + restaurant.getTimeslots()
                        + "\nComment: \n" + restaurant.getComment();
            }
        }
        return null;
    }

    // public void updateUserDetail(Account ac, Scanner in) {
    //     List<Role> roles = ac.getRoles();
    //     for (Role role : roles) {
    //         if (role == Role.CUSTOMER) {
    //             Customer customer = (Customer) ac;
    //             System.out.print("\nNew Name: ");
    //             String newName = in.next();
    //             System.out.print("\nNew Phone Number: ");
    //             String newContact = in.next();
    //             customer.setCustomerName(newName);
    //             customer.setCustomerContact(newContact);
    //         }
    //         if (role == Role.RESTAURANT) {
    //             Restaurant restaurant = (Restaurant) ac;
    //             System.out.print("\nNew Name: ");
    //             String newName = in.next();
    //             System.out.print("\nNew Type: ");
    //             String newType = in.next();
    //             System.out.print("\nNew Address: ");
    //             String newAddress = in.next();
    //             System.out.print("\nNew Phone Number: ");
    //             String newContact = in.next();
    //             restaurant.setRestaurantName(newName);
    //             restaurant.setType(newType);
    //             restaurant.setAddress(newAddress);
    //             restaurant.setRestaurantContact(newContact);
    //         }
    //     }
    // }

    public void updateUserDetail(Account ac, Scanner in) {
        ac.edit(in);
    }

    public void getAllTableInfo(Account ac) {

        Restaurant restaurant = (Restaurant) ac;
        ArrayList<Table> tables = restaurant.getAllTables();

        StringBuilder tableID = new StringBuilder("                ");
        StringBuilder seat = new StringBuilder("                ");
        StringBuilder status = new StringBuilder("                ");
        for (Table table : tables) {
            tableID.append(String.format("| Table ID: %-13d ", table.getTableID()));
            seat.append(String.format("| Seat: %-17d ", table.getSeatNum()));
            status.append(String.format("| Status: %-15s ", table.getStatus()));
        }
        System.out.println(tableID.toString());
        System.out.println(seat.toString());
        System.out.println(status.toString());
    }

    public void updateTableInfo(Account ac, Scanner in, int tableID) {

        Restaurant restaurant = (Restaurant) ac;
        ArrayList<Table> tables = restaurant.getAllTables();
        Table table = tables.get(tableID - 1);

        System.out.print("\nNew Seat: ");
        int newSeat = in.nextInt();
        table.setSeatNum(newSeat);

    }

    // public String getAllTimeslotInfo(Restaurant restaurant) {
    //     restaurant.getTimeslots();
    // for (Table table : tables) {
    //     System.out.println("Table ID: " + table.getTableID());
    //     System.out.println("Seat: " + table.getSeatNum());
    //     System.out.println("Status: " + table.gatStatus());
    // System.out.println("Timeslots: ");
    // List<Timeslot> timeslots = table.getAllTimeslots();
    // for (Timeslot timeslot : timeslots) {
    //     System.out.println(timeslot.getSession());
    // }
}

// public void updateTimeslotInfo(Account ac, Scanner in) {
//         Restaurant restaurant = (Restaurant) ac;
//         System.out.print("\nNew open time (HH:mm): ");
//         LocalTime openTime = LocalTime.parse(in.next());
//         System.out.print("\nNew close time (HH:mm): ");
//         LocalTime closeTime = LocalTime.parse(in.next());
//         System.out.print("\nNew session duration in minutes: ");
//         Duration sessionDuration = Duration.ofMinutes(in.nextInt());
//         restaurant.setTimeslots(openTime, closeTime, sessionDuration);
//     }
