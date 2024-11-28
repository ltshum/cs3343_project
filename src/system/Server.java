package system;

import acm.Permission;
import acm.Resource;
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
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server {

    private static final Server instance = new Server();
    private static final ArrayList<Account> AccountList = new ArrayList<>();
    private final Map<String, Account> RestaurantAccounts = new HashMap<>();
    private final Map<String, Map<String, RestaurantLog>> AllRestaurantLogs = new HashMap<>();
    private final Map<String, RestaurantLogData> restaurantLogDataMap = new HashMap<>();

    private Server() {
    }

    public static Server getInstance() {
        return instance;
    }

    public void addRestaurantAccount(Restaurant restaurant) {
        RestaurantAccounts.put(restaurant.getAccountUserName(), restaurant);
    }

    public void addRestaurant(Restaurant ac) {
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

    public Resource getPermissionResource(Account ac, int inputNumber) {
        return ac.getAccountPermissions().get(inputNumber - 1).getResource();
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

    private void generateRestaurantLogData() {
        LocalDate thisWeekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate thisWeekEndDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate lastWeekStartDate = thisWeekStartDate.minusWeeks(1);
        LocalDate lastWeekEndDate = thisWeekEndDate.minusWeeks(1);
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            Restaurant restaurant = (Restaurant) restaurantAc;

            //last week data
            ArrayList<Booking> lastWeekBookings = getPeriodBookings(restaurant, lastWeekStartDate, lastWeekEndDate);
            ArrayList<Comment> lastWeekComments = getPeriodComments(restaurant, lastWeekStartDate, lastWeekEndDate);
            int lastWeekTotalPpl = lastWeekBookings.stream().mapToInt(Booking::getBookingPpl).sum();
            float lastWeekRate = lastWeekComments.isEmpty() ? 0 : (float) lastWeekComments.stream().mapToDouble(Comment::getCommentRate).average().getAsDouble();

            //this week data
            ArrayList<Booking> thisWeekBookings = getPeriodBookings(restaurant, thisWeekStartDate, thisWeekEndDate);
            ArrayList<Comment> thisWeekComments = getPeriodComments(restaurant, thisWeekStartDate, thisWeekEndDate);
            int thisWeekTotalPpl = thisWeekBookings.stream().mapToInt(Booking::getBookingPpl).sum();
            float thisWeekRate = thisWeekComments.isEmpty() ? 0 : (float) thisWeekComments.stream().mapToDouble(Comment::getCommentRate).average().getAsDouble();

            RestaurantLogData restaurantLogData = new RestaurantLogData(lastWeekComments, lastWeekTotalPpl, lastWeekRate, 0, thisWeekComments, thisWeekTotalPpl, thisWeekRate, 0);
            restaurantLogDataMap.put(restaurant.getAccountUserName(), restaurantLogData);

        }

        // Sort the map by thisWeekRate
        @SuppressWarnings("unused")
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

        // Sort the map by lastWeekRate 
        @SuppressWarnings("unused")
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

    public void generateRestaurantLog() {
        generateRestaurantLogData();
        for (Account restaurantAc : (RestaurantAccounts.values())) {
            Restaurant restaurant = (Restaurant) restaurantAc;
            RestaurantLogData restaurantLogData = restaurantLogDataMap.get(restaurant.getAccountUserName());
            Map<String, RestaurantLog> restaurantLogs = new HashMap<>();
            restaurantLogs.put("lastWeek", new RestaurantLog(restaurantLogData.getLastWeekRank(), restaurantLogData.getLastWeekRate(), restaurantLogData.getLastWeekTotalPpl(), restaurantLogData.getLastWeekComments()));
            restaurantLogs.put("thisWeek", new RestaurantLog(restaurantLogData.getThisWeekRank(), restaurantLogData.getThisWeekRate(), restaurantLogData.getThisWeekTotalPpl(), restaurantLogData.getThisWeekComments()));
            AllRestaurantLogs.put(restaurant.getAccountUserName(), restaurantLogs);
        }
    }

    public RestaurantLog getRestaurantLog(Restaurant restaurant, String period) {
        Map<String, RestaurantLog> logs = AllRestaurantLogs.get(restaurant.getAccountUserName());
        if (logs != null) {
            return logs.get(period);
        }
        return null;
    }

    public void generateWeeklyReport(Restaurant restaurant) {
        RestaurantLog lastWeekLog = getRestaurantLog(restaurant, "lastWeek");
        RestaurantLog thisWeekLog = getRestaurantLog(restaurant, "thisWeek");

        StringBuilder report = new StringBuilder();

        report.append("Last week:\n");
        report.append("Rank: ").append(lastWeekLog.getRank()).append("\n");
        report.append("Rate: ").append(lastWeekLog.getRate()).append("\n");
        report.append("Total ppl: ").append(lastWeekLog.getTotalPpl()).append("\n");
        report.append("Comment:\n");
        for (Comment comment : lastWeekLog.getComments()) {
            report.append(comment.getCommentCustomerName()).append(": ").append(comment.getCommentContent()).append(" ").append(comment.getCommentRate()).append("\n");
        }

        report.append("\nThis week:\n");
        report.append("Rank: ").append(thisWeekLog.getRank()).append("\n");
        report.append("Rate: ").append(thisWeekLog.getRate()).append("\n");
        report.append("Total ppl: ").append(thisWeekLog.getTotalPpl()).append("\n");
        report.append("Comment:\n");
        for (Comment comment : thisWeekLog.getComments()) {
            report.append(comment.getCommentCustomerName()).append(": ").append(comment.getCommentContent()).append(" ").append(comment.getCommentRate()).append("\n");
        }

        report.append("\nRank decrease/increase ").append(thisWeekLog.getRank() - lastWeekLog.getRank()).append("\n");
        report.append("Rate decrease/increase ").append(thisWeekLog.getRate() - lastWeekLog.getRate()).append("\n");
        report.append("Total ppl decrease/increase ").append(thisWeekLog.getTotalPpl() - lastWeekLog.getTotalPpl()).append("\n");

        System.out.println(report.toString());
    }
    public void reset() {
        AccountList.clear();
        RestaurantAccounts.clear();
        AllRestaurantLogs.clear();
        restaurantLogDataMap.clear();
    }
}
