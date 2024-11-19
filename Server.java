
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private static final Server instance = new Server();
    private static ArrayList<Account> AccountList = new ArrayList<>();
    private Map<String, Account> RestaurantAccounts;
    private Map<String, Map<String, RestaurantLog>> restaurantLogs;

    private Server() {
        RestaurantAccounts = new HashMap<>();
        restaurantLogs = new HashMap<>();
        Restaurant r1 = new Restaurant("AC1", "1", "1", "1", "1", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 1);
        Restaurant r2 = new Restaurant("AC2", "2", "AC2", "India", "Wong Tai Sin", "1", "1", LocalTime.parse("09:00"), LocalTime.parse("21:00"), Duration.ofMinutes(60), 3);
        // predeined data
        RestaurantAccounts.put(r1.getUserName(), r1);
        RestaurantAccounts.put(r2.getUserName(), r2);
        // add data
        addTestData();
    }

    private void addTestData() {
        // test1
        Map<String, RestaurantLog> restaurant1Logs = new HashMap<>();
        restaurant1Logs.put("lastWeek", new RestaurantLog(1, 4.5f, 100, List.of(new Comment("User1", "Nice", 5.0f))));
        restaurant1Logs.put("thisWeek", new RestaurantLog(2, 4.0f, 120, List.of(new Comment("User2", "Good", 4.0f))));
        restaurantLogs.put("AC1", restaurant1Logs);

        // test2
        Map<String, RestaurantLog> restaurant2Logs = new HashMap<>();
        restaurant2Logs.put("lastWeek", new RestaurantLog(3, 3.5f, 80, List.of(new Comment("User3", "Average", 3.0f))));
        restaurant2Logs.put("thisWeek", new RestaurantLog(1, 4.5f, 150, List.of(new Comment("User4", "Excellent", 5.0f))));
        restaurantLogs.put("AC2", restaurant2Logs);
    }

    public RestaurantLog getRestaurantLog(Restaurant restaurant, String period) {
        Map<String, RestaurantLog> logs = restaurantLogs.get(restaurant.getUserName());
        if (logs != null) {
            return logs.get(period);
        }
        return null;
    }

    public static Server getInstance() {
        return instance;
    }

    public void addRestaurant(Restaurant ac) {
        AccountList.add(ac);
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

    public void signUpRestaurant(String role, String username, String password, String name, String type, String district, String address, String contact, LocalTime openTime, LocalTime closeTime, Duration sessiDuration, int tableNum) {

        if (AccountList.stream().anyMatch(user -> user.getUserName().equals(username))) {
            System.out.println("Error: Username already exists!");
        } else {
            Restaurant restaurant = new Restaurant(username, password, name, type, district, address, contact, openTime, closeTime, sessiDuration, tableNum);
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
                        + "\nDistrict: " + restaurant.getDistrict()
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

    public String getRestaurantBookingDetail(Restaurant restaurant) {
        for (Account account : AccountList) {
            if (account.getUserName().equals(restaurant.getUserName())) {
                System.out.println("In Condition");
                Restaurant requiredRestaurant = (Restaurant) account;
                return "Rate: " + requiredRestaurant.getRate()
                        + "\nRestaurant Name: " + requiredRestaurant.getRestaurantName()
                        + "\nType: " + requiredRestaurant.getType()
                        + "\nDistrict: " + requiredRestaurant.getDistrict()
                        + "\nAddress: " + requiredRestaurant.getAddress()
                        + "\nPhone: " + requiredRestaurant.getRestaurantContact()
                        + "\nOpen Time: " + requiredRestaurant.getOpenTime()
                        + "\nClose Time: " + requiredRestaurant.getCloseTime()
                        + "\nTable Amount: " + requiredRestaurant.getAllTables().size()
                        + "\n\nTimeslot: \n" + requiredRestaurant.getTimeslots()
                        + "\nComment: \n" + requiredRestaurant.getComment();
            }
        }
        return "Restaurant not found.";
    }

    public boolean timeslotValidation(Restaurant ac, String bookTimeslot) {
        String[] allTimeslots = ac.getTimeslots().split(" \n");
        for (String timeslot : allTimeslots) {
            if (timeslot.equals(bookTimeslot)) {
                return true;
            }
        }
        return false;
    }

    public int availableTableID(Restaurant ac, int ppl, String timeslotSession, LocalDate date) {
        // Iterate through the restaurant's tables
        ArrayList<Table> allTables = ac.getAllTables();
        for (Table table : allTables) {
            // Check if table meets the seating requirement
            if (table.getSeatNum() >= ppl) {
                // Check if the timeslot is available
                if (table.isTimeslotAvailable(timeslotSession, date)) {
                    table.setTimeslotUnavailable(timeslotSession, date);
                    return table.getTableID(); // Return the table ID if found
                }
            }
        }
        return 0; // Return -1 if no suitable table is found
    }

    public String makeBooking(LocalDate date, int tableID, String bookSession, Restaurant restaurant, Customer ac, String contact, int ppl) {
        for (Account account : AccountList) {
            if (account.getUserName().equals(restaurant.getUserName())) {
                Restaurant requiredRestaurant = (Restaurant) account;
                Booking bk = new Booking(date, tableID, bookSession, requiredRestaurant.getRestaurantName(), ac.getCustomerName(), contact, ppl);
                requiredRestaurant.addBooking(bk);
                ac.addBooking(bk);
                break;
            }
        }
        return "Already booked a seat for you";
    }

    public String getListInfo(Restaurant restaurant) {
        return restaurant.getRestaurantName() + ": " + restaurant.getRate() + " " + restaurant.getType() + " " + restaurant.getDistrict();
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

    public void updateSeatNo(Account ac, int tableID, int seatNum) {

        Restaurant restaurant = (Restaurant) ac;
        ArrayList<Table> tables = restaurant.getAllTables();
        Table table = tables.get(tableID - 1);

        table.setSeatNum(seatNum);
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
    public int getViewBookingRecord(Account ac, LocalDate date) {
        List<Role> roles = ac.getRoles();
        for (Role role : roles) {
            if (role == Role.CUSTOMER) {
                Customer customer = (Customer) ac;
                ArrayList<Booking> allbookings = customer.getAllBookings();
                String bookingString = "";
                int index = 0;
                for (Booking booking : allbookings) {
                    if (booking.getDate().equals(date)) {
                        index++;
                        bookingString += index + ". " + booking.getRestaurantName() + ": " + booking.getTimeslot() + " " + booking.getPpl() + "ppl" + "\n";
                    }
                }
                System.out.println(bookingString);
                return index;
            }
            if (role == Role.RESTAURANT) {
                Restaurant restaurant = (Restaurant) ac;
                ArrayList<Booking> allbookings = restaurant.getAllBookings();
                Collections.sort(allbookings, Comparator.comparing(Booking::getTimeslot));
                int totalBookings = 0; // Counter for total bookings on the date
                ArrayList<Booking> requiredBookings = new ArrayList<>();
                for (Booking booking : allbookings) {
                    if (booking.getDate().equals(date)) {
                        totalBookings++; // Increment the total bookings counter
                        requiredBookings.add(booking);
                    }
                }
                List<List<Booking>> groupedBookings = new ArrayList<>();

                // Iterate over the initial list of bookings
                for (Booking booking : requiredBookings) {
                    boolean added = false;

                    // Check if the timeslot matches any existing group
                    for (List<Booking> group : groupedBookings) {
                        if (!group.isEmpty() && group.get(0).getTimeslot().equals(booking.getTimeslot())) {
                            group.add(booking);
                            added = true;
                            break;
                        }
                    }

                    // If the timeslot doesn't match any existing group, create a new group
                    if (!added) {
                        List<Booking> newGroup = new ArrayList<>();
                        newGroup.add(booking);
                        groupedBookings.add(newGroup);
                    }
                }

                // Access grouped bookings by timeslot
                for (List<Booking> group : groupedBookings) {
                    System.out.println(group.get(0).getTimeslot());
                    StringBuilder tableID = new StringBuilder("            ");
                    StringBuilder booker = new StringBuilder("            ");
                    StringBuilder ppl = new StringBuilder("            ");
                    StringBuilder contact = new StringBuilder("            ");
                    StringBuilder arrived = new StringBuilder("            ");
                    for (Booking booking : group) {
                        tableID.append(String.format("| Table ID: %-13d ", booking.getTableID()));
                        booker.append(String.format("| Booker: %-13s ", booking.getCustomerName()));
                        ppl.append(String.format("| Ppl No: %-13s ", booking.getPpl()));
                        contact.append(String.format("| Contact: %-13s ", booking.getCustomerContact()));
                        arrived.append(String.format("| Arrived?: %-13s ", booking.hasArrived()));
                    }
                    System.out.println(tableID.toString());
                    System.out.println(booker.toString());
                    System.out.println(ppl.toString());
                    System.out.println(contact.toString());
                    System.out.println(arrived.toString());
                }
                return totalBookings;
            }
        }
        return 0;
    }

    /*StringBuilder tableID = new StringBuilder("                ");
    StringBuilder seat = new StringBuilder("                ");
    StringBuilder status = new StringBuilder("                ");
    for (Table table : tables) {
        tableID.append(String.format("| Table ID: %-13d ", table.getTableID()));
        seat.append(String.format("| Seat: %-17d ", table.getSeatNum()));
        status.append(String.format("| Status: %-15s ", table.getStatus()));
    }
    System.out.println(tableID.toString());
    System.out.println(seat.toString());
    System.out.println(status.toString());*/

    public void makeComment(Account ac, int inputNumber, LocalDate date, int rate, String comment) {
        Customer customer = (Customer) ac;
        ArrayList<Booking> allbookings = customer.getAllBookings();
        ArrayList<Booking> bookings = new ArrayList<>();
        for (Booking booking : allbookings) {
            if (booking.getDate().equals(date)) {
                bookings.add(booking);
            }
        }
        Booking requiredBooking = bookings.get(inputNumber - 1);
        for (Account account : AccountList) {
            if (account.getUserName().equals(requiredBooking.getRestaurantName())) {
                Restaurant restaurant = (Restaurant) account;
                restaurant.addComment(ac.getUserName(), comment, rate);
            }
        }
    }

    public boolean takeAttendance(Account ac, LocalDate date, String inputSession, int tableID) {
        Restaurant restaurant = (Restaurant) ac;
        ArrayList<Booking> allbookings = restaurant.getAllBookings();
        for (Booking booking : allbookings) {
            if (booking.getDate().equals(date) && booking.getTableID() == tableID && booking.getTimeslot().equals(inputSession)) {
                booking.takeAttendance();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Booking> getCustomerAllBookings(Customer customer) {
        return customer.getAllBookings();
    }

    public ArrayList<Booking> getRestaurantAllBookings(Restaurant restaurant) {
        return restaurant.getAllBookings();
    }
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
