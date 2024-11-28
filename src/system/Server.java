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

import acm.Permission;

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
                Customer customer = new Customer(userName, password, name, contact);
                System.out.println("\n# Customer signed up successfully! #");
                AccountList.add(customer);
                return customer;
            }
            case "RESTAURANT" -> {
                // Create a Restaurant account
                Restaurant restaurant = new Restaurant(userName, password, name, type, district, address, contact,
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

    public Account signIn(String username, String password) {
        for (Account account : AccountList) {
            if (account.getAccountUserName().equals(username) && account.getAccountPassword().equals(password)) {
                return account; // Return the account if credentials match
            }
        }
        return null; // Return null if no match found
    }

    public String getUserName(Account account) {
        return account.getAccountUserName();
    }

    public int getPermissionNumber(Account ac) {
        int count = 1;
        for (Permission permission : ac.getAccountPermissions()) {
            System.out.println("\n" + count + ". " + permission.getResource());
            count++;
        }
        return count;
    }

    public int getPermissionSize(Account ac) {
        return ac.getAccountPermissions().size();
    }

    public String getPermissionResource(Account ac, int inputNumber) {
        if (getPermissionSize(ac) + 1 == inputNumber){
            return "LOGOUT";
        }
        else if (inputNumber >= 1 && inputNumber <= getPermissionSize(ac)){
            return ac.getAccountPermissions().get(inputNumber - 1).getResource().toString();
        }
        else{
            return "invalid";
        }
    }

    public String getUserDetail(Account ac) {
        System.out.println("\nUsername: " + ac.getAccountUserName() + "\nPassword: " + ac.getAccountPassword());
        return ac.getProfileDetail();
    }

    public void updateUserDetail(Account ac, Scanner in) {
        ac.edit(in);
    }

    public int getViewBookingRecord(Account ac, LocalDate date) {
        return ac.getBookingRecord(date);
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

    public boolean tableValidation(Restaurant ac, int tableID) {
        return ac.tableValidation(tableID);
    }

    public boolean takeAttendance(Account ac, LocalDate date, String inputSession, int tableID) {
        Restaurant restaurant = (Restaurant) ac;
        ArrayList<Booking> allbookings = restaurant.getAllBookings();
        for (Booking booking : allbookings) {
            if (booking.getBookingDate().equals(date) && booking.getBookingTableID() == tableID && booking.getBookingTimeslot().equals(inputSession)) {
                booking.takeAttendance();
                return true;
            }
        }
        return false;
    }

    public Booking getBookingToBeComment(Account ac, int inputNumber, LocalDate date) {
        Customer customer = (Customer) ac;
        ArrayList<Booking> allbookings = customer.getAllBookings();
        ArrayList<Booking> bookings = new ArrayList<>();
        for (Booking booking : allbookings) {
            if (booking.getBookingDate().equals(date)) {
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

    public void makeComment(Account ac, int inputNumber, LocalDate date, int rate, String commentString) {
        Customer customer = (Customer) ac;
        try {
            Restaurant restaurant = getBookingToBeComment(ac, inputNumber, date).getBookingRestaurant();
            ArrayList<Comment> allComments = restaurant.getAllCommentsList();
            Comment comment = new Comment(customer.getCustomerName(), commentString, rate, date);
            allComments.add(comment);
            float recal_rate = 0;
            for (Comment cm : allComments) {
                recal_rate += cm.getCommentRate();
            }
            restaurant.setRate(recal_rate / allComments.size());
            System.out.println("\nComment added!");
        } catch (Exception e) {
            System.out.println("Restaurant not found.");
        }
    }

    public ArrayList<Restaurant> getRestaurantAccounts() {
        ArrayList<Restaurant> result = new ArrayList<>();
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            result.add((Restaurant) restaurantAc);
        }
        return result;
    }

    public ArrayList<Restaurant> searchRestaurantsIn(String restaurantName, String district, String rateRange, String type, String ppl, String startTime, String session) {
        SearchCriteria search = new SearchCriteria(restaurantName, district, rateRange, type, ppl, startTime, session);
        return search.searchRestaurantsIn(getRestaurantAccounts());
    }

    public String getListInfo(Restaurant restaurant) {
        return restaurant.getRestaurantName()
                + "\n   Rate: " + restaurant.getRate()
                + "\n   District: " + restaurant.getDistrict()
                + "\n   Type: " + restaurant.getType();
    }

    public Restaurant getRestaurantAccountByUserName(String username) {
        for (Account account : RestaurantAccounts.values()) {
            if (account.getAccountUserName().equals(username)) {
                return (Restaurant) account;
            }
        }
        return null;
    }

    public String getRestaurantBookingDetail(Restaurant restaurant) {

        Restaurant requiredRestaurant = getRestaurantAccountByUserName(restaurant.getAccountUserName());
        if (requiredRestaurant != null) {
            return requiredRestaurant.getProfileDetail();
        }
        return "Restaurant not found.";
    }

    public int availableTableID(Restaurant ac, int ppl, String timeslotSession, LocalDate date) {
        Restaurant restaurant = getRestaurantAccountByUserName(ac.getAccountUserName());
        return restaurant.availableTableID(ppl, timeslotSession, date);
    }

    public String makeBooking(LocalDate date, int tableID, String bookSession, Restaurant restaurant, Customer ac, String contact, int ppl) {
        Restaurant requiredRestaurant = getRestaurantAccountByUserName(restaurant.getAccountUserName());
        if (requiredRestaurant == null) {
            return "Restaurant not found.";
        } else {
            Booking bk = new Booking(date, tableID, bookSession, requiredRestaurant, ac, contact, ppl);
            requiredRestaurant.addBooking(bk);
            ac.addBooking(bk);
            return "\nAlready booked a seat for you";
        }
    }

    public StringBuilder getAllTableInfo(Account ac) {
        Restaurant restaurant = (Restaurant) ac;
        return restaurant.getAllTableInfo();
    }

    public void updateTableInfo(Account ac, Scanner in, int tableID) {
        Restaurant restaurant = (Restaurant) ac;
        restaurant.updateTableInfo(in, tableID);
    }

    public ArrayList<Booking> getPeriodBookings(Restaurant restaurant, LocalDate startDate, LocalDate endDate) {
        ArrayList<Booking> allBookings = restaurant.getAllBookings();
        ArrayList<Booking> periodBookings = new ArrayList<>();
        for (Booking bk : allBookings) {
            if ((bk.getBookingDate().isEqual(startDate) || bk.getBookingDate().isAfter(startDate)) && (bk.getBookingDate().isEqual(endDate) || bk.getBookingDate().isBefore(endDate))) {
                periodBookings.add(bk);
            }
        }
        return periodBookings;
    }

    public ArrayList<Comment> getPeriodComments(Restaurant restaurant, LocalDate startDate, LocalDate endDate) {
        ArrayList<Comment> allComments = restaurant.getAllCommentsList();
        ArrayList<Comment> periodComments = new ArrayList<>();
        for (Comment cm : allComments) {
            if ((cm.getCommentDate().isEqual(startDate) || cm.getCommentDate().isAfter(startDate)) && (cm.getCommentDate().isEqual(endDate) || cm.getCommentDate().isBefore(endDate))) {
                periodComments.add(cm);
            }
        }
        return periodComments;
    }

    public static void mergeSort(ArrayList<Account> accounts, String sortBy) {
        if (accounts == null || accounts.size() <= 1) {
            return;
        }

        ArrayList<Account> temp = new ArrayList<>(accounts);
        Comparator<Account> comparator = sortBy.equals("lastWeekRate") ?
                Comparator.comparingDouble(Account::getRestaurantLastWeekRate) :
                Comparator.comparingDouble(Account::getRestaurantThisWeekRate);

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
        int rank = 1;
        for (int i = 0; i < accounts.size(); i++) {
            Account current = accounts.get(i);
            if (i == 0) {
                if (sortBy.equals("lastWeekRate")) {
                    current.setRestaurantLastWeekRank(rank);
                } else {
                    current.setRestaurantThisWeekRank(rank);
                }
            } else {
                Account previous = accounts.get(i - 1);
                if (sortBy.equals("lastWeekRate")) {
                    if (current.getRestaurantLastWeekRate() == previous.getRestaurantLastWeekRate()) {
                        current.setRestaurantLastWeekRank(previous.getRestaurantLastWeekRank());
                    } else {
                        current.setRestaurantLastWeekRank(++rank);
                    }
                } else {
                    if (current.getRestaurantThisWeekRate() == previous.getRestaurantThisWeekRate()) {
                        current.setRestaurantThisWeekRank(previous.getRestaurantThisWeekRank());
                    } else {
                        current.setRestaurantThisWeekRank(++rank);
                    }
                }
            }
        }

    }

    private void generateAccountLogData() {
        LocalDate thisWeekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate thisWeekEndDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate lastWeekStartDate = thisWeekStartDate.minusWeeks(1);
        LocalDate lastWeekEndDate = thisWeekEndDate.minusWeeks(1);
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            restaurantAc.generateRestaurantLogDataWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate); 
        }
        
        ArrayList<Account> lastWeekRankedRestaurantAccounts = new ArrayList<>(RestaurantAccounts.values());
        calAndSetRestaurantRank(lastWeekRankedRestaurantAccounts, "lastWeekRate");

        ArrayList<Account> thisWeekRankedRestaurantAccounts = new ArrayList<>(RestaurantAccounts.values());
        calAndSetRestaurantRank(thisWeekRankedRestaurantAccounts, "thisWeekRate");

        for(Account ac: RestaurantAccounts.values()){
            System.out.println(ac.getAccountUserName() + ": " + ac.getRestaurantThisWeekRank());
        }
    }

    public void generateAccountLog(Account ac) {
        generateAccountLogData();
        ac.generateRestaurantLog();
    }

    public void generateAccountWeeklyReport(Account restaurant) {
        restaurant.generateRestaurantWeeklyReport();
    }
    public void reset() {
        AccountList.clear();
        RestaurantAccounts.clear();
       
    }
}
