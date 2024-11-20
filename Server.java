
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server {

    private static final Server instance = new Server();
    private static ArrayList<Account> AccountList = new ArrayList<>();
    private Map<String, Account> RestaurantAccounts = new HashMap<>();
    private Map<String, Map<String, RestaurantLog>> AllRestaurantLogs = new HashMap<>();
    private Map<String, RestaurantLogData> restaurantLogDataMap = new HashMap<>();

    private Server() {
    }

    public void addRestaurantAccount(Restaurant restaurant) {
        RestaurantAccounts.put(restaurant.getUserName(), restaurant);
    }

    public void generateRestaurantLog() {
        generateRestaurantLogData();
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            Restaurant restaurant = (Restaurant) restaurantAc;
            RestaurantLogData restaurantLogData = restaurantLogDataMap.get(restaurant.getUserName());
            Map<String, RestaurantLog> restaurantLogs = new HashMap<>();
            restaurantLogs.put("lastWeek", new RestaurantLog(restaurantLogData.getLastWeekRank(), restaurantLogData.getLastWeekRate(), restaurantLogData.getLastWeekTotalPpl(), restaurantLogData.getLastWeekComments()));
            restaurantLogs.put("thisWeek", new RestaurantLog(restaurantLogData.getThisWeekRank(), restaurantLogData.getThisWeekRate(), restaurantLogData.getThisWeekTotalPpl(), restaurantLogData.getThisWeekComments()));
            AllRestaurantLogs.put(restaurant.getUserName(), restaurantLogs);
        }
    }

    public RestaurantLog getRestaurantLog(Restaurant restaurant, String period) {
        Map<String, RestaurantLog> logs = AllRestaurantLogs.get(restaurant.getUserName());
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
            //add to list
            RestaurantAccounts.put(restaurant.getUserName(), restaurant);
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
                        + "\nComment: \n" + restaurant.getCommentString();
            }
        }
        return null;
    }

    public String getRestaurantBookingDetail(Restaurant restaurant) {

        Restaurant requiredRestaurant = getRestaurantAccountByUserName(restaurant.getUserName());
        if (requiredRestaurant != null) {
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
                        + "\nComment: \n" + requiredRestaurant.getCommentString();
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

    public boolean tableValidation(Restaurant ac, int tableID){
        ArrayList<Table> allTables = ac.getAllTables();
        for (Table table: allTables) {
            if (table.getTableID() == tableID){
                return true;
            }
        }
        return false;
    }

    public int availableTableID(Restaurant ac, int ppl, String timeslotSession, LocalDate date) {
        ArrayList<Table> allTables = getRestaurantAccountByUserName(ac.getUserName()).getAllTables();
        for (Table table : allTables) {
            if (table.isTimeslotAvailable(timeslotSession, date)) {
                if (table.getSeatNum() >= ppl) {
                    table.setTimeslotUnavailable(timeslotSession, date);
                    return table.getTableID();
                }
            }
        }
        return 0;
    }

    public String makeBooking(LocalDate date, int tableID, String bookSession, Restaurant restaurant, Customer ac, String contact, int ppl) {
        Restaurant requiredRestaurant = getRestaurantAccountByUserName(restaurant.getUserName());
        if (requiredRestaurant == null) {
            return "Restaurant not found.";
        } else {
            Booking bk = new Booking(date, tableID, bookSession, requiredRestaurant.getRestaurantName(), ac.getCustomerName(), contact, ppl);
            requiredRestaurant.addBooking(bk);
            ac.addBooking(bk);
            return "Already booked a seat for you";
        }
    }

    public String getListInfo(Restaurant restaurant) {
        return restaurant.getRestaurantName() + ": " + restaurant.getRate() + " " + restaurant.getType() + " " + restaurant.getDistrict();
    }

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
                int totalBookings = 0;
                ArrayList<Booking> requiredBookings = new ArrayList<>();
                for (Booking booking : allbookings) {
                    if (booking.getDate().equals(date)) {
                        totalBookings++;
                        requiredBookings.add(booking);
                    }
                }
                List<List<Booking>> groupedBookings = new ArrayList<>();

                for (Booking booking : requiredBookings) {
                    boolean added = false;
                    for (List<Booking> group : groupedBookings) {
                        if (!group.isEmpty() && group.get(0).getTimeslot().equals(booking.getTimeslot())) {
                            group.add(booking);
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        List<Booking> newGroup = new ArrayList<>();
                        newGroup.add(booking);
                        groupedBookings.add(newGroup);
                    }
                }
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

    public Booking getBookingToBeComment(Account ac, int inputNumber, LocalDate date) {
        Customer customer = (Customer) ac;
        ArrayList<Booking> allbookings = customer.getAllBookings();
        ArrayList<Booking> bookings = new ArrayList<>();
        for (Booking booking : allbookings) {
            if (booking.getDate().equals(date)) {
                bookings.add(booking);
            }
        }
        return bookings.get(inputNumber - 1);
    }

    public boolean checkHasAttend(Account ac, int inputNumber, LocalDate date) {
        if (!getBookingToBeComment(ac, inputNumber, date).hasArrived()) {
            System.out.println("\nYou can only make comment after you have attended the appointment.");
            return false;
        }
        return true;
    }

    public void makeComment(Account ac, int inputNumber, LocalDate date, int rate, String comment) {
        try {
            getRestaurantAccountByUserName(getBookingToBeComment(ac, inputNumber, date).getRestaurantName()).addComment(ac.getUserName(), comment, rate, date);
        } catch (Exception e) {
            System.out.println("Restaurant not found.");
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

    public Restaurant getRestaurantAccountByUserName(String username) {
        for (Account account : AccountList) {
            if (account.getUserName().equals(username)) {
                return (Restaurant) account;
            }
        }
        return null;
    }

    private void generateRestaurantLogData() {
        LocalDate thisWeekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate thisWeekEndDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate lastWeekStartDate = thisWeekStartDate.minusWeeks(1);
        LocalDate lastWeekEndDate = thisWeekEndDate.minusWeeks(1);
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            Restaurant restaurant = (Restaurant) restaurantAc;

            //last week data
            ArrayList<Booking> lastWeekBookings = restaurant.getPeriodBooking(lastWeekStartDate, lastWeekEndDate);
            ArrayList<Comment> lastWeekComments = restaurant.getPeriodComment(lastWeekStartDate, lastWeekEndDate);
            int lastWeekTotalPpl = lastWeekBookings.stream().mapToInt(Booking::getPpl).sum();
            float lastWeekRate = lastWeekComments.isEmpty() ? 0 : (float) lastWeekComments.stream().mapToDouble(Comment::getRate).average().getAsDouble();

            //this week data
            ArrayList<Booking> thisWeekBookings = restaurant.getPeriodBooking(thisWeekStartDate, thisWeekEndDate);
            ArrayList<Comment> thisWeekComments = restaurant.getPeriodComment(thisWeekStartDate, thisWeekEndDate);
            int thisWeekTotalPpl = thisWeekBookings.stream().mapToInt(Booking::getPpl).sum();
            float thisWeekRate = thisWeekComments.isEmpty() ? 0 : (float) thisWeekComments.stream().mapToDouble(Comment::getRate).average().getAsDouble();

            RestaurantLogData restaurantLogData = new RestaurantLogData(lastWeekComments, lastWeekTotalPpl, lastWeekRate, 0, thisWeekComments, thisWeekTotalPpl, thisWeekRate, 0);
            restaurantLogDataMap.put(restaurant.getUserName(), restaurantLogData);

        }
        
        Map<String, RestaurantLogData> sortedByThisWeekRate = restaurantLogDataMap.entrySet()
            .stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue(Comparator.comparingDouble(RestaurantLogData::getThisWeekRate))))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Update thisWeekRank based on the index after sorting
        int thisWeekRank = 1;
        Float thisWeekPreviousRate = null;
        int thisWeekSameRateRank = 0;
        for (Map.Entry<String, RestaurantLogData> entry : sortedByThisWeekRate.entrySet()) {
            Float currentRate = entry.getValue().getLastWeekRate();
            if (thisWeekPreviousRate == null || currentRate.compareTo(thisWeekPreviousRate) != 0) {
                thisWeekRank += thisWeekSameRateRank;
                thisWeekSameRateRank = 1;
                thisWeekPreviousRate = currentRate;
            } else {
                thisWeekSameRateRank++;
            }
            entry.getValue().setThisWeekRank(thisWeekRank);
        }

        // Sort the map by lastWeekRate from big to small
        Map<String, RestaurantLogData> sortedByLastWeekRate = restaurantLogDataMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue(Comparator.comparingDouble(RestaurantLogData::getLastWeekRate))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Update lastWeekRank based on the index after sorting
        int lastWeekRank = 1;
        Float lastWeekPreviousRate = null;
        int lastWeekSameRateRank = 0;
        for (Map.Entry<String, RestaurantLogData> entry : sortedByLastWeekRate.entrySet()) {
            Float currentRate = entry.getValue().getLastWeekRate();
            if (lastWeekPreviousRate == null || currentRate.compareTo(lastWeekPreviousRate) != 0) {
                lastWeekRank += lastWeekSameRateRank;
                lastWeekSameRateRank = 1;
                lastWeekPreviousRate = currentRate;
            } else {
                lastWeekSameRateRank++;
            }
            entry.getValue().setLastWeekRank(lastWeekRank);
        }
    }
}


