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
    private String name;
    private String contact;
    private ArrayList<Booking> allBookings = new ArrayList<>();

    public Account(List<Role> r, String userName, String password, String name, String contact) {
        this.id = idCounter++;
        this.roles = r;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
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

    public int getCommentRestaurantId(int inputNumber, LocalDate date) {

        ArrayList<Booking> requiredbookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getBookingDate().equals(date)) {
                requiredbookings.add(booking);
            }
        }
        return requiredbookings.get(inputNumber - 1).getRestaurantId();

    }

    public int availableTableIDInAccount(int ppl, String timeslotSession, LocalDate date) {
        return ((Restaurant) this).availableTableIDInRestaurant(ppl, timeslotSession, date);
    }

    public int getAccountPermissionNumber() {
        int count = 1;
        for (Permission permission : getAccountPermissions()) {
            System.out.println("\n" + count + ". " + permission.getResource());
            count++;
        }
        return count;
    }

    public String getAccountPermissionsString(int inputNumber) {
        return getAccountPermissions().get(inputNumber - 1).getResource().toString();
    }

    public String getAccountName() {
        return ((Restaurant) this).getRestaurantName();
    }

    public String getAccountDistrict() {
        return ((Restaurant) this).getDistrict();
    }

    public float getAccountRate() {
        return ((Restaurant) this).getRate();
    }

    public String getAccountType() {
        return ((Restaurant) this).getType();
    }

    public ArrayList<Table> getAccountAllTables() {
        return ((Restaurant) this).getAllTables();
    }

    public String getRestaurantName() {
        return ((Restaurant) this).getRestaurantName();
    }

    public float getRate() {
        return ((Restaurant) this).getRate();
    }

    public String getDistrict() {
        return ((Restaurant) this).getDistrict();
    }

    public String getType() {
        return ((Restaurant) this).getType();
    }

    public String getTimeslots() {
        return ((Restaurant) this).getTimeslots();
    }

}
