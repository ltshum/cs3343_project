package system;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private static final Server instance = new Server();
    private static final ArrayList<Account> AccountList = new ArrayList<>();
    private final Map<String, Account> RestaurantAccounts = new HashMap<>();

    private Server() {
    }

    public static Server getInstance() {
        return instance;
    }

    public void addRestaurantAccount(Restaurant restaurant) {
        RestaurantAccounts.put(restaurant.getAccountUserName(), restaurant);
    }

    public void addAccount(Account ac) {
        AccountList.add(ac);
    }

    public ArrayList<Account> getAccountList() {
        return AccountList;
    }

    public Account getAccountByUserName(String username) {
        for (Account account : AccountList) {
            if (account.getAccountUserName().equals(username)) {
                return account;
            }
        }
        return null;
    }

    // public void updateSeatNo(Account ac, int tableID, int seatNum) {
    //     ac.updateSeatNo(tableID, seatNum);
    // }
    public boolean isUsernameExist(String username) {
        if (AccountList.stream().anyMatch(user -> user.getAccountUserName().equals(username))) {
            System.out.print("\nError: Username already exists! Please input another.");
            return true;
        }
        return false;
    }

    public Account signUp(String role, String userName, String password, String name, String contact,
            String type, String district, String address,
            LocalTime openTime, LocalTime closeTime,
            Duration sessionDuration, int tableNum) {
        switch (role) {
            case "CUSTOMER" -> {
                // Create a Customer account
                Account customer = new Customer(userName, password, name, contact);
                System.out.println("\n# Customer signed up successfully! #");
                AccountList.add(customer);
                return customer;
            }
            case "RESTAURANT" -> {
                // Create a Restaurant account
                Account restaurant = new Restaurant(userName, password, name, type, district, address, contact,
                        openTime, closeTime, sessionDuration, tableNum);
                System.out.println("\n# Restaurant signed up successfully! #");
                AccountList.add(restaurant);
                RestaurantAccounts.put(restaurant.getAccountUserName(), restaurant);
                return restaurant;
            }
            default -> {
                return null;
            }
        }
    }

    public String signIn(String username, String password) {
        for (Account account : AccountList) {
            if (account.getAccountUserName().equals(username) && account.getAccountPassword().equals(password)) {
                return account.getAccountUserName(); // Return the account if credentials match
            }
        }
        return null; // Return null if no match found
    }

    public String getUserName(Account account) {
        return account.getAccountUserName();
    }

    public int getPermissionNumber(String acUsername) {
        Account ac = getAccountByUserName(acUsername);
        return ac.getAccountPermissionNumber();
    }

    public int getPermissionSize(Account ac) {
        return ac.getAccountPermissions().size();
    }

    public String getPermissionResource(String acUsername, int inputNumber) {
        Account ac = getAccountByUserName(acUsername);
        if (getPermissionSize(ac) + 1 == inputNumber) {
            return "LOGOUT";
        } else if (inputNumber >= 1 && inputNumber <= getPermissionSize(ac)) {
            return ac.getAccountPermissions().get(inputNumber - 1).getResource().toString();
        } else {
            return "invalid";
        }
    }

    public String getUserDetail(String userName) {
        Account ac = getAccountByUserName(userName);
        System.out.println("\nUsername: " + ac.getAccountUserName() + "\nPassword: " + ac.getAccountPassword());
        return ac.getProfileDetail();
    }

    public void updateUserDetail(String userName, Scanner in) {
        Account ac = getAccountByUserName(userName);
        ac.edit(in);
    }

    public int getViewBookingRecord(String userName, LocalDate date) {
        Account ac = getAccountByUserName(userName);
        return ac.getBookingRecord(date);
    }

    public boolean timeslotValidation(String userName, String bookTimeslot) {
        Account restaurantAc = getAccountByUserName(userName);
        String[] allTimeslots = restaurantAc.getAccountTimeslots().split(" \n");
        for (String timeslot : allTimeslots) {
            if (timeslot.equals(bookTimeslot)) {
                return true;
            }
        }
        return false;
    }

    public boolean tableValidation(String userName, int tableID) {
        Account restaurantAc = getAccountByUserName(userName);
        return restaurantAc.tableValidationInAccount(tableID);
    }

    public boolean takeAttendance(String userName, LocalDate date, String inputSession, int tableID) {
        Account ac = getAccountByUserName(userName);
        return ac.takeAttenanceInAccount(date, inputSession, tableID);
    }

    public boolean checkHasAttend(String userName, int inputNumber, LocalDate date) {
        Account ac = getAccountByUserName(userName);
        return ac.checkHasAttendInAccount(inputNumber, date);
    }

    public void makeComment(String userName, int inputNumber, LocalDate date, int rate, String commentString) {

        Account restaurantAc = getAccountByUserName(userName);
        String restaurantUserName = restaurantAc.getCommentRestaurantUserName(inputNumber, date);
        Account commentRestaurant = getAccountByUserName(restaurantUserName);
        Comment cm = new Comment(restaurantAc.getAccountName(), commentString, rate, date);
        commentRestaurant.addCommentInAccount(cm);

    }

    public ArrayList<Account> getRestaurantAccounts() {
        ArrayList<Account> result = new ArrayList<>();
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            result.add(restaurantAc);
        }
        return result;
    }

    public ArrayList<String> searchRestaurantsIn(String restaurantName, String district, String rateRange, String type, String ppl, String startTime, String session) {
        SearchCriteria search = new SearchCriteria(restaurantName, district, rateRange, type, ppl, startTime, session);
        ArrayList<String> resultList = new ArrayList<>();
        for (Account restaurant : search.searchRestaurantsIn(getRestaurantAccounts())) {
            resultList.add(restaurant.getAccountUserName());
        }
        return resultList;
    }

    public String getListInfo(String userName) {
        Account restaurant = getAccountByUserName(userName);
        return restaurant.getAccountName()
                + "\n   Rate: " + restaurant.getAccountRate()
                + "\n   District: " + restaurant.getAccountDistrict()
                + "\n   Type: " + restaurant.getAccountType();
    }

    public Account getRestaurantAccountByUserName(String username) {
        for (Account account : RestaurantAccounts.values()) {
            if (account.getAccountUserName().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public String getRestaurantBookingDetail(String userName) {
//        Account restaurant = getAccountByUserName(userName);
        Account requiredRestaurant = getRestaurantAccountByUserName(userName);
        if (requiredRestaurant != null) {
            return requiredRestaurant.getProfileDetail();
        }
        return "Restaurant not found.";
    }

    public int availableTableID(String userName, int ppl, String timeslotSession, LocalDate date) {
        Account ac = getAccountByUserName(userName);
        Account restaurant = getRestaurantAccountByUserName(ac.getAccountUserName());
        return restaurant.availableTableIDInAccount(ppl, timeslotSession, date);
    }

    public Account searchAccountById(int id) {
        for (Account ac : AccountList) {
            if (ac.getId() == id) {
                return ac;
            }
        }
        return null;
    }

    public String makeBooking(LocalDate date, int tableID, String bookSession, String restaurantUserName, String customerUserName, String contact, int ppl) {

        Account restaurant = getAccountByUserName(restaurantUserName);
        Account customer = getAccountByUserName(customerUserName);
        Booking booking = new Booking(date, tableID, bookSession, restaurant.getAccountName(), restaurantUserName, customer.getAccountName(), customer.getAccountContact(), ppl);
        restaurant.addBooking(booking);
        customer.addBooking(booking);
        return "\nAlready booked a seat for you";

    }

    public StringBuilder getAllTableInfo(String userName) {
        Account ac = getAccountByUserName(userName);
        return ac.getAccountAllTableInfo();
    }

    public void updateTableInfo(String userName, Scanner in, int tableID) {
        Account ac = getAccountByUserName(userName);
        ac.updateAccountTableInfo(in, tableID);
    }

    public static void mergeSort(ArrayList<Account> accounts, String sortBy) {
        if (accounts == null || accounts.size() <= 1) {
            return;
        }

        ArrayList<Account> temp = new ArrayList<>(accounts);
        Comparator<Account> comparator = sortBy.equals("lastWeekRate")
                ? Comparator.comparingDouble(Account::getRestaurantLastWeekRate)
                : Comparator.comparingDouble(Account::getRestaurantThisWeekRate);

        mergeSort(accounts, temp, 0, accounts.size() - 1, comparator);
    }

    private static void mergeSort(ArrayList<Account> accounts, ArrayList<Account> temp, int leftStart, int rightEnd, Comparator<Account> comparator) {
        if (leftStart >= rightEnd) {
            return;
        }

        int middle = (leftStart + rightEnd) / 2;
        mergeSort(accounts, temp, leftStart, middle, comparator);
        mergeSort(accounts, temp, middle + 1, rightEnd, comparator);
        mergeHalves(accounts, temp, leftStart, rightEnd, middle, comparator);
    }

    private static void mergeHalves(ArrayList<Account> accounts, ArrayList<Account> temp, int leftStart, int rightEnd, int middle, Comparator<Account> comparator) {
        int leftEnd = middle;
        int rightStart = middle + 1;
        int left = leftStart;
        int right = rightStart;
        int index = leftStart;
        while (left <= leftEnd && right <= rightEnd) {
            if (comparator.compare(accounts.get(left), accounts.get(right)) >= 0) { // Sort in descending order
                temp.set(index, accounts.get(left));
                left++;
            } else {
                temp.set(index, accounts.get(right));
                right++;
            }
            index++;
        }
        while (left <= leftEnd) {
            temp.set(index, accounts.get(left));
            left++;
            index++;
        }
        while (right <= rightEnd) {
            temp.set(index, accounts.get(right));
            right++;
            index++;
        }
        for (int i = leftStart; i <= rightEnd; i++) {
            accounts.set(i, temp.get(i));
        }
    }

    public void calAndSetRestaurantRank(ArrayList<Account> accounts, String sortBy) {
        mergeSort(accounts, sortBy);
        for (int i = 0; i < accounts.size(); i++) {
            Account current = accounts.get(i);
            if (i == 0) {
                if (sortBy.equals("lastWeekRate")) {
                    current.setRestaurantLastWeekRank(1);
                } else {
                    current.setRestaurantThisWeekRank(1);
                }
            } else {
                Account previous = accounts.get(i - 1);
                if (sortBy.equals("lastWeekRate")) {
                    if (current.getRestaurantLastWeekRate() == previous.getRestaurantLastWeekRate()) {
                        current.setRestaurantLastWeekRank(previous.getRestaurantLastWeekRank());
                    } else {
                        current.setRestaurantLastWeekRank(i + 1);
                    }
                } else {
                    if (current.getRestaurantThisWeekRate() == previous.getRestaurantThisWeekRate()) {
                        current.setRestaurantThisWeekRank(previous.getRestaurantThisWeekRank());
                    } else {
                        current.setRestaurantThisWeekRank(i + 1);
                    }
                }
            }
        }

    }

    public void generateAccountLog() {
        LocalDate thisWeekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate thisWeekEndDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate lastWeekStartDate = thisWeekStartDate.minusWeeks(1);
        LocalDate lastWeekEndDate = thisWeekEndDate.minusWeeks(1);
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            restaurantAc.generateRestaurantLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
        }

        ArrayList<Account> lastWeekRankedRestaurantAccounts = new ArrayList<>(RestaurantAccounts.values());
        calAndSetRestaurantRank(lastWeekRankedRestaurantAccounts, "lastWeekRate");

        ArrayList<Account> thisWeekRankedRestaurantAccounts = new ArrayList<>(RestaurantAccounts.values());
        calAndSetRestaurantRank(thisWeekRankedRestaurantAccounts, "thisWeekRate");

        for (Account restaurantAc : (RestaurantAccounts.values())) {
            System.out.println(restaurantAc.getAccountName() + ":" + restaurantAc.getRestaurantThisWeekRank() + "     " + restaurantAc.getRestaurantLastWeekRank());
        }
    }

    public void generateAccountWeeklyReport(String restaurantUsername) {
        Account restaurant = getAccountByUserName(restaurantUsername);
        restaurant.generateRestaurantWeeklyReport();
    }

    public void reset() {
        AccountList.clear();
        RestaurantAccounts.clear();
    }

    public boolean isRestaurantByUsername(String accountUsername) {
        return getAccountByUserName(accountUsername) instanceof Restaurant;
    }

    public boolean isCustomerByUsername(String accountUsername) {
        return getAccountByUserName(accountUsername) instanceof Customer;
    }
}
