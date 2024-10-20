
// Other imports as needed
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Restaurant extends Account {

    private String restaurantName;
    private String type;
    private String address;
    private String restaurantContact;
    private float rate;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Duration sessionDuration;
    private ArrayList<Table> allTables = new ArrayList<>();

    //private ArrayList<Comment> allComments = new ArrayList<>();
    public Restaurant(String userName, String password, String name, String type, String address, String contact, LocalTime openTime, LocalTime closeTime, Duration sessionDuration, int tableNum) {
        super(Arrays.asList(Role.RESTAURANT), userName, password, getRestaurantPermissions());
        this.restaurantName = name;
        this.type = type;
        this.address = address;
        this.restaurantContact = contact;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.sessionDuration = sessionDuration;
        this.allTables = new ArrayList<>();
        initializeTables(tableNum);
        generateTimeslots();
        //this.allComments = new ArrayList<>();
        //this.allTables = new ArrayList<>();
    }

    private void initializeTables(int tableCount) {
        for (int i = 1; i <= tableCount; i++) {
            allTables.add(new Table(i));
        }
    }

    public void getTimeslots() {
        LocalTime currentTime = openTime;
        while (currentTime.plus(sessionDuration).isBefore(closeTime)
                || currentTime.plus(sessionDuration).equals(closeTime)) {
            String session = currentTime.toString() + " - " + currentTime.plus(sessionDuration).toString();
            currentTime = currentTime.plus(sessionDuration);
            System.out.println(session);
        }
    }

    public void setTimeslots(LocalTime openTime, LocalTime closeTime, Duration sessionDuration) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.sessionDuration = sessionDuration;
        LocalTime currentTime = openTime;
        while (currentTime.plus(sessionDuration).isBefore(closeTime)
                || currentTime.plus(sessionDuration).equals(closeTime)) {
            String session = currentTime.toString() + " - " + currentTime.plus(sessionDuration).toString();
            for (Table table : allTables) {
                table.addTimeslot(new Timeslot(session));
            }
            currentTime = currentTime.plus(sessionDuration);
        }
    }

    private void generateTimeslots() {
        LocalTime currentTime = openTime;
        while (currentTime.plus(sessionDuration).isBefore(closeTime)
                || currentTime.plus(sessionDuration).equals(closeTime)) {
            String session = currentTime.toString() + " - " + currentTime.plus(sessionDuration).toString();
            for (Table table : allTables) {
                table.addTimeslot(new Timeslot(session));
            }
            currentTime = currentTime.plus(sessionDuration);
        }
    }

    // Permissions for the restaurant
    private static List<Permission> getRestaurantPermissions() {
        return Arrays.asList(
                new Permission(Role.RESTAURANT, Resource.PROFILE, Set.of(Privilege.READ, Privilege.UPDATE)),
                new Permission(Role.RESTAURANT, Resource.TABLE_MANAGEMENT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.TIMESLOT_MANAGEMENT, Set.of(Privilege.CREATE, Privilege.READ, Privilege.UPDATE, Privilege.DELETE)),
                new Permission(Role.RESTAURANT, Resource.BOOKING, Set.of(Privilege.READ)),
                new Permission(Role.RESTAURANT, Resource.COMMENT, Set.of(Privilege.READ, Privilege.REPLY))
        );
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getRestaurantContact() {
        return restaurantContact;
    }

    public float getRate() {
        return rate;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public long getSessionDuration() {
        return sessionDuration.toMinutes();
    }

    public ArrayList<Table> getAllTables() {
        return allTables;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRestaurantContact(String restaurantContact) {
        this.restaurantContact = restaurantContact;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setSessionDuration(Duration sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public void setAllTables(ArrayList<Table> allTables) {
        this.allTables = allTables;
    }

}
