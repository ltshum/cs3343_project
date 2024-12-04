package system;

import acm.Permission;
import acm.PermissionRegistry;
import acm.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Account {

    private static int idCounter = 1;
    private int id;
    private List<Role> roles;
    private String userName;
    private String password;
    private String name;
    private String contact;
    private final ArrayList<Booking> allBookings = new ArrayList<>();

    public Account(List<Role> r, String userName, String password, String name, String contact) {
        this.id = idCounter++;
        this.roles = r;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.contact = contact;
    }

    public String getAccountName() {
        return name;
    }

    public void setAccountName(String name) {
        this.name = name;
    }

    public String getAccountContact() {
        return contact;
    }

    public void setAccountContact(String contact) {
        this.contact = contact;
    }

    public ArrayList<Booking> getAllBookings() {
        return allBookings;
    }

    public void addBooking(Booking booking) {
        allBookings.add(booking);
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
            PermissionRegistry permissionRegistry = new PermissionRegistry();
            allPermissions.addAll(permissionRegistry.getPermissionsForRole(role));
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
    public void generateRestaurantLogWithoutRank(LocalDate thisWeekStartDate, LocalDate thisWeekEndDate, LocalDate lastWeekStartDate, LocalDate lastWeekEndDate) {
        ((Restaurant) this).generateLogWithoutRank(thisWeekStartDate, thisWeekEndDate, lastWeekStartDate, lastWeekEndDate);
    }

    public float getRestaurantLastWeekRate() {
        return ((Restaurant) this).getLastWeekRate();
    }

    public float getRestaurantThisWeekRate() {
        return ((Restaurant) this).getThisWeekRate();
    }

    public void setRestaurantLastWeekRank(int rank) {
        ((Restaurant) this).setLastWeekRank(rank);
    }

    public void setRestaurantThisWeekRank(int rank) {
        ((Restaurant) this).setThisWeekRank(rank);
    }

    public int getRestaurantThisWeekRank() {
        return ((Restaurant) this).getThisWeekRank();
    }

    public int getRestaurantLastWeekRank() {
        return ((Restaurant) this).getLastWeekRank();
    }

    public void generateRestaurantWeeklyReport() {
        ((Restaurant) this).generateWeeklyReport();
    }

    public boolean takeAttenanceInAccount(LocalDate date, String inputSession, int tableID) {
        return ((Restaurant) this).takeAttendanceInRestaurant(date, inputSession, tableID);
    }

    public boolean checkHasAttendInAccount(int inputNumber, LocalDate date) {
        return ((Customer) this).checkHasAttendInCustomer(inputNumber, date);
    }

    public String getCommentRestaurantUserName(int inputNumber, LocalDate date) {
        ArrayList<Booking> requiredBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getBookingDate().equals(date)) {
                requiredBookings.add(booking);
            }
        }
        // Check if the list is empty or if the input number is out of bounds
        if (requiredBookings.isEmpty() || inputNumber <= 0 || inputNumber > requiredBookings.size()) {
            return null; // or throw an exception if you prefer
        }
        return requiredBookings.get(inputNumber - 1).getRestaurantUserName();
    }

    public int availableTableIDInAccount(int ppl, String timeslotSession, LocalDate date) {
        return ((Restaurant) this).availableTableIDInRestaurant(ppl, timeslotSession, date);
    }

    public int getAccountPermissionNumber() {
        int count = 0;
        for (Permission permission : this.getAccountPermissions()) {
            System.out.println("\n" + count + ". " + permission.getResource());
            count++;
        }
        return count;
    }

    public String getAccountPermissionsString(int inputNumber) {
        return getAccountPermissions().get(inputNumber - 1).getResource().toString();
    }

    public String getAccountDistrict() {
        return ((Restaurant) this).getRestaurantDistrict();
    }

    public float getAccountRate() {
        return ((Restaurant) this).getRestaurantRate();
    }

    public String getAccountType() {
        return ((Restaurant) this).getRestaurantType();
    }

    public ArrayList<Table> getAccountAllTables() {
        return ((Restaurant) this).getRestaurantAllTables();
    }

    public String getAccountTimeslots() {
        return ((Restaurant) this).getRestaurantTimeslots();
    }

    public boolean tableValidationInAccount(int tableID) {
        return ((Restaurant) this).tableValidationInRestaurant(tableID);
    }

    public void addCommentInAccount(Comment cm) {
        ((Restaurant) this).addCommentInRestaurant(cm);
    }

    public StringBuilder getAccountAllTableInfo() {
        return ((Restaurant) this).getRestaurantAllTableInfo();
    }

    public void updateAccountTableInfo(Scanner in, int tableID) {
        ((Restaurant) this).updateRestaurantTableInfo(in, tableID);
    }

    

}
