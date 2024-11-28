package system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import acm.Permission;
import acm.PermissionRegistry;
import acm.Role;

public abstract class Account {

    private static int idCounter = 1;
    private int id;
    private List<Role> roles;
    private String userName;
    private String password;

    public Account(List<Role> r, String n, String p) {
        this.id = idCounter++;
        this.roles = r;
        this.userName = n;
        this.password = p;
    }

    // public boolean hasPermission(Resource resource, Privilege privilege) {
    //     for (Permission permission : permissions) {
    //         if (roles.contains(permission.getRole())
    //                 && permission.getResource() == resource
    //                 && permission.canPerform(privilege)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }
    public static int getIdCounter() {
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getAccountUserName() {
        return userName;
    }

    public String getAccountPassword() {
        return password;
    }

    //fetch permissions dynamically
    public List<Permission> getAccountPermissions() {
        List<Permission> allPermissions = new ArrayList<>();
        for (Role role : roles) {
            allPermissions.addAll(PermissionRegistry.getPermissionsForRole(role));
        }
        return allPermissions;
    }

    public static void setIdCounter(int idCounter) {
        Account.idCounter = idCounter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoles(List<Role> roles) {
    
        this.roles = roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void edit(Scanner in);

    public abstract String getProfileDetail();

    public abstract int getBookingRecord(LocalDate date);

    //protected abstract void updateSeatNo(int tableID, int seatNum);

    public void generateRestaurantLogDataWithoutRank(LocalDate thisWeekStartDate, LocalDate thisWeekEndDate, LocalDate lastWeekStartDate, LocalDate lastWeekEndDate ) {
       ((Restaurant)this).generateLogDataWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
    }

    public float getRestaurantLastWeekRate() {
        return ((Restaurant)this).getLastWeekRate();
    }

    public float getRestaurantThisWeekRate() {
        return ((Restaurant)this).getThisWeekRate();
    }

    public void setRestaurantLastWeekRank(int rank) {
        ((Restaurant)this).setLastWeekRank(rank);
    }

    public void setRestaurantThisWeekRank(int rank) {
        ((Restaurant)this).setThisWeekRank(rank);
    }

    public int getRestaurantThisWeekRank() {
        return ((Restaurant)this).getThisWeekRank();
    }

    public int getRestaurantLastWeekRank() {
        return ((Restaurant)this).getLastWeekRank();
    }

    public void generateRestaurantLog() {
        ((Restaurant)this).generateLog();
    }

    public void generateRestaurantWeeklyReport() {
        ((Restaurant)this).generateWeeklyReport();
    }

    public boolean takeAttenanceInAccount(LocalDate date, String inputSession, int tableID) {
        return ((Restaurant)this).takeAttendanceInRestaurant(date, inputSession, tableID);
    }

    public boolean checkHasAttendInAccount(int inputNumber, LocalDate date) {
        return ((Customer)this).checkHasAttendInCustomer(inputNumber, date);
    }

    public void makeCommentInAccount(int inputNumber, LocalDate date, int rate, String commentString) {
        try {
            Restaurant restaurant = ((Customer) this).getRestaurantToBeComment(inputNumber, date);
            restaurant.makeCommentInRestaurant(((Customer) this).getCustomerName(), date, rate, commentString);
            System.out.println("\nComment added!");
        } catch (Exception e) {
            System.out.println("Restaurant not found.");
        }

    }

    public int availableTableIDInAccount(int ppl, String timeslotSession, LocalDate date) {
        return ((Restaurant)this).availableTableIDInRestaurant(ppl, timeslotSession, date);
    }

    public void makeBookingInAccount(LocalDate date, int tableID, String bookSession, Customer ac, String contact, int ppl) {
        ac.addBooking(((Restaurant)this).makeBookingInRestaurant(date, tableID, bookSession, ac, contact, ppl));
    }

}
